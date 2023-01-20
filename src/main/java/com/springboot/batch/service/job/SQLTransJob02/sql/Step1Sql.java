package com.springboot.batch.service.job.SQLTransJob02.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Slf4j
public class Step1Sql {

    public String selectSourceTbInfo(@Param("tableName") String tableName) {
        return new SQL() {{
            //log.info("[QUERY] SQLTransJob02.sql.Step1Sql.selectSourceTbInfo");
            //System.out.println("[QUERY] SQLTransJob01.sql.Step3Sql.selectSourceTbInfo");
            SELECT("* FROM ( "
                    + "SELECT A.*, ROWNUM AS RN "
                    + "FROM ${TABLE_NAME} A "
                    + "WHERE ROWNUM <= (#{_page}+1)*#{_pagesize}"
                    + " ) "
                    + "WHERE RN > (#{_page}*#{_pagesize})");
        }}.toString();
    }

    public String insertTargetTbInfo(@Param("tableName") String tableName
            , @Param("keyList") String keyList
            , @Param("valueList") List<String> valueList
            , @Param("_pagesize") Integer _pagesize
    ) {
        final String[] finalHi = {""};
        return new SQL() {{
            //log.info("[QUERY] SQLTransJob02.sql.Step1Sql.insertTargetTbInfo ");
            //System.out.println("[QUERY] SQLTransJob01.sql.Step3Sql.insertTargetTbInfo");
            INSERT_INTO("${TABLE_NAME}");
            INTO_COLUMNS("${keyList}");
            for(Object value : valueList){
                finalHi[0] = "'"+String.valueOf(value)+"'";
                INTO_VALUES(finalHi[0]);
            }
        }}.toString();
    }


}
