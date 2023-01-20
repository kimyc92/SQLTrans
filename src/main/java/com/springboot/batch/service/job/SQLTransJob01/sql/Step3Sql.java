package com.springboot.batch.service.job.SQLTransJob01.sql;

import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Step3Sql {

    public String selectSourceTbInfo(@Param("tableName") String tableName
            , @Param("curPage") Integer curPage
            , @Param("perPage") Integer perPage
    ) {
        return new SQL() {{
            log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.selectSourceTbInfo");
            SELECT("* FROM ( "
                    + "SELECT A.*, ROWNUM AS RN "
                    + "FROM ${tableName} A "
                    + "WHERE ROWNUM <= (#{curPage}+1)*#{perPage}"
                    + " ) "
                    + "WHERE RN > (#{curPage}*#{perPage})");
        }}.toString();

    }

    public String insertTargetTbInfo(@Param("tableName") String tableName
            , @Param("keyList") String keyList
            //, @Param("valueList") List<String> valueList
            , @Param("sourceTbInfo") Map<String, Object> sourceTbInfo
    ) {
        //final String[] finalHi = {""};
        return new SQL() {{
            log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfo");
            INSERT_INTO("${tableName}");
            INTO_COLUMNS("${keyList}");
            INTO_VALUES(sourceTbInfo.values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(",")));
            /*
            for(Object value : valueList){
                finalHi[0] = "'"+String.valueOf(value)+"'";
                INTO_VALUES(finalHi[0]);
            }*/
        }}.toString();
    }

    public StringBuffer insertTargetTbInfo2(@Param("sql") StringBuffer sql
    ) {
        //log.info("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfo2");
        //final String[] finalHi = {""};
        //final StringBuilder[] innserSQL = {new StringBuilder()};
        //StringBuilder innserSQL = new StringBuilder();
        //innserSQL = sql;
        return sql;
        /*
        return new SQL() {{
            log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfo");
            //innserSQL[0] = ;
            return innserSQL;
        }}.toString();

         */
    }

    public StringBuffer insertTargetTbInfoList(@Param("tableName") String tableName
            , @Param("sourceTbInfo") List<Map<String, Object>> sourceTbInfo
    ) {
        log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfoList");
        ForkJoinPool forkJoinPool = new ForkJoinPool(20);       // 멀티 스레드를 위한 스레드 풀 5개 생성
        StringBuffer sql = new StringBuffer();
        sourceTbInfo.get(0).remove("RN");
        sql.append("INSERT INTO " + tableName + "(" + sourceTbInfo.get(0).keySet().parallelStream()
                .map(Object::toString).collect(Collectors.joining(",")) + ")");

        IntStream.range(0, sourceTbInfo.size()).parallel().forEach(idx -> { // parallel을 붙여야 최대 스레드가 정해진 만큼 늘어남
//                            System.out.println(Thread.currentThread().getName()
//                                    + ", Pool 사이즈 : "+forkJoinPool.getPoolSize()
//                                    + ", Active 개수 : "+forkJoinPool.getActiveThreadCount());
            sourceTbInfo.get(idx).remove("RN");
            sql.append("\n SELECT " + sourceTbInfo.get(idx).values().parallelStream().map(name -> "'" + name + "'").collect(Collectors.joining(",")) + " FROM DUAL UNION ALL");
        });
//        ThreadHandler2 threadHandler = new ThreadHandler2(200, sourceTbInfo.size());
//        Thread.setDefaultUncaughtExceptionHandler(threadHandler);
//        ExecutorService executorService = threadHandler.getExecutorService();
//        CountDownLatch latch = threadHandler.getLatch();
       // try {
       // forkJoinPool.submit(() -> {
//            for (Map<String, Object> item : sourceTbInfo) {
//               // executorService.execute(() -> {
//                /*
//                System.out.println(Thread.currentThread().getName()
//                        + ", Pool 사이즈 : "+forkJoinPool.getPoolSize()
//                        + ", Active 개수 : "+forkJoinPool.getActiveThreadCount());
//                 */
//                    item.remove("RN");
//                    sql.append("\n SELECT " + item.values().parallelStream()
//                            .map(name -> "'" + name + "'").collect(Collectors.joining(",")) + " FROM DUAL UNION ALL");
//               //     latch.countDown();
//
//               // });
//
//            }
            //latch.await();
            sql.setLength(sql.length() - 10);  // 마지막 UNION ALL 삭제

/*
    } catch (Exception e) {
        log.info("[STEP3-E001] S1111ource to Target DB Trans 작업 에러\n" + e.getStackTrace());

    } finally {
        executorService.shutdown();
    }

 */
        //}).join();
        //Arrays.stream(sourceArray).mapToObj(Integer::toHexString) .limit(10).toList()
        /*
        IntStream.range(0, sourceTbInfo.size()).forEach(idx -> {
            sourceTbInfo.get(idx).remove("RN");
            if(idx == 0) sql.append("INSERT INTO "+"${tableName}("+sourceTbInfo.get(0).keySet().stream().map(Object::toString).collect(Collectors.joining(","))+")");
            sql.append("\n SELECT "+sourceTbInfo.get(idx).values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(","))+" FROM DUAL");
            if(sourceTbInfo.size()-1 != idx) sql.append(" UNION ALL");
        });
         */
        return sql;

    }

    public String insertTargetTbInfoList2(@Param("tableName") String tableName
            , @Param("sourceTbInfo") List<Map<String, Object>> sourceTbInfo
    ) {
        log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfoList");
        //StringBuilder sql = new StringBuilder();

        return new SQL() {{
            log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfo");
            sourceTbInfo.get(0).remove("RN");
            INSERT_INTO("${tableName}");
            INTO_COLUMNS(sourceTbInfo.get(0).keySet().stream().map(Object::toString).collect(Collectors.joining(",")));
            IntStream.range(0, sourceTbInfo.size()).forEach(idx -> {
                sourceTbInfo.get(idx).remove("RN");
                INTO_VALUES(sourceTbInfo.get(idx).values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(",")));
                if(sourceTbInfo.size()-1 != idx) ADD_ROW();
            });
//
//            sourceTbInfo.forEach(item -> {
//                item.remove("RN");
//                INTO_VALUES(item.values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(",")));
//                ADD_ROW();
//            });
            //INTO_COLUMNS(sourceTbInfo.get(0).keySet().stream().map(Object::toString).collect(Collectors.joining(",")));
            //INTO_VALUES(sourceTbInfo.values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(",")));

        }}.toString();
//
//        IntStream.range(0, sourceTbInfo.size()).forEach(idx -> {
//            sourceTbInfo.get(idx).remove("RN");
//            if(idx == 0) sql.append("INSERT INTO "+"${tableName}("+sourceTbInfo.get(0).keySet().stream().map(Object::toString).collect(Collectors.joining(","))+")");
//            sql.append("\n SELECT "+sourceTbInfo.get(idx).values().stream().map(name -> "'" + name + "'").collect(Collectors.joining(","))+" FROM DUAL");
//            if(sourceTbInfo.size()-1 != idx) sql.append(" UNION ALL");
//        });
//        return sql;
    }

    public String updateTbJobExeInfoDet(@Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq
            , @Param("tableName") String tableName
            , @Param("exeCnt") Integer exeCnt
            , @Param("exeCd") String exeCd
            , @Param("exeContent") String exeContent

    ) {
        return new SQL() {{
            log.debug("[QUERY] SQLTransJob01.sql.Step3Sql.updateTbJobExeInfoDet");
            UPDATE("TB_JOB_EXE_INFO_DET");
               SET("EXE_CD = #{exeCd}");
               SET("EXE_CNT = #{exeCnt}");
               SET("EXE_CONTENT = #{exeContent}");
               SET("CHG_DATE = SYSDATE");
               SET("CHG_USER_ID = 'admin'");
            WHERE("JOB_EXE_DATE = #{jobExeDate}");
            WHERE("JOB_EXE_SEQ = #{jobExeSeq}");
            WHERE("TRANS_TABLE_NM = #{tableName}");
        }}.toString();

    }
}
