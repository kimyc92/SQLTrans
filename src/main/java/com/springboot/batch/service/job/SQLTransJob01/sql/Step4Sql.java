package com.springboot.batch.service.job.SQLTransJob01.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class Step4Sql {

    public String selectTargetTbConstrints() {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step4Sql.selectTargetTbConstrints");
            SELECT("*");
            FROM("USER_CONSTRAINTS");
            WHERE("CONSTRAINT_TYPE = 'R'");
            WHERE("STATUS = 'DISABLED'");
        }}.toString();
    }

}
