package com.springboot.batch.common.incrementer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class UniqueRunIdIncrementer extends RunIdIncrementer {
    private static final String RUN_ID = "run.id";

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;
        return new JobParametersBuilder()
                .addLong(RUN_ID, params.getLong(RUN_ID, 0L) + 1)
                .addString("jobExeDate", "20220522")
                .toJobParameters();
    }
}