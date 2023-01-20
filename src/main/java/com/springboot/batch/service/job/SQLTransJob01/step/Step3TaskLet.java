package com.springboot.batch.service.job.SQLTransJob01.step;

import com.springboot.batch.service.job.SQLTransJob01.dao.Step3Dao;
import com.springboot.batch.service.job.SQLTransJob01.handler.ThreadHandler;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameter;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
//@Configuration
public class Step3TaskLet {

    @Autowired
    Step3Dao step3Dao;

    @Autowired
    SQLTransJobShareParameter<Object> shareParam;

    @Autowired
    SQLTransJobShareParameter<List<Map<String, Object>>> shareParamList;

    @Resource(name="routeSqlSessionTemplate")
    SqlSession sqlSession;

    ThreadHandler threadHandler;

    public Boolean step3TaskLetProcess(SQLTransJobParameter jobParameter) {
        Boolean rtn = true;
        List<Map<String, Object>> tableList = shareParamList.getData("TB_JOB_EXE_INFO_DET_LIST");
        threadHandler = new ThreadHandler(10, tableList.size());
        Thread.setDefaultUncaughtExceptionHandler(threadHandler);
        ExecutorService executorService = threadHandler.getExecutorService();
        CountDownLatch latch = threadHandler.getLatch();

        try {

            for (Map<String, Object> item : tableList) {
                executorService.execute(() -> {
                    this.task(item);
                    latch.countDown();
                });
            }
            latch.await();

        } catch (Exception e) {
            log.info("[STEP3-E001] Source to Target DB Trans 작업 에러\n" + e.getStackTrace());
            return false;

        } finally {
            executorService.shutdown();
        }
        rtn = true;
        return rtn;
    }

    protected void task(Map<String, Object> item){//, String paramTableName, AtomicInteger totalCnt) {

        // Thread Value Setting
        String paramTableName = (String) item.get("TABLE_NAME");
        String paramJobExeDate = (String) shareParam.getData("JOB_EXE_DATE");
        Integer paramJobExeSeq = (Integer) shareParam.getData("JOB_EXE_SEQ");
        threadHandler.setSqlSession(sqlSession);
        threadHandler.setThreadTableName(new StringBuffer(paramTableName));
        threadHandler.setThreadJobExeDate(new StringBuffer(paramJobExeDate));
        threadHandler.setThreadJobExeSeq(new AtomicInteger(paramJobExeSeq));

        AtomicInteger totalCnt = new AtomicInteger(0);

        log.info("[STEP3-I000] TRANS START TABLE_NAME - " + paramTableName);
        Integer perPage = 500;   // paging 사이즈
        Integer curPage = 0;     // 현재 page 수

        while (true) {
            System.out.println(Thread.currentThread().getName());
            List<Map<String, Object>> selectSourceTbInfo =
                    step3Dao.selectSourceTbInfo(paramTableName, curPage, perPage);
            if (selectSourceTbInfo.isEmpty()) {
                log.info("[STEP3-I001] " + paramTableName + " 테이블, 이관 개수 : " + totalCnt);
                break;
            }
            totalCnt.updateAndGet(v -> v + selectSourceTbInfo.size());
            threadHandler.setTotalCnt(totalCnt);
            curPage += 1;
            step3Dao.insertTargetTbInfoList(paramTableName, selectSourceTbInfo);
            /*
            if (paramTableName.equals("TB_SAMPLE_LIST2") && curPage == 3) {
                int a = 5 / 0;
            }
             */
        }
        step3Dao.updateTbJobExeInfoDet(paramJobExeDate, paramJobExeSeq
                , paramTableName, totalCnt.intValue(), "Y", "Complete");
    }
}


