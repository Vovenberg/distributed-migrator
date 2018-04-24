package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface DataConverterService<T> extends Serializable {

    JavaPairRDD<RecordId, T> convert(JavaRDD<String> lines, MainJobContext context);
}
