//package com.killprojects.migrator.job.actions;
//
//import com.killprojects.migrator.config.TestConfig;
//import com.killprojects.migrator.dto.EntityContainer;
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
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestConfig.class)
//class DataConverterServiceImplTest {
//
//    @Autowired
//    DataConverterServiceImpl<User> dataConverterService;
//
//    @Autowired
//    SparkSession sparkSession;
//
//    @Test
//    @DisplayName("просто парсим файл и конвертим строки в сущность User")
//    void simpleTest() throws IOException {
//        dataConverterService.setRecordConverter(line -> {
//            String[] values = line.split(" ");
//            return new User(values[0], Integer.valueOf(values[1]));
//        });
//        dataConverterService.setRecordIdRegistry(user -> String.valueOf(user.getExternalId()));
//
//        JavaRDD<EntityContainer<User>> testRdd= sparkSession.sparkContext().parallelize(Arrays.asList(
//                new EntityContainer<User>("1","sdf 1"),
//                new EntityContainer<User>("2","xcv 2"),
//                new EntityContainer<User>("3","vxc 4"),
//                new EntityContainer<User>("4","dsf 1"),
//                new EntityContainer<User>("5","wer 2"))).toJavaRDD();
//
//        JavaRDD<EntityContainer<User>> result = dataConverterService.convert(testRdd, new MainJobContext());
//
//        Assert.assertNotNull(result);
//        List resultList = result.collect();
//        Assert.assertEquals(7, resultList.size());
//    }
//
//
//}