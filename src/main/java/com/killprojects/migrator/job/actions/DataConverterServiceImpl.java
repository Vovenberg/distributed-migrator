package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.api.RecordConverter;
import com.killprojects.migrator.api.RecordIdRegistry;
import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataConverterServiceImpl<T> implements DataConverterService<T>, RequiredFieldAssertable {

    private RecordConverter<T> recordConverter;
    private RecordIdRegistry<T> recordIdRegistry;

    @Override
    public JavaRDD<EntityContainer<T>> convert(JavaRDD<EntityContainer<T>> lines, MainJobContext context) {

        // каждую запись конвертим в бизнес-объект, определяем его id, формируем пару <id,объект>
        return lines.map(dataFrame -> {
            T object = recordConverter.convert(dataFrame.getLine());
            String businessId = recordIdRegistry.defineRecordID(object);

            dataFrame.setBusinessId(businessId);
            dataFrame.setObject(object);
            return dataFrame;
        });

    }

    @Autowired
    public void setRecordConverter(RecordConverter<T> recordConverter) {
        this.recordConverter = recordConverter;
    }

    @Autowired
    public void setRecordIdRegistry(RecordIdRegistry<T> recordIdRegistry) {
        this.recordIdRegistry = recordIdRegistry;
    }

    @Override
    public void checkRequiredFields() {
        Assert.notNull(recordConverter, "Bean RecordConverter not exist");
        Assert.notNull(recordIdRegistry, "Bean RecordIdRegistry not exist");
    }
}
