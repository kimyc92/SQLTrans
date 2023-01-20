package com.springboot.batch.service.job.SQLTransJob01.handler;

import com.springboot.batch.service.job.SQLTransJob01.dao.ThreadDao;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
@Slf4j
public class ThreadHandler implements Thread.UncaughtExceptionHandler {
    //private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
    private StringBuffer ThreadTableName;

    private StringBuffer ThreadJobExeDate;

    private AtomicInteger ThreadJobExeSeq;

    private AtomicInteger TotalCnt = new AtomicInteger(0);

    private ExecutorService executorService;

    private CountDownLatch latch;

    private SqlSession sqlSession;
/*
    public void setThreadLocal(SqlSession sqlSession){
        threadLocal.set(sqlSession);
    }

    public SqlSession getThreadLocal(){
        return threadLocal.get();
    }
*/
    /*
    public AtomicInteger setTotalCnt(Integer v){
        TotalCnt = new AtomicInteger(v);
        return TotalCnt;
    }
     */

    //public CountDownLatch getLatch(){ return latch; }

    public ThreadHandler(int e, int l){
        executorService = Executors.newFixedThreadPool(e);
        latch = new CountDownLatch(l);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            log.error(String.format(
                    "[%s] 스레드에서 발생한 Exception \n%s",
                    t.getName(), ExceptionUtils.getStackTrace(e))
            );

            byte[] trace = ExceptionUtils.getStackTrace(e).getBytes();
            int lastLength = Math.min(trace.length, 4000);

            ThreadDao threadDao = new ThreadDao();
            threadDao.updateTbJobExeInfoDet(String.valueOf(ThreadJobExeDate), ThreadJobExeSeq.intValue()
                    , String.valueOf(ThreadTableName), getTotalCnt().intValue(), "E"
                    , new String(trace, 0, lastLength), this.sqlSession);

        } catch (Exception te) {
            log.error(ExceptionUtils.getStackTrace(te));
            executorService.shutdown();

        } finally {
            latch.countDown();
        }
    }
}
