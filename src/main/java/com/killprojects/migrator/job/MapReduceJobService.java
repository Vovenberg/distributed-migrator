package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.job.contexts.ResendJobContext;
import com.killprojects.migrator.job.contexts.StatisticsJobContext;

import java.io.Serializable;
import java.util.List;

public interface MapReduceJobService<T> extends Serializable {

    List<EntityContainer<T>> executeMainJob(MainJobContext context);

    void executeResendJob(ResendJobContext context);

    void executeOnlineStatisticsJob(StatisticsJobContext context);

}
