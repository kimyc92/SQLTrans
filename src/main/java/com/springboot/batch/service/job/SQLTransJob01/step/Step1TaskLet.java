package com.springboot.batch.service.job.SQLTransJob01.step;

import com.springboot.batch.config.database.DataSourceConfig;
import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.RoutingDataSource;
import com.springboot.batch.config.database.RoutingDatabaseConfig;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import com.springboot.batch.service.job.SQLTransJob01.dao.Step1Dao;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameter;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class Step1TaskLet {

    @Autowired
    Step1Dao step1Dao;

    @Autowired
    RoutingDatabaseInfo routingDatabaseInfo;

//    @Autowired
//    DataSourceConfig dataSourceConfig;
//
//    @Autowired
//    routingDatabaseConfig routingDatabaseConfig;
//
    @Transactional
    public List<Map<String, Object>> step1TaskLetProcess(SQLTransJobParameter jobParameter) throws Exception {

        //System.out.println("잡파라미터[0] : "+jobParameter.getJobExeDate());
        //System.out.println("잡파라미터[1] : "+jobParameter.getJobExeSeq());
        String jobExeDate = jobParameter.getJobExeDate();
        Integer jobExeSeq  = Integer.parseInt(jobParameter.getJobExeSeq());

        // 1. SQLTrans Job 대상 정보 조회
        List<Map<String, Object>> tbJobExeInfoList = step1Dao.selectTbJobExeInfo(jobExeDate, jobExeSeq);
        if(tbJobExeInfoList.isEmpty()){
            log.error("[STEP1-E001] SQLTrans Job 정보가 없습니다.");
            return tbJobExeInfoList;
        }

        // 2. SOURCE_DB, TARGET_DB 정보 조회
        // 2.1. SOURCE_DB 정보조회
        Integer sourceDbSeq = Integer.parseInt(String.valueOf(tbJobExeInfoList.get(0).get("SOURCE_DB_SEQ")));
        List<Map<String, Object>> sourceDbInfo = step1Dao.selectTbMgtDbInfo(sourceDbSeq);
        if(sourceDbInfo.isEmpty()){
            log.error("[STEP1-E002] SOURCE DB 정보가 없습니다.");
            return sourceDbInfo;
        }

        // 2.2. SOURCE_DB 정보 셋팅 및 동기화 확인 Routing Database Config
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDatabaseInfo.RoutingDatabaseInfo(String.valueOf(sourceDbInfo.get(0).get("DB_JDBC_URL")) // Routing Database Info Init
                , String.valueOf(sourceDbInfo.get(0).get("DB_DRIVER_CLASS_NM"))
                , String.valueOf(sourceDbInfo.get(0).get("DB_CON_NM"))
                , String.valueOf(sourceDbInfo.get(0).get("DB_CON_PW")));
        DataSource sds = DataSourceConfig.createCustomDataSource(routingDatabaseInfo); // Source Hakari Pool 정보 생성
        routingDataSource.add(DatabaseType.Source, sds);
        step1Dao.selectSourceHealthCheck();
        //step1Dao.deleteSourceHealthCheck();

        // 2.3. TARGET_DB 정보 조회
        Integer targetDbSeq = Integer.parseInt(String.valueOf(tbJobExeInfoList.get(0).get("TARGET_DB_SEQ")));
        List<Map<String, Object>> targetDbInfo = step1Dao.selectTbMgtDbInfo(targetDbSeq);
        if(targetDbInfo.isEmpty()){
            log.error("[STEP1-E003] TARGET DB 정보가 없습니다.");
            return targetDbInfo;
        }

        // 2.4. TARGET_DB 정보 셋팅 및 동기화 확인 Routing Database Config
        routingDatabaseInfo.RoutingDatabaseInfo(String.valueOf(targetDbInfo.get(0).get("DB_JDBC_URL")) // Routing Database Info Init
                , String.valueOf(targetDbInfo.get(0).get("DB_DRIVER_CLASS_NM"))
                , String.valueOf(targetDbInfo.get(0).get("DB_CON_NM"))
                , String.valueOf(targetDbInfo.get(0).get("DB_CON_PW")));
        DataSource tds = DataSourceConfig.createCustomDataSource(routingDatabaseInfo); // Source Hakari Pool 정보 생성
        routingDataSource.add(DatabaseType.Target, tds);
        step1Dao.selectTargetHealthCheck();

        return tbJobExeInfoList;
    }

}
