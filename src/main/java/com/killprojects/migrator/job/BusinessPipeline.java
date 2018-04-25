package com.killprojects.migrator.job;

import com.killprojects.migrator.api.RecordConverter;
import com.killprojects.migrator.api.RecordIdRegistry;
import com.killprojects.migrator.api.RecordTransfer;
import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.job.ErrorProcessor;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

@Component
public class BusinessPipeline<T> implements Function<Tuple2<String, Long>, EntityContainer<T>> {

    private RecordConverter<T> recordConverter;
    private RecordIdRegistry<T> recordIdRegistry;
    private RecordTransfer<T> recordTransfer;
    private ErrorProcessor<T> errorProcessor;

    @Override
    public EntityContainer<T> call(Tuple2<String, Long> pair) throws Exception {
        T object = recordConverter.convert(pair._1);
        String businessId = recordIdRegistry.defineRecordID(object);

        TransferResult transferResult = recordTransfer.transfer(object);
        TransferResult processedResult = errorProcessor.processIfError(transferResult, object);

        return new EntityContainer<>(pair._2, businessId, object, processedResult);
    }

    @Autowired
    public void setRecordConverter(RecordConverter<T> recordConverter) {
        this.recordConverter = recordConverter;
    }

    @Autowired
    public void setRecordIdRegistry(RecordIdRegistry<T> recordIdRegistry) {
        this.recordIdRegistry = recordIdRegistry;
    }

    @Autowired
    public void setRecordTransfer(RecordTransfer<T> recordTransfer) {
        this.recordTransfer = recordTransfer;
    }

    @Autowired
    public void setErrorProcessor(ErrorProcessor<T> errorProcessor) {
        this.errorProcessor = errorProcessor;
    }
}
