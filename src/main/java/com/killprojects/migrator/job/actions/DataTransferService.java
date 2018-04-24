package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface DataTransferService<T> extends Serializable {

    JavaRDD<EntityContainer<T>> transfer(JavaRDD<EntityContainer<T>> idAndObjectPairRDD, MainJobContext context);
}
