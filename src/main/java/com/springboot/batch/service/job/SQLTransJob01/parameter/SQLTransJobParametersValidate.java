package com.springboot.batch.service.job.SQLTransJob01.parameter;

import com.springboot.batch.common.parameter.JobParametersDateValidate;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class SQLTransJobParametersValidate implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String jobExeDate = parameters.getString("jobExeDate");
        String jobExeSeq  = parameters.getString("jobExeSeq");
        new JobParametersDateValidate(jobExeDate);

        if(jobExeSeq == null) {
            throw new JobParametersInvalidException("jobExeSeq 빈 문자열이거나 존재하지 않습니다.");
        }
    }
}
