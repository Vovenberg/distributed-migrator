package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.job.contexts.ResendJobContext;
import com.killprojects.migrator.job.contexts.StatisticsJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public abstract class AbstractSparkJobService<T> implements JobService<T> {

    @Autowired
    protected SparkSession sparkSession;

    private BusinessPipeline<T> businessPipeline;


    abstract JavaRDD<String> extractInputRdd(String inputPath);

    abstract void saveOutputRdd(JavaRDD<EntityContainer<T>> outputRdd, String outputPath);

    @Override
    public List<EntityContainer<T>> executeMainJob(MainJobContext context) {
        JavaRDD<String> fileRdd = extractInputRdd(context.getInputPath());

        //Transformations:
        //1) file mapping to pair <line,index>
        JavaPairRDD<String, Long> lineAndIndexRdd = fileRdd.zipWithIndex();

        //2) business scenaries: convert and transfer
        JavaRDD<EntityContainer<T>> transferedRdd = lineAndIndexRdd.map(businessPipeline);

        //cache for subsequent utils
        transferedRdd.persist(StorageLevel.MEMORY_AND_DISK());

        //Actions:
        saveOutputRdd(transferedRdd, context.getOutputPath());

        return transferedRdd.collect();
    }

    @Override
    public void executeResendJob(ResendJobContext context) {
        sparkSession.sparkContext().textFile(context.getOutputPath(), 2);

    }

    @Override
    public void executeOnlineStatisticsJob(StatisticsJobContext context) {

    }

    @Autowired
    public void setBusinessPipeline(BusinessPipeline<T> businessPipeline) {
        this.businessPipeline = businessPipeline;
    }
}
