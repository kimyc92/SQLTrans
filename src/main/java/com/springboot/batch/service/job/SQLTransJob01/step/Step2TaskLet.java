package com.springboot.batch.service.job.SQLTransJob01.step;


import com.springboot.batch.service.job.SQLTransJob01.dao.Step2Dao;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameter;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
public class Step2TaskLet {//extends SuperStepExecution<List<Map<String, Object>>> {

    @Autowired
    Step2Dao step2Dao;

    @Autowired
    SQLTransJobShareParameter<List<Map<String, Object>>> shareParam;

    public Boolean step2TaskLetProcess(SQLTransJobParameter jobParameter) throws Exception {
        Boolean rtn = false;
        String jobExeDate = jobParameter.getJobExeDate();
        Integer jobExeSeq  = Integer.parseInt(jobParameter.getJobExeSeq());

        // 1. SQLTrans Job Detail 대상 정보 조회
        List<Map<String, Object>> tbJobExeInfoDetList = step2Dao.selectTbJobExeInfoDet(jobExeDate, jobExeSeq);
        shareParam.putData("TB_JOB_EXE_INFO_DET_LIST", tbJobExeInfoDetList);
//        tbJobExeInfoDetList.forEach(item -> {
//            System.out.println(item.toString());
//        });
        if(tbJobExeInfoDetList.isEmpty()){
            throw new Exception("[STEP2-E001] SQLTrans Job Detail 정보가 없습니다.");
        }

        // 2. TARGET DB FOREIGN KEY, TRUNKCATE 작업
        List<Map<String, Object>> targetTbConstrintsList = step2Dao.selectTargetTbConstrints();
//        targetTbConstrintsList.forEach(item -> {
//            System.out.println(item.toString());
//        });
        if(!targetTbConstrintsList.isEmpty()) {
            log.info("[STEP2-I001] Target DB Constrints 정보 조회 완료, FOREIGN KEY DISABLED 작업을 진행합니다.");
        }

        List<Map<String, Object>> foreignKeyWorkList = tbNameInnerJoin(tbJobExeInfoDetList, targetTbConstrintsList);
        shareParam.putData("FOREIGN_KEY_WORK_LIST", foreignKeyWorkList);

//        foreignKeyWorkList.forEach(item -> {
//            System.out.println(item.toString());
//        });
        try {
            step2Dao.updateTargetForeignKeyDisableAndTableTruncate(tbJobExeInfoDetList, foreignKeyWorkList);
            rtn = true;

        } catch (Exception e) {
            throw new Exception("[STEP2-E002] Target DB Foreign Key Disable, Truncate 작업 에러\n"+ ExceptionUtils.getStackTrace(e));
        }



        return rtn;
    }

    public List<Map<String, Object>> tbNameInnerJoin(List<Map<String, Object>> map1, List<Map<String, Object>> map2) {
        return map1.stream().flatMap(m1 ->
                map2.stream()
                        .filter(m2 -> m1.get("TABLE_NAME").equals(m2.get("TABLE_NAME")))
                        .map((Map<String, Object> m2) -> {
                            Map<String, Object> mr = new HashMap<>();
                            mr.putAll(m1);
                            mr.putAll(m2);
                            return mr;
                        })
        ).collect(Collectors.toList());
    }

    // key 값으로 groupby
    public Map<String, List<Map<String, Object>>> doGrouping(List<String> columns, List<Map<String, Object>> data) {
        Map<String, List<Map<String, Object>>> output = new HashMap<>();
        for (Map<String, Object> map : data) {
            String key = "";
            for(String column :  columns) key += "".equals(key) ? (map.get(column)) : (":" + map.get(column));
            output.computeIfAbsent(key, k -> Arrays.asList(map));
        }
        return output;
    }
}
