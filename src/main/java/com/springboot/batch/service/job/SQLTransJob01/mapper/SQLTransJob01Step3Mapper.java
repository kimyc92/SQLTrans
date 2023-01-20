package com.springboot.batch.service.job.SQLTransJob01.mapper;

import com.springboot.batch.service.job.SQLTransJob01.sql.Step3Sql;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Mapper
public interface SQLTransJob01Step3Mapper {

    @SelectProvider(type = Step3Sql.class, method = "selectSourceTbInfo")
    List<Map<String, Object>> selectSourceTbInfo(@Param("tableName") String tableName
            , @Param("curPage") Integer curPage
            , @Param("perPage") Integer perPage
    );

    @InsertProvider(type = Step3Sql.class, method = "insertTargetTbInfo")
    void insertTargetTbInfo(@Param("tableName") String tableName
            , @Param("keyList") String keyList
            //, @Param("valueList") List<String> valueList
            , @Param("sourceTbInfo") Map<String, Object> sourceTbInfo
    );

    @InsertProvider(type = Step3Sql.class, method = "insertTargetTbInfo2")
    void insertTargetTbInfo2(@Param("sql") StringBuffer sql);

    @InsertProvider(type = Step3Sql.class, method = "insertTargetTbInfoList")
    void insertTargetTbInfoList(@Param("tableName") String tableName
            , @Param("sourceTbInfo") List<Map<String, Object>> sourceTbInfo
    );

    @InsertProvider(type = Step3Sql.class, method = "insertTargetTbInfoList2")
    void insertTargetTbInfoList2(@Param("tableName") String tableName
            , @Param("sourceTbInfo") List<Map<String, Object>> sourceTbInfo
    );

    @UpdateProvider(type = Step3Sql.class, method = "updateTbJobExeInfoDet")
    void updateTbJobExeInfoDet(@Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq
            , @Param("tableName") String tableName
            , @Param("exeCnt") Integer exeCnt
            , @Param("exeCd") String exeCd
            , @Param("exeContent") String exeContent);
}
