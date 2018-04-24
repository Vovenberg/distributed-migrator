package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.dto.TransferResult;
import org.apache.spark.api.java.JavaPairRDD;

import java.io.Serializable;

public interface DataTransferService<T> extends Serializable {

    JavaPairRDD<RecordId, TransferResult> transfer(JavaPairRDD<RecordId, T> idAndObjectPairRDD, MainJobContext context);
}
