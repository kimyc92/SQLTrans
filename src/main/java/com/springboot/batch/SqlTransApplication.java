package com.springboot.batch;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@SpringBootApplication
@Slf4j
public class SqlTransApplication {
/*
    public static void main(String[] args) {
        SpringApplication.run(SqlTransApplication.class, args);
    }

 */

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:profile/prd/real-application.yml";

    @Value("${prd.master.datasource.maxLifetime}")
    private String property;

    @Value("${spring.batch.job.names:NONE}")
    private String jobNames;

    @Resource
    private ApplicationArguments applicationArguments;  // CLI 인수

    public static void main(String[] args) {
        //SpringApplication.run(SqlTransApplication.class, args);
        System.setProperty("log4jdbc.log4j2.properties.file", "/log/log4jdbc.log4j2.properties");		// LOG4JDBC PROPS

        new SpringApplicationBuilder(SqlTransApplication.class)   	// SpringApplication.run(MyBatchApplication.class, args);
                .properties(APPLICATION_LOCATIONS)
                .run(args);

    }

    // spring.batch.job.names 를 지정하지 않으면 모든 Job이 실행돼 버리기 때문에
    // 방어차원에서 넣은 job.names validation 처리
    @PostConstruct
    public void validateJobExe() {
        log.info("jobNames : {}", jobNames);
        if(jobNames.isEmpty() || jobNames.equals("NONE")) {
            throw new IllegalStateException("spring.batch.job.names=job1,job2 형태로 실행을 원하는 Job을 명시해야만 합니다!");
        }
        log.info("arg length : "+ applicationArguments.getSourceArgs().length);
        if(applicationArguments.getSourceArgs().length < 2){
            throw new IllegalStateException("Application CLI 인수 값은 jobName을 제외하고 1개 이상 이어야 합니다.");
        }
    }
    /*
    @Bean
    public CommandLineRunner runner() {
        return (a) -> {
            log.info("CommandLineRunner: " + property);
            log.info("test: "+test);
        };
    };

     */

}
