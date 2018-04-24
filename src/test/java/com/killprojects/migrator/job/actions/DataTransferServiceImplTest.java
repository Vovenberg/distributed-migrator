//package com.killprojects.migrator.job.actions;
//
//import com.killprojects.migrator.config.TestConfig;
//import com.killprojects.migrator.dto.EntityContainer;
//import com.killprojects.migrator.dto.RecordId;
//import com.killprojects.migrator.dto.TransferResult;
//import com.killprojects.migrator.dto.User;
//import com.killprojects.migrator.job.contexts.MainJobContext;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.SparkSession;
//import org.junit.Assert;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import scala.Tuple2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestConfig.class)
//class DataTransferServiceImplTest {
//
//    @Autowired
//    DataTransferServiceImpl<User> dataTransferService;
//
//    @Autowired
//    SparkSession sparkSession;
//
//
//    @Test
//    @DisplayName("просто отправляем user'ов")
//    void transfer() {
//        List<User> targetSystemEmulationList = new ArrayList<>();
//
//        dataTransferService.setRecordTransfer(user -> {
//            targetSystemEmulationList.add(user);
//            return new TransferResult();
//        });
//
//        JavaRDD<EntityContainer<User>> testRdd= sc.parallelize(Arrays.asList(
//                new EntityContainer<User>("1","1", new User("sd", 23)),
//                new EntityContainer<User>("2","1", new User("ssdd", 253)),
//                new EntityContainer<User>("3","1",, new User("xcv", 223)),
//                new EntityContainer<User>("4","1",, new User("sxcvd", 253)),
//                new EntityContainer<User>("5","1",, new User("sxcvd", 236))));
//        JavaRDD<EntityContainer<User>> resultRdd = dataTransferService.transfer(testRdd, new MainJobContext());
//
//        Assert.assertNotNull(resultRdd);
//        List<EntityContainer<User>> resultList = resultRdd.collect();
//        Assert.assertEquals(5, resultList.size());
//    }
//}