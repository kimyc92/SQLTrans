package com.springboot.batch.service.job.SQLTransJob02.parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
@NoArgsConstructor
public class SQLTransJobParameter {

    @Value("#{jobParameters[jobExeDate]}")
    private String jobExeDate;

    @Value("#{jobParameters[jobExeSeq]}")
    private String jobExeSeq;

}
