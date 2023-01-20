package com.springboot.batch.service.job.SQLTransJob01.dao;

import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step1Mapper;
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
public class Step1Dao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    public List<Map<String, Object>> selectTbJobExeInfo(String jobExeDate, Integer jobExeSeq){
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectTbJobExeInfo(jobExeDate, jobExeSeq);
        return rtnList;
    }

    public List<Map<String, Object>> selectTbMgtDbInfo(Integer seq){
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectTbMgtDbInfo(seq);
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectHealthCheck(){
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectHealthCheck();
        System.out.println("selectHealthCheck : "+rtnList.size());
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectTargetHealthCheck(){
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectHealthCheck();
        log.info("Target DB - SELECT * FROM DUAL !!");
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectSourceHealthCheck() {
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectHealthCheck();
        log.info("Source DB - SELECT * FROM DUAL !!");
        return rtnList;
    }

    public void deleteSourceHealthCheck() {
        SQLTransJob01Step1Mapper mapper = sqlSession.getMapper(SQLTransJob01Step1Mapper.class);
        mapper.deleteSourceHealthCheck();
    }
}
