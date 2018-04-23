package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.job.actions.DataConverterService;
import com.killprojects.migrator.job.actions.DataTransferService;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.job.contexts.ResendJobContext;
import com.killprojects.migrator.job.contexts.StatisticsJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SparkMapReduceJobService<T> implements MapReduceJobService {

    private final JavaSparkContext sparkContext;
    private final DataTransferService<T> dataTransferService;
    private final DataConverterService<T> dataConverterService;

    public SparkMapReduceJobService(JavaSparkContext sparkContext,
                                    DataTransferService<T> dataTransferService,
                                    DataConverterService<T> dataConverterService) {
        this.sparkContext = sparkContext;
        this.dataTransferService = dataTransferService;
        this.dataConverterService = dataConverterService;
    }

    @Override
    public Map<RecordId, TransferResult> executeMainJob(MainJobContext context) {
        JavaRDD<String> inputDataRDD = sparkContext.textFile(context.getInputPath());

        JavaPairRDD<RecordId, T> convertedRDD = dataConverterService.convert(inputDataRDD, context);

        JavaPairRDD<RecordId, TransferResult> transferedRDD
                = dataTransferService.transfer(convertedRDD, context);

        //пока что просто collect
        return transferedRDD.collectAsMap();
    }

    @Override
    public void executeResendJob(ResendJobContext context) {

    }

    @Override
    public void executeOnlineStatisticsJob(StatisticsJobContext context) {

    }

}