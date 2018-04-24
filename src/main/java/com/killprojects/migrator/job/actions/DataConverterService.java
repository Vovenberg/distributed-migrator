package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface DataConverterService<T> extends Serializable {

    JavaRDD<EntityContainer<T>> convert(JavaRDD<EntityContainer<T>> lines, MainJobContext context);
}
