package com.killprojects.migrator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainConfig.class)
@ComponentScan("com.killprojects.migrator.api.impl")
public class TestConfig {
}
