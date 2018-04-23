package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.job.contexts.MainJobContext;
import com.killprojects.migrator.job.contexts.ResendJobContext;
import com.killprojects.migrator.job.contexts.StatisticsJobContext;
import com.killprojects.migrator.dto.TransferResult;
import scala.Tuple2;

import java.util.List;
import java.util.Map;

public interface MapReduceJobService {

    Map<RecordId, TransferResult> executeMainJob(MainJobContext context);

    void executeResendJob(ResendJobContext context);

    void executeOnlineStatisticsJob(StatisticsJobContext context);

}
