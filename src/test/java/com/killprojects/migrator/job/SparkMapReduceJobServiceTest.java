package com.killprojects.migrator.job;

import com.killprojects.migrator.config.TestConfig;
import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.dto.TransferResult;
import com.killprojects.migrator.dto.User;
import com.killprojects.migrator.job.actions.DataTransferServiceImpl;
import com.killprojects.migrator.job.contexts.MainJobContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = TestConfig.class)
class SparkMapReduceJobServiceTest {

    @Autowired
    SparkMapReduceJobService<User> jobService;

    @Autowired
    DataTransferServiceImpl<User> dataTransferService;

    @Autowired
    JavaSparkContext sc;

    @Test
    @DisplayName("совместный запуск конвертации и трансфера")
    void executeMainJob() throws IOException {
        List<User> targetSystemEmulationList = new ArrayList<>();

        dataTransferService.setRecordTransfer(user -> {
            targetSystemEmulationList.add(user);
            return new TransferResult();
        });

        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        MainJobContext mainJobContext = MainJobContext.builder()
                .inputPath(new ClassPathResource("testfile.txt").getFile().getAbsolutePath())
                .outputPath(temporaryFolder.getRoot().getAbsolutePath())
                .build();

        Map<RecordId, TransferResult> resultMap = jobService.executeMainJob(mainJobContext);

        assertNotNull(resultMap);
        assertEquals(7, resultMap.size());
    }
}