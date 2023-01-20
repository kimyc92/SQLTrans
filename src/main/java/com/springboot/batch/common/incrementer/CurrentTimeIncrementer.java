package com.springboot.batch.common.incrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class CurrentTimeIncrementer implements JobParametersIncrementer {

	static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

	@Override
	public JobParameters getNext(JobParameters parameters) {
		String id = format.format(new Date());
		return new JobParametersBuilder()
				  .addString("run.id", id)
				  .toJobParameters();
	}

} 
