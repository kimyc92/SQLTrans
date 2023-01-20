package com.springboot.batch.service.job.SQLTransJob02.parameter;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;

public abstract class SQLTransJobParameterConfig {

    protected JobParametersBuilder getUniqueJobParametersBuilder() {
        return new JobParametersBuilder(new JobLauncherTestUtils().getUniqueJobParameters());
    }
}
