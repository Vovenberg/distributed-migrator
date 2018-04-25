package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.EntityContainer;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Service;

@Service
public class SparkHdfsJobService<T> extends AbstractSparkJobService<T> {


    @Override
    JavaRDD<String> extractInputRdd(String inputPath) {
        return sparkSession.sparkContext()
                .textFile(inputPath, 2)
                .toJavaRDD();
    }

    @Override
    void saveOutputRdd(JavaRDD<EntityContainer<T>> outputRdd, String outputPath) {
        outputRdd.saveAsTextFile(outputPath);
    }
}
