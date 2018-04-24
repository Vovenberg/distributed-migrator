package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.config.TestConfig;
import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.dto.User;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DataTransferServiceImplTest {

    @Autowired
    DataTransferServiceImpl<User> dataTransferService;

    @Autowired
    JavaSparkContext sc;


    @Test
    @DisplayName("просто отправляем user'ов")
    void transfer() {
        List<User> targetSystemEmulationList = new ArrayList<>();

        dataTransferService.setRecordTransfer(user -> {
            targetSystemEmulationList.add(user);
            return new TransferResult();
        });

        JavaPairRDD<RecordId, User> pairRDD = sc.parallelizePairs(Arrays.asList(
                Tuple2.apply(RecordId.is("1", 12L), new User("sd", 23)),
                Tuple2.apply(RecordId.is("2", 12L), new User("ssdd", 253)),
                Tuple2.apply(RecordId.is("3", 12L), new User("xcv", 223)),
                Tuple2.apply(RecordId.is("4", 12L), new User("sxcvd", 253)),
                Tuple2.apply(RecordId.is("5", 12L), new User("sxcvd", 236))));
        JavaPairRDD<RecordId, TransferResult> resultRdd = dataTransferService.transfer(pairRDD, new MainJobContext());

        Assert.assertNotNull(resultRdd);
        List<Tuple2<RecordId, TransferResult>> resultList = resultRdd.collect();
        Assert.assertEquals(5, resultList.size());
    }
}