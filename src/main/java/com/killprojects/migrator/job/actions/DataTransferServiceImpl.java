package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.api.RecordTransfer;
import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.job.ErrorProcessor;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataTransferServiceImpl<T> implements DataTransferService<T>, RequiredFieldAssertable {

    private RecordTransfer<T> recordTransfer;
    private ErrorProcessor<T> errorProcessor;

    @Override
    public JavaRDD<EntityContainer<T>> transfer(JavaRDD<EntityContainer<T>> dataFrameRDD, MainJobContext context) {
        // каждый объект отправляем, формируем пару <id,результат отправки>
        return dataFrameRDD.map(dataFrame -> {
            TransferResult transferResult = recordTransfer.transfer(dataFrame.getObject());
            TransferResult processedResult = errorProcessor.processIfError(transferResult, dataFrame);

            dataFrame.setTransferResult(processedResult);
            return dataFrame;
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
