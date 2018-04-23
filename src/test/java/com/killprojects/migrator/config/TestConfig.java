package com.killprojects.migrator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainConfig.class)
public class TestConfig {
}
