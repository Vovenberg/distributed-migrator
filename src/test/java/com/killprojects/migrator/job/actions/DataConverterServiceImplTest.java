package com.killprojects.migrator.job.actions;

import com.killprojects.migrator.config.MainConfig;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MainConfig.class)
public class DataConverterServiceImplTest {

    @Autowired
    DataConverterServiceImpl<User> dataConverterService;

    @Autowired
    JavaSparkContext sc;

    @Test
    public void convert() {
        dataConverterService.setRecordConverter(line -> {
            String[] values = line.split(" ");
            return new User(values[0], Integer.valueOf(values[1]));
        });

        JavaRDD<String> javaRDD = sc.textFile(new ClassPathResource("testfile.txt").getPath());
        JavaPairRDD result = dataConverterService.convert(javaRDD, new MainJobContext());

        Assert.assertNotNull(result);
    }


}