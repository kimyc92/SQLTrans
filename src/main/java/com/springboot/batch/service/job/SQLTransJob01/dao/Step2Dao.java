package com.springboot.batch.service.job.SQLTransJob01.dao;

import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step2Mapper;
import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step3Mapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class Step2Dao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    public List<Map<String, Object>> selectTbJobExeInfoDet(String jobExeDate, Integer jobExeSeq){
        SQLTransJob01Step2Mapper mapper = sqlSession.getMapper(SQLTransJob01Step2Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectTbJobExeInfoDet(jobExeDate, jobExeSeq);
        //System.out.println("selectTbJobExeInfoDet - "+ rtnList.toString());
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectTargetTbConstrints() {
        SQLTransJob01Step2Mapper mapper = sqlSession.getMapper(SQLTransJob01Step2Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectTargetTbConstrints();
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updateTargetForeignKeyDisable(String constraintName, String tableName) throws Exception {
        SQLTransJob01Step2Mapper mapper = sqlSession.getMapper(SQLTransJob01Step2Mapper.class);
        mapper.updateTargetForeignKeyDisable(constraintName, tableName);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void deleteTargetTableTruncate(String tableName) throws Exception {
        SQLTransJob01Step2Mapper mapper = sqlSession.getMapper(SQLTransJob01Step2Mapper.class);
        mapper.deleteTargetTableTruncate(tableName);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updateTargetForeignKeyDisableAndTableTruncate(List<Map<String, Object>> tbJobExeInfoDetList
            , List<Map<String, Object>> foreignKeyWorkList) throws Exception {
        SQLTransJob01Step2Mapper mapper = sqlSession.getMapper(SQLTransJob01Step2Mapper.class);

        // Table Truncat Work
        tbJobExeInfoDetList.forEach(item -> {
            mapper.deleteTargetTableTruncate(String.valueOf(item.get("TABLE_NAME")));
            //step2Dao.deleteTargetTableTruncate(String.valueOf(String.valueOf(item.get("TABLE_NAME"))));
        });

        // Foreign Key Disable Work
        foreignKeyWorkList.forEach(item -> {
            mapper.updateTargetForeignKeyDisable(String.valueOf(item.get("CONSTRAINT_NAME")), String.valueOf(item.get("TABLE_NAME")));
            //step2Dao.updateTargetForeignKeyDisable(String.valueOf(item.get("CONSTRAINT_NAME")), String.valueOf(item.get("TABLE_NAME")));
        });
    }



    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectSourceTbInfo() {
        System.out.println("들어옴2222?");
        // RoutingDatabaseContextHolder.set(DatabaseType.Target);
        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectSourceTbInfo("TB_SAMPLE_LIST",0,100);
        System.out.println(rtnList.size());

        rtnList.forEach(item -> {
            // Foreign Key Disable Work
            System.out.println(item.toString());
        });
        return rtnList;
    }
}
