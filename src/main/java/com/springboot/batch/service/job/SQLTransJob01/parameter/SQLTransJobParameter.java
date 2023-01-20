package com.springboot.batch.service.job.SQLTransJob01.parameter;

import lombok.Data;
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
