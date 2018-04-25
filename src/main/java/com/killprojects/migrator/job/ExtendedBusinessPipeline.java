package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.EntityContainer;
import com.killprojects.migrator.dto.TransferResult;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

@Component
public class ExtendedBusinessPipeline<T> implements Function<Tuple2<String, Long>, EntityContainer<T>> {


    private BusinessPipeline businessPipeline;

    @Override
    public EntityContainer<T> call(Tuple2<String, Long> pair) throws Exception {
        //проверяем на уже отправленную запись
        // смотрим в hbase в таблицу №1, если существует с таким index ом то, не выполняем функцию
        if (false) {
            return null;
        }

        EntityContainer entityContainer = businessPipeline.call(pair);
        //сохраняем index в hbase в таблицу №1 отправленных
        //сохраняем entityContainer в hbase в таблицу №2, для онлайн-статистики
        return entityContainer;
    }

    @Autowired
    public void setBusinessPipeline(BusinessPipeline businessPipeline) {
        this.businessPipeline = businessPipeline;
    }
}
