package com.springboot.batch.service.job.SQLTransJob01.mapper;

import com.springboot.batch.service.job.SQLTransJob01.sql.Step2Sql;
import com.springboot.batch.service.job.SQLTransJob01.sql.Step4Sql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SQLTransJob01Step4Mapper {

    @SelectProvider(type = Step4Sql.class, method = "selectTargetTbConstrints")
    List<Map<String, Object>> selectTargetTbConstrints();

    @Update("ALTER TABLE ${tableName} ENABLE CONSTRAINT ${constraintName}")
    void updateTargetForeignKeyEnabled(@Param("constraintName") String constraintName
            , @Param("tableName") String tableName);
}
