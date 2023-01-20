package com.springboot.batch.service.job.SQLTransJob01.dao;

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
@Repository
public class Step3Dao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSession sqlSession;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectSourceTbInfo(String paramTableName, Integer curPage, Integer perPage) {
        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectSourceTbInfo(paramTableName, curPage, perPage);
        //System.out.println(rtnList.size());
//        rtnList.forEach(item -> {
//            System.out.println(item.toString());
//        });
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void insertTargetTbInfo(String tableName, String keyList, Map<String, Object> sourceTbInfo) {
        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        mapper.insertTargetTbInfo(tableName, keyList, sourceTbInfo);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void insertTargetTbInfo2(StringBuffer sql) {
        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        //System.out.println("sql ---- "+sql);
        mapper.insertTargetTbInfo2(sql);
        //sqlSession.commit();
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void insertTargetTbInfoList(String tableName, List<Map<String, Object>> sourceTbInfo) {

        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        mapper.insertTargetTbInfoList(tableName, sourceTbInfo);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void insertTargetTbInfoList2(String tableName, List<Map<String, Object>> sourceTbInfo) {
        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        mapper.insertTargetTbInfoList2(tableName, sourceTbInfo);

    }

    public void updateTbJobExeInfoDet(String jobExeDate, Integer jobExeSeq, String tableName
            , Integer exeCnt, String exeCd, String exeContent) {

        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        mapper.updateTbJobExeInfoDet(jobExeDate, jobExeSeq, tableName, exeCnt, exeCd, exeContent);
    }

    public void updateTbJobExeInfoDet2(String jobExeDate, Integer jobExeSeq, String tableName
            , Integer exeCnt, String exeCd, String exeContent, SqlSession thSqlSession) {



        System.out.println("dddd?????");
        System.out.println("1"+jobExeDate);
        System.out.println("2"+jobExeSeq);
        System.out.println("3"+tableName);
        System.out.println("4"+exeCnt);
        System.out.println("5"+exeCd);
        System.out.println("6"+exeContent);

        //SqlSessionTemplate sqlSession = ;

        System.out.println("TEST "+thSqlSession);
        SQLTransJob01Step3Mapper mapper = thSqlSession.getMapper(SQLTransJob01Step3Mapper.class);
        System.out.println("vbvvvv?????");

        mapper.updateTbJobExeInfoDet(jobExeDate, jobExeSeq, tableName, exeCnt, exeCd, exeContent);
    }
//
//    @Transactional(propagation= Propagation.REQUIRES_NEW)
//    public void insertTargetTbInfoList3(String tableName, List<Map<String, Object>> sourceTbInfo) {
//        SQLTransJob01Step3Mapper mapper = sqlSession.getMapper(SQLTransJob01Step3Mapper.class);
//        mapper.insertTargetTbInfoList(tableName, sourceTbInfo);
//    }
}
