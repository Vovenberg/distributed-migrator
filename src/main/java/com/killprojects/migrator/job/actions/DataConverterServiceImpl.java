package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.api.RecordConverter;
import com.killprojects.migrator.api.RecordIdRegistry;
import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import scala.Tuple2;

@Service
public class DataConverterServiceImpl<T> implements DataConverterService<T>, RequiredFieldAssertable {

    private RecordConverter<T> recordConverter;
    private RecordIdRegistry<T> recordIdRegistry;

    @Override
    public JavaPairRDD<RecordId, T> convert(JavaRDD<String> lines, MainJobContext context) {
        JavaPairRDD<String, Long> zippedRdd = lines.zipWithIndex();

        // каждую запись конвертим в бизнес-объект, определяем его id, формируем пару <id,объект>
        JavaPairRDD<RecordId, T> convertedRdd = zippedRdd.mapToPair(pair -> {
            T object = recordConverter.convert(pair._1);
            RecordId recordId = RecordId.is(recordIdRegistry.defineRecordID(object), pair._2);
            return Tuple2.apply(recordId, object);
        });

        return convertedRdd;
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
