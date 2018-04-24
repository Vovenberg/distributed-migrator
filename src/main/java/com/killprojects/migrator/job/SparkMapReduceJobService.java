package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.actions.DataConverterService;
import com.killprojects.migrator.job.actions.DataTransferService;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.job.contexts.ResendJobContext;
import com.killprojects.migrator.job.contexts.StatisticsJobContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SparkMapReduceJobService<T> implements MapReduceJobService<T> {

    private final SparkSession sparkSession;
    private final DataTransferService<T> dataTransferService;
    private final DataConverterService<T> dataConverterService;

    public SparkMapReduceJobService(SparkSession sparkSession,
                                    DataTransferService<T> dataTransferService,
                                    DataConverterService<T> dataConverterService) {
        this.sparkSession = sparkSession;
        this.dataTransferService = dataTransferService;
        this.dataConverterService = dataConverterService;
    }

    @Override
    public List<EntityContainer<T>> executeMainJob(MainJobContext context) {
        JavaRDD<EntityContainer<T>> inputRdd = sparkSession.sparkContext()
                .textFile(context.getInputPath(), 2)
                .zipWithIndex()
                .toJavaRDD()
                .map(pair -> {
                    EntityContainer<T> entityContainer = new EntityContainer<>();
                    entityContainer.setIndex(String.valueOf(pair._2));
                    entityContainer.setLine(pair._1);
                    return entityContainer;
                });


        //Transformations:
        JavaRDD<EntityContainer<T>> convertedRdd = dataConverterService.convert(inputRdd, context);
        JavaRDD<EntityContainer<T>> transferedRdd = dataTransferService.transfer(convertedRdd, context);

        //cache for subsequent actions
        transferedRdd.persist(StorageLevel.MEMORY_AND_DISK());

        //Actions:
        Dataset<Row> dataFrame = sparkSession.createDataFrame(transferedRdd, EntityContainer.class);
        dataFrame.write().json(context.getOutputPath());

        return transferedRdd.collect();
    }

    @Override
    public void executeResendJob(ResendJobContext context) {
        sparkSession.sparkContext().textFile(context.getOutputPath(), 2);

    }

    @Override
    public void executeOnlineStatisticsJob(StatisticsJobContext context) {

    }

}
