package com.springboot.batch.service.job.SQLTransJob01.mapper;

import com.springboot.batch.service.job.SQLTransJob01.sql.Step2Sql;
import com.springboot.batch.service.job.SQLTransJob01.sql.Step3Sql;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SQLTransJob01Step2Mapper {

    @SelectProvider(type = Step2Sql.class, method = "selectTbJobExeInfoDet")
    List<Map<String, Object>> selectTbJobExeInfoDet(@Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq);

    @SelectProvider(type = Step2Sql.class, method = "selectTargetTbConstrints")
    List<Map<String, Object>> selectTargetTbConstrints();

    //@DeleteProvider(type = Step2Sql.class, method = "deleteTargetForeignKeyDisable")
    @Update("ALTER TABLE ${tableName} DISABLE CONSTRAINT ${constraintName}")
    void updateTargetForeignKeyDisable(@Param("constraintName") String constraintName, @Param("tableName") String tableName);

    @Update("TRUNCATE TABLE ${tableName}")
    void deleteTargetTableTruncate(@Param("tableName") String tableName);


    @SelectProvider(type = Step3Sql.class, method = "selectSourceTbInfo")
        //@SelectProvider(type = SpringBatchProviderAdapter.class, method = "select")
        //@Select("SELECT * FROM TB_JOB_EXE_INFO")
    List<Map<String, Object>> selectSourceTbInfo(@Param("tableName") String tableName
            , @Param("page") Integer page
            , @Param("pageSize") Integer pageSize
    );

}
