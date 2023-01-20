package com.springboot.batch.service.job.SQLTransJob01.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

@Slf4j
public class SQLTransStepListener extends StepExecutionListenerSupport {

    StopWatch sw = new StopWatch();

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("["+stepExecution.getStepName()+"] AFTER STEP !!!");
        sw.stop();
        log.info("시간 " + (sw.getTotalTimeMillis() / 1000) + "초");
        return super.afterStep(stepExecution);
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("["+stepExecution.getStepName()+"] BEFORE STEP !!!");
        sw.start();
        super.beforeStep(stepExecution);
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener () {
        ExecutionContextPromotionListener executionContextPromotionListener = new ExecutionContextPromotionListener();

        System.out.println("gigigihihihihihi");
        // 데이터 공유를 위해 사용될 key값을 미리 빈에 등록해주어야 합니다.
        executionContextPromotionListener.setKeys(new String[]{"SPECIFIC_MEMBER"});

        return executionContextPromotionListener;
    }
}
