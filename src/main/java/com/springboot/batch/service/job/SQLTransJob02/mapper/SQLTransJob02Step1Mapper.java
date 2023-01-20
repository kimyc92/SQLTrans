package com.springboot.batch.service.job.SQLTransJob02.mapper;

import com.springboot.batch.service.job.SQLTransJob02.sql.Step1Sql;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;
import java.util.Map;

@Mapper
public interface SQLTransJob02Step1Mapper {

    @SelectProvider(type = Step1Sql.class, method = "selectSourceTbInfo")
        //@SelectProvider(type = SpringBatchProviderAdapter.class, method = "select")
        //@Select("SELECT * FROM TB_JOB_EXE_INFO")
    List<Map<String, Object>> selectSourceTbInfo(@Param("tableName") String tableName);
    //List<Map<String, Object>> selectSourceTbInfo(SelectStatementProvider selectStatement);

    @InsertProvider(type = Step1Sql.class, method = "insertTargetTbInfo")
        //@SelectProvider(type = SpringBatchProviderAdapter.class, method = "select")
        //@Select("SELECT * FROM TB_JOB_EXE_INFO")
    int insertTargetTbInfo(@Param("tableName") String tableName
            , @Param("keyList") String keyList
            , @Param("valueList") List<String> valueList
            , @Param("_pagesize") Integer pagesize
    );

    /*
    @InsertProvider(type = SqlProviderAdapter.class, method="insert")
    int insertTargetTbInfo(InsertStatementProvider<List<Map<String, Object>>> insertStatement);
     */
}
