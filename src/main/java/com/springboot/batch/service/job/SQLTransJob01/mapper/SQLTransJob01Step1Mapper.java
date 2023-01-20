package com.springboot.batch.service.job.SQLTransJob01.mapper;

import com.springboot.batch.service.job.SQLTransJob01.sql.Step1Sql;
import com.springboot.batch.service.job.SQLTransJob01.sql.Step3Sql;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface SQLTransJob01Step1Mapper {

    @SelectProvider(type = Step1Sql.class, method = "selectTbJobExeInfo")
    List<Map<String, Object>> selectTbJobExeInfo(@Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq);

    @SelectProvider(type = Step1Sql.class, method = "selectTbMgtDbInfo")
    List<Map<String, Object>> selectTbMgtDbInfo(Integer seq);

    @SelectProvider(type = Step1Sql.class, method = "selectHealthCheck")
    List<Map<String, Object>> selectHealthCheck();

    @SelectProvider(type = Step3Sql.class, method = "selectSourceTbInfo")
        //@SelectProvider(type = SpringBatchProviderAdapter.class, method = "select")
        //@Select("SELECT * FROM TB_JOB_EXE_INFO")
    List<Map<String, Object>> selectSourceTbInfo(@Param("tableName") String tableName
            , @Param("page") Integer page
            , @Param("pageSize") Integer pageSize
    );

    @DeleteProvider(type = Step1Sql.class, method = "deleteSourceHealthCheck")
    void deleteSourceHealthCheck();
}
