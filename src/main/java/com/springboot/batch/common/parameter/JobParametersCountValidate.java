package com.springboot.batch.common.parameter;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class JobParametersCountValidate {
    public JobParametersCountValidate(Integer jobCnt,Integer cliCnt) throws JobParametersInvalidException {
        if(jobCnt != cliCnt){
            throw new JobParametersInvalidException("jobName을 제외하고, Application CLI 인수 개수는 "+jobCnt+"개 여야 합니다.(현재 값 : "+cliCnt+")");
        }
    }
}
