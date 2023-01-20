package com.springboot.batch.service.job.SQLTransJob01.dao;

import com.springboot.batch.service.job.SQLTransJob01.handler.ThreadHandler;
import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step3Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
public class ThreadDao {

    public void updateTbJobExeInfoDet(String jobExeDate, Integer jobExeSeq, String tableName
            , Integer exeCnt, String exeCd, String exeContent, SqlSession sqlSession) {

        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        mapper.updateTbJobExeInfoDet(jobExeDate, jobExeSeq, tableName, exeCnt, exeCd, exeContent);
    }

}
