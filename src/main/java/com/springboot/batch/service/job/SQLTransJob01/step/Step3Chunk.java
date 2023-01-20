package com.springboot.batch.service.job.SQLTransJob01.step;

import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.service.job.SQLTransJob01.dao.Step3Dao;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor
@Component
//@Configuration
public class Step3Chunk {

    @Autowired
    Step3Dao step3Dao;

    @Autowired
    SQLTransJobShareParameter<List<Map<String, Object>>> shareParam;

    @Resource(name="routeSqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음


    //@Transactional(propagation= Propagation.REQUIRES_NEW)
    /*
    public SynchronizedItemStreamReader<List<Map<String, Object>>> customItemReader() {
        System.out.println("here 1111");
        return step3Dao.selectSourceItemReader();
    }
*/
    /*
    //@StepScope
    public TaskExecutor taskExecutor(){
        System.out.println("[STEP3] taskExecutor");
        RoutingDatabaseContextHolder.set(DatabaseType.Target);
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


     */
    /*


    //@Transactional(propagation= Propagation.REQUIRES_NEW)
    public MyBatisBatchItemWriter<List<Map<String, Object>>> customItemWriter() throws Exception {
        System.out.println("[STEP3] itemWriter");
        RoutingDatabaseContextHolder.set(DatabaseType.Target);
        return step3Dao.insertTargetItemWriter();

        ItemToParameterMapConverters mytest = new ItemToParameterMapConverters();
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST");
        return new MyBatisBatchItemWriterBuilder<List<Map<String, Object>>>()
                .assertUpdates(false)
                .sqlSessionFactory(sqlSessionFactory)
                .statementId(SQLTransJob01Step3Mapper.class.getName()+".insertTargetTbInfo")
                .itemToParameterConverter(mytest.createItemToParameterMapConverter(parameterValues))
                .build();


    }

*/

    /*
    @StepScope
    public Step step3ChunkProcess() throws Exception {
        return stepBuilderFactory.get("step3")
                .<List<Map<String, Object>>, List<Map<String, Object>>> chunk(5000)    // <Object, Object> 에서 첫번째는 Reader에서 반환타입, 두번쨰는 Writer에 파라미터로 넘어올 타입
                .reader(customItemReader())
                .writer(customItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

     */

}
