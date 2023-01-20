package com.springboot.batch.common.exception;

import com.springboot.batch.service.job.SQLTransJob01.dao.Step3Dao;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Setter
@Getter
@Slf4j
public class ThreadHandler2 implements Thread.UncaughtExceptionHandler {

    @Autowired
    Step3Dao step3Dao;

    private ExecutorService executorService;

    private CountDownLatch latch;

    public ThreadHandler2(int e, int l){
        executorService = Executors.newFixedThreadPool(e);
        latch = new CountDownLatch(l);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error(String.format(
                "[%s] 스레드에서 발생한 Exception \n%s",
                t.getName(), ExceptionUtils.getStackTrace(e))
        );
        //step3Dao.updateTbInfoList(paramTableName, selectSourceTbInfo);
        latch.countDown();
    }
}
