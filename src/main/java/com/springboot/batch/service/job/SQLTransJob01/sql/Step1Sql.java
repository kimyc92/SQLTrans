package com.springboot.batch.service.job.SQLTransJob01.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class Step1Sql{

    public String selectTbJobExeInfo(@Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq) {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step1Sql.selectTbJobExeInfo");
            SELECT("A.JOB_EXE_DATE, A.JOB_EXE_SEQ, A.JOB_NM, A.SOURCE_DB_SEQ, A.TARGET_DB_SEQ, A.JOB_EXE_CD");
            SELECT("A.REG_DATE, A.REG_USER_ID, A.CHG_DATE, A.CHG_USER_ID");
              FROM("TB_JOB_EXE_INFO A");
             WHERE("A.JOB_EXE_DATE = #{jobExeDate}");
             WHERE("A.JOB_EXE_SEQ  = #{jobExeSeq}");
        }}.toString();
    }

    public String selectTbMgtDbInfo(Integer seq) {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step1Sql.selectTbMgtDbInfo");
            SELECT("DB_TYPE_SEQ,DB_TYPE_CD,DB_IP,DB_PORT,DB_SID,DB_NAME,DB_JDBC_URL,DB_DRIVER_CLASS_NM");
            SELECT("DB_CON_NM,DB_CON_PW,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID");
            FROM("TB_MGT_DB_INFO");
            WHERE("DB_TYPE_SEQ = #{seq}");
        }}.toString();
    }

    public String selectHealthCheck(){
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step1Sql.selectHealthCheck");
            SELECT("*");
            FROM("DUAL");
            //SELECT("*");
            //FROM("TB_SAMPLE_LIST");
        }}.toString();
    }

    public String deleteSourceHealthCheck(){
        String sql = "DELETE FROM TB_SAMPLE_LIST WHERE ROWNUM <= 1";
        return sql;
    }

}
