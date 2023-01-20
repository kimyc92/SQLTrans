package com.springboot.batch.service.job.SQLTransJob01.step;

import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import com.springboot.batch.service.job.SQLTransJob01.dao.Step4Dao;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameter;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class Step4TaskLet {//extends SuperStepExecution<List<Map<String, Object>>> {

    @Autowired
    Step4Dao step4Dao;

    @Autowired
    RoutingDatabaseInfo routingDatabaseInfo;

    @Autowired
    SQLTransJobShareParameter<List<Map<String, Object>>> shareParam;

    public Boolean step4TaskLetProcess(SQLTransJobParameter jobParameter) throws Exception {
        Boolean rtn = false;
        //List<Map<String, Object>> targetTbConstrintsList = step4Dao.selectTargetTbConstrints();
        try {
            List<Map<String, Object>> foreignKeyWorkList = shareParam.getData("FOREIGN_KEY_WORK_LIST");
            if(!foreignKeyWorkList.isEmpty()) {

                log.info("[STEP4-I001] Target DB Constrints 정보 조회 완료, FOREIGN KEY ENABLED 작업을 진행합니다.");
                step4Dao.updateTargetForeignKeyEnabled(foreignKeyWorkList);
            }
        } catch (Exception e) {
            throw new Exception("[STEP4-E001] Target DB Foreign Key Enabled 작업 에러");
        }
        rtn = true;
        return rtn;
    }
}
