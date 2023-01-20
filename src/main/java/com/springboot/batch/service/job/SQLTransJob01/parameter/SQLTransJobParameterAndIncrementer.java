package com.springboot.batch.service.job.SQLTransJob01.parameter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;

@Slf4j
public class SQLTransJobParameterAndIncrementer extends RunIdIncrementer {

    private String jobExeDate;
    private Integer jobExeSeq;
    private static final String RUN_ID = "run.id";

    public SQLTransJobParameterAndIncrementer(String jobExeDate, Integer jobExeSeq) {
        this.jobExeDate = jobExeDate;
        this.jobExeSeq  = jobExeSeq;
    }

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;
        return new JobParametersBuilder()
                .addLong(RUN_ID, params.getLong(RUN_ID, 0L) + 1)
                .addString("jobExeDate", jobExeDate)
                .addLong("jobExeSeq", Long.valueOf(jobExeSeq))
                .toJobParameters();
    }
}
