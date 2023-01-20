package com.springboot.batch.service.job.SQLTransJob02;

import com.springboot.batch.common.parameter.JobParametersCountValidate;
import com.springboot.batch.service.job.SQLTransJob02.listener.SQLTransJobListener;
import com.springboot.batch.service.job.SQLTransJob02.listener.SQLTransStepListener;
import com.springboot.batch.service.job.SQLTransJob02.mapper.SQLTransJob02Step1Mapper;
import com.springboot.batch.service.job.SQLTransJob02.parameter.ItemToParameterMapConverters;
import com.springboot.batch.service.job.SQLTransJob02.parameter.SQLTransJobParameterAndIncrementer;
import com.springboot.batch.service.job.SQLTransJob02.parameter.SQLTransJobParametersValidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.batch.api.chunk.ItemProcessor;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "JOB02")
public class SQLTransJob02 {

    private final JobBuilderFactory jobBuilderFactory;   // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
//    private final SQLTransJobParameter jobParameter;
//
//    @Bean
//    @JobScope
//    public SQLTransJobParameter jobParameter() {
//        return new SQLTransJobParameter();
//    }

    @Resource
    private ApplicationArguments applicationArguments;  // CLI 인수s

    @Resource(name="routeSqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    @Bean(name="JOB02")
    public Job job() throws Exception {
        log.info("======== Hollow SQLTransJob02 ========");
        new JobParametersCountValidate(2, applicationArguments.getSourceArgs().length-1);
        String jobExeDate = applicationArguments.getSourceArgs()[1];
        String jobExeSeq  = applicationArguments.getSourceArgs()[2];

        return (Job) jobBuilderFactory.get("JOB02")
                .listener(new SQLTransJobListener())
                .incrementer(new SQLTransJobParameterAndIncrementer(jobExeDate, jobExeSeq))
                .validator(new SQLTransJobParametersValidate())   // 유효성 검사
                .start(step1())
                .build();
    }

    @Bean
    @JobScope
    public Step step1() throws Exception {
        //hibernateSqlSessionFactory.openStatelessSession();
        return stepBuilderFactory.get("step1")
                .<List<Map<String, Object>>, List<Map<String, Object>>> chunk(5000)    // <Object, Object> 에서 첫번째는 Reader에서 반환타입, 두번쨰는 Writer에 파라미터로 넘어올 타입
                .reader(customItemReader())
                //.reader(itemReader())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    @StepScope
    public SynchronizedItemStreamReader<List<Map<String, Object>>> customItemReader() {

        /*
        JdbcCursorItemReader<Customer> notSafetyReader = new JdbcCursorItemReaderBuilder<Customer>()
                .fetchSize(60)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
                .sql("select id, firstName, lastName, birthdate from customer")
                .name("SafetyReader")
                .build();

         */
        System.out.println("here 1111");
        Map<String,Object> parameterValues = new HashMap<>();
        //parameterValues.put("tableName", "TB_SAMPLE_LIST");
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST");
        MyBatisPagingItemReader<List<Map<String, Object>>> notSafetyReader = new MyBatisPagingItemReaderBuilder<List<Map<String, Object>>>()
                .pageSize(5000)
                .sqlSessionFactory(sqlSessionFactory)
                .queryId(SQLTransJob02Step1Mapper.class.getName()+".selectSourceTbInfo")
                .parameterValues(parameterValues)
                .build();

        return new SynchronizedItemStreamReaderBuilder<List<Map<String, Object>>>()
                .delegate(notSafetyReader)
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(3);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setKeepAliveSeconds(10);    // 10초 대기
        executor.setAllowCoreThreadTimeOut(true); // 대기 후 Thread 종료
        executor.setThreadNamePrefix("async-thread"); // 스레드 이름 prefix
        return executor;
    }

    @Bean
    public AsyncItemWriter asyncItemWriter() throws Exception {

        AsyncItemWriter<List<Map<String, Object>>> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(itemWriter());
        asyncItemWriter.afterPropertiesSet();
        return asyncItemWriter;
    }

    @StepScope
    public MyBatisPagingItemReader<List<Map<String, Object>>> itemReader() throws Exception {
        //System.out.println("[STEP1] zz itemReader");
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST");
        return new MyBatisPagingItemReaderBuilder<List<Map<String, Object>>>()
                .pageSize(5000)
                .sqlSessionFactory(sqlSessionFactory)
                .queryId(SQLTransJob02Step1Mapper.class.getName()+".selectSourceTbInfo")  // Mapper 사용의 경우 Paging 처리 시 OrderBy는 필수
                .parameterValues(parameterValues)
                .build();
    }


    @StepScope
    public MyBatisBatchItemWriter<List<Map<String, Object>>> itemWriter() {
        //System.out.println("[STEP1] itemWriter ");
        ItemToParameterMapConverters mytest = new ItemToParameterMapConverters();
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST_COPY");
        return new MyBatisBatchItemWriterBuilder<List<Map<String, Object>>>()
                .assertUpdates(false)
                .sqlSessionFactory(sqlSessionFactory)
                .statementId(SQLTransJob02Step1Mapper.class.getName()+".insertTargetTbInfo")
                .itemToParameterConverter(mytest.createItemToParameterMapConverter(parameterValues))
                .build();
    }

}
