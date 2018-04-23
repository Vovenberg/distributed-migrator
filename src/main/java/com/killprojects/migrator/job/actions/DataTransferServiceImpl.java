package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.api.RecordTransfer;
import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.job.ErrorProcessor;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import scala.Tuple2;

@Service
public class DataTransferServiceImpl<T> implements DataTransferService<T>, RequiredFieldAssertable {

    private RecordTransfer<T> recordTransfer;
    private ErrorProcessor<T> errorProcessor;

    @Override
    public JavaPairRDD<RecordId, TransferResult> transfer(JavaPairRDD<RecordId, T> idAndObjectPairRDD, MainJobContext context) {
        // каждый объект отправляем, формируем пару <id,результат отправки>
        return idAndObjectPairRDD.mapToPair(pair -> {
            TransferResult transferResult = recordTransfer.transfer(pair._2);
            TransferResult processedResult = errorProcessor.processIfError(transferResult, pair);
            return Tuple2.apply(pair._1, processedResult);
        });
    }

    @Autowired
    public void setRecordTransfer(RecordTransfer<T> recordTransfer) {
        this.recordTransfer = recordTransfer;
    }


    @Autowired
    public void setErrorProcessor(ErrorProcessor<T> errorProcessor) {
        this.errorProcessor = errorProcessor;
    }

    @Override
    public void checkRequiredFields() {
        Assert.notNull(recordTransfer, "Bean RecordTransfer not exist");
    }
}
