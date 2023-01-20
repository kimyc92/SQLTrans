package com.springboot.batch.service.job.SQLTransJob01.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class Step2Sql {

    public String selectTbJobExeInfoDet(
              @Param("jobExeDate") String jobExeDate
            , @Param("jobExeSeq") Integer jobExeSeq) {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step2Sql.selectTbJobExeInfoDet");
            SELECT("A.JOB_EXE_DATE    AS JOB_EXE_DATE       ");
            SELECT("A.JOB_EXE_SEQ     AS JOB_EXE_SEQ        ");
            SELECT("A.TARGET_DB_SEQ   AS TARGET_DB_SEQ      ");
            SELECT("C.DB_TYPE_CD      AS TARGET_DB_TYPE_CD  ");
            SELECT("D.MGT_CD_NM       AS TARGET_DB_TYPE_NM  ");
            SELECT("B.TRANS_TABLE_NM  AS TABLE_NAME         ");
            SELECT("B.TRANS_PAGE_SIZE AS TRANS_PAGE_SIZE    ");
            SELECT("B.PRE_WORK_CD     AS PRE_WORK_CD        ");
            SELECT("B.PRE_WORK_SQL    AS PRE_WORK_SQL       ");
            SELECT("B.POST_WORK_CD    AS POST_WORK_CD       ");
            SELECT("B.POST_WORK_SQL   AS POST_WORK_SQL      ");
            SELECT("B.EXE_CD          AS EXE_CD             ");
            SELECT("B.EXE_CNT         AS EXE_CNT            ");
            SELECT("B.EXE_CONTENT     AS EXE_CONTENT        ");
            SELECT("B.REG_DATE        AS REG_DATE           ");
            SELECT("B.REG_USER_ID     AS REG_USER_ID        ");
            SELECT("B.CHG_DATE        AS CHG_DATE           ");
            SELECT("B.CHG_USER_ID     AS CHG_USER_ID        ");
            SELECT("'TARGET'     AS OWNER        ");
              FROM("TB_JOB_EXE_INFO A     ");
              FROM("TB_JOB_EXE_INFO_DET B ");
              FROM("TB_MGT_DB_INFO C      ");
              FROM("TB_MGT_CD D           ");
            WHERE("A.JOB_EXE_DATE = B.JOB_EXE_DATE ");
            WHERE("A.JOB_EXE_SEQ  = B.JOB_EXE_SEQ  ");
            WHERE("B.JOB_EXE_SEQ  = C.DB_TYPE_SEQ  ");
            WHERE("D.MGT_CD_TYPE  = 'DB_TYPE_CD'   ");
            WHERE("D.MGT_CD       = C.DB_TYPE_CD   ");
            WHERE("A.JOB_EXE_DATE = #{jobExeDate}");
            WHERE("A.JOB_EXE_SEQ  = #{jobExeSeq}");
        }}.toString();
    }

    public String selectTargetTbConstrints() {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step2Sql.selectTargetTbConstrints");
            SELECT("*");
              FROM("USER_CONSTRAINTS");
             WHERE("CONSTRAINT_TYPE = 'R'");
             WHERE("STATUS = 'ENABLED'");
        }}.toString();
    }

    public String selectSourceTbInfo(@Param("tableName") String tableName
            , @Param("page") Integer page
            , @Param("pageSize") Integer pageSize
    ) {
        return new SQL() {{
            log.info("[QUERY] SQLTransJob01.sql.Step3Sql.selectSourceTbInfo");
            //System.out.println("[QUERY] SQLTransJob01.sql.Step3Sql.selectSourceTbInfo");
/*
            SELECT("* FROM ( "
                    + "SELECT A.*, ROWNUM AS RN "
                    + "FROM ${tableName} A "
                   // + "WHERE ROWNUM <= (#{page}+1)*#{pageSize}"
                    + " ) "
            );//+ "WHERE RN > (#{page}*#{pageSize})");


 */

            SELECT("A.*");
            FROM("TB_MGT_DB_INFO A");

        }}.toString();

    }
}
