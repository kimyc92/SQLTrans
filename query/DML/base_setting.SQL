﻿-- DB_TYPE_CD SETTING
INSERT INTO TB_MGT_CD(MGT_CD_TYPE,MGT_CD,MGT_CD_NM,ETC1,ETC2,ETC3,ETC4,ETC5,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT 'DB_TYPE_CD' AS MGT_CD_TYPE,'01' AS MGT_CD, 'ORACLE' AS MGT_CD_NM, 'jdbc:log4jdbc:oracle:thin:@{IP}:{PORT}:{SID}' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'DB_TYPE_CD' AS MGT_CD_TYPE,'02' AS MGT_CD, 'MYSQL' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'DB_TYPE_CD' AS MGT_CD_TYPE,'03' AS MGT_CD, 'POSTGRESQL' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL

-- TB_DB_WORK_COMMAND_LIST
/*
INSERT INTO TB_DB_WORK_COMMAND_LIST(DB_TYPE_CD,WORK_CD,WORK_COMMAND,ETC,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '01' AS DB_TYPE_CD, '01' AS WORK_CD, 'TRAUNKCATE TABLE' AS WORK_COMMAND,NULL AS ETC,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE,'admin' AS CHG_USER_ID
  FROM DUAL
UNION ALL
SELECT '01' AS DB_TYPE_CD, '02' AS WORK_CD, 'DELETE FROM' AS WORK_COMMAND,NULL AS ETC,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE,'admin' AS CHG_USER_ID
  FROM DUAL
UNION ALL
SELECT '01' AS DB_TYPE_CD, '03' AS WORK_CD, 'ALTER TABLE #{transTableNm} ENABLE CONSTRAINT #{constraintName}' AS WORK_COMMAND,NULL AS ETC,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE,'admin' AS CHG_USER_ID
  FROM DUAL
UNION ALL
SELECT '01' AS DB_TYPE_CD, '04' AS WORK_CD, 'ALTER TABLE #{transTableNm} DISABLE CONSTRAINT #{constraintName}' AS WORK_COMMAND,NULL AS ETC,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE,'admin' AS CHG_USER_ID
  FROM DUAL
  */


ALTER TABLE TB_JOB_EXE_INFO_DET DISABLE CONSTRAINT FK_JOB_EXE_INFO_TEST;


SELECT *
  FROM TB_MGT_CD


SELECT *
  FROM TB_DB_WORK_COMMAND_LIST

-- WORK_CD SETTING
INSERT INTO TB_MGT_CD(MGT_CD_TYPE,MGT_CD,MGT_CD_NM,ETC1,ETC2,ETC3,ETC4,ETC5,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT 'WORK_CD' AS MGT_CD_TYPE,'99' AS MGT_CD, '없음' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'WORK_CD' AS MGT_CD_TYPE,'01' AS MGT_CD, 'TRANKCATE' AS MGT_CD_NM, '완전삭제' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'WORK_CD' AS MGT_CD_TYPE,'02' AS MGT_CD, 'DELETE' AS MGT_CD_NM, '조건문 삭제' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'WORK_CD' AS MGT_CD_TYPE,'03' AS MGT_CD, 'FOREIGN_KEY_ENABLE' AS MGT_CD_NM, '외래키 활성화' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'WORK_CD' AS MGT_CD_TYPE,'04' AS MGT_CD, 'FOREIGN_KEY_DISABLE' AS MGT_CD_NM, '외래키 비활성화' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL

-- JOB_EXE_CD SETTING
INSERT INTO TB_MGT_CD(MGT_CD_TYPE,MGT_CD,MGT_CD_NM,ETC1,ETC2,ETC3,ETC4,ETC5,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT 'JOB_EXE_CD' AS MGT_CD_TYPE,'Y' AS MGT_CD, '작업완료' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'JOB_EXE_CD' AS MGT_CD_TYPE,'N' AS MGT_CD, '작업미완료' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'JOB_EXE_CD' AS MGT_CD_TYPE,'S' AS MGT_CD, '작업스킵' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL
UNION ALL
SELECT 'JOB_EXE_CD' AS MGT_CD_TYPE,'E' AS MGT_CD, '작업에러' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL

-- POST_WORK_CD SETTING
--INSERT INTO TB_MGT_CD(MGT_CD_TYPE,MGT_CD,MGT_CD_NM,ETC1,ETC2,ETC3,ETC4,ETC5,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
--SELECT 'POST_WORK_CD' AS MGT_CD_TYPE,'99' AS MGT_CD, '없음' AS MGT_CD_NM, NULL AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL

-- DB_JDBC_URL
INSERT INTO TB_MGT_CD(MGT_CD_TYPE,MGT_CD,MGT_CD_NM,ETC1,ETC2,ETC3,ETC4,ETC5,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT 'DB_JDBC_URL' AS MGT_CD_TYPE,'01' AS MGT_CD, 'ORACLE' AS MGT_CD_NM, 'jdbc:log4jdbc:oracle:thin:@{IP}:{PORT}:{SID}' AS ETC1, NULL AS ETC2, NULL AS ETC3, NULL AS ETC4, NULL AS ETC5,SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' CHG_USER_ID FROM DUAL

-- 관리, 소스 DB 추가
INSERT INTO TB_MGT_DB_INFO(DB_TYPE_SEQ, DB_TYPE_CD,DB_IP,DB_PORT, DB_SID, DB_NAME,DB_JDBC_URL,DB_DRIVER_CLASS_NM,DB_CON_NM,DB_CON_PW,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '1', '01' AS DB_TYPE_CD,'localhost' AS DB_IP, '1521' AS DB_PORT, 'accsys' AS DB_SID, 'SOURCE DB' AS DB_NAME
     , 'jdbc:log4jdbc:oracle:thin:@localhost:1521:accsys' AS DB_JDBC_URL
     , 'net.sf.log4jdbc.sql.jdbcapi.DriverSpy' AS DB_DRIVER_CLASS_NM
     , 'MsYkAmPzhVYx9fMOinAF66lzSJ48Yp6WRtOEW+wbprrr8FiWmIfLpzXayT59m2RQyEH06w==' AS DB_CON_NM
     , 'CIcHFdq0ON9QSdVU8rCgO0NOEE9upfwpJvIb0PdfMIVAUX40yEiybWtgfcOVZOA6S5IogQ==' AS DB_CON_PW
     , SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' AS CHG_USER_ID
  FROM DUAL;

-- 타겟 DB 추가(Oracle dbca로 database 새로 생성함)
INSERT INTO TB_MGT_DB_INFO(DB_TYPE_SEQ, DB_TYPE_CD,DB_IP,DB_PORT, DB_SID, DB_NAME,DB_JDBC_URL,DB_DRIVER_CLASS_NM,DB_CON_NM,DB_CON_PW,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '2', '01' AS DB_TYPE_CD,'localhost' AS DB_IP, '1623' AS DB_PORT, 'target' AS DB_SID, 'TARGET DB' AS DB_NAME
     , 'jdbc:log4jdbc:oracle:thin:@localhost:1623:target' AS DB_JDBC_URL
     , 'net.sf.log4jdbc.sql.jdbcapi.DriverSpy' AS DB_DRIVER_CLASS_NM
     , 'QNa84kbdlwDjfc7zC4dlxDsvLUZYLNgn8OzoBRyYlQbnpnlBRFqkO+2ErsLVwbZPjddkbg==' AS DB_CON_NM
     , 'aZbK3FrEse1QhMCWmM4zAzQeO4ZS2KGoNsmMo/gFRzaZFXjmGrIfaKbDn7UrPj31w2RwQg==' AS DB_CON_PW
     , SYSDATE AS REG_DATE, 'admin' AS REG_USER_ID, SYSDATE AS CHG_DATE, 'admin' AS CHG_USER_ID
  FROM DUAL;

-- DATA 추가(Trans 시킬 DATA)
INSERT INTO TB_SAMPLE_LIST(SAMPLE_KEY,SAMPLE_DATA1,SAMPLE_DATA2,SAMPLE_DATA3,SAMPLE_DATA4,SAMPLE_DATA5)
SELECT ROWNUM
     , DBMS_RANDOM.STRING('U', 20) AS SAMPLE_DATA1
     , DBMS_RANDOM.STRING('U', 20) AS SAMPLE_DATA2
     , DBMS_RANDOM.STRING('U', 20) AS SAMPLE_DATA3
     , DBMS_RANDOM.STRING('U', 20) AS SAMPLE_DATA4
     , DBMS_RANDOM.STRING('U', 20) AS SAMPLE_DATA5
  FROM (SELECT ROWNUM FROM DUAL CONNECT BY LEVEL <= 500) A
     , (SELECT ROWNUM FROM DUAL CONNECT BY LEVEL <= 500) B
;

DELETE FROM TB_SAMPLE_LIST


-- SAMPLE JOB 정보
INSERT INTO TB_JOB_EXE_INFO(JOB_EXE_DATE,JOB_EXE_SEQ,JOB_NM,SOURCE_DB_SEQ,TARGET_DB_SEQ,PRE_WORK_CD,PRE_WORK_SQL,POST_WORK_CD,POST_WORK_SQL,JOB_EXE_CD,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '20220522' AS JOB_EXE_DATE, 001 AS JOB_EXE_SEQ
     , '20220522_ACCSYS_TO_TARGET_DB_TRANS' AS JOB_NM
     , 1 AS SOURCE_DB_SEQ
     , 2 AS TARGET_DB_SEQ
     , '01' AS PRE_WORK_CD
     , 'TRANKCATE' PRE_WORK_SQL
     , '99' AS POST_WORK_CD
     , NULL AS POST_WORK_SQL
     , 'N' AS JOB_EXE_CD
     , SYSDATE AS REG_DATE
     , 'admin' AS REG_USER_ID
     , SYSDATE AS CHG_DATE
     , 'admin' AS CHG_USER_ID
  FROM dual;

-- SAMPLE JOB 정보
INSERT INTO TB_JOB_EXE_INFO(JOB_EXE_DATE,JOB_EXE_SEQ,JOB_NM,SOURCE_DB_SEQ,TARGET_DB_SEQ,PRE_WORK_CD,PRE_WORK_SQL,POST_WORK_CD,POST_WORK_SQL,JOB_EXE_CD,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '20220522' AS JOB_EXE_DATE, 001 AS JOB_EXE_SEQ
     , '20220522_ACCSYS_TO_TARGET_DB_TRANS' AS JOB_NM
     , 1 AS SOURCE_DB_SEQ
     , 2 AS TARGET_DB_SEQ
     , '01' AS PRE_WORK_CD
     , 'TRANKCATE' PRE_WORK_SQL
     , '99' AS POST_WORK_CD
     , NULL AS POST_WORK_SQL
     , 'N' AS JOB_EXE_CD
     , SYSDATE AS REG_DATE
     , 'admin' AS REG_USER_ID
     , SYSDATE AS CHG_DATE
     , 'admin' AS CHG_USER_ID
  FROM dual;

INSERT INTO TB_JOB_EXE_INFO_DET(JOB_EXE_DATE,JOB_EXE_SEQ,TRANS_TABLE_NM,TRANS_PAGE_SIZE,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '20220522' AS JOB_EXE_DATE
     , 001 AS JOB_EXE_SEQ
     , 'TB_SAMPLE_LIST' AS TRANS_TABLE_NM
     , 100 AS TRANS_PAGE_SIZE
     , SYSDATE AS REG_DATE
     , 'admin' AS REG_USER_ID
     , SYSDATE AS CHG_DATE
     , 'admin' AS CHG_USER_ID
  FROM dual;

INSERT INTO TB_JOB_EXE_INFO_DET(JOB_EXE_DATE,JOB_EXE_SEQ,TRANS_TABLE_NM,TRANS_PAGE_SIZE,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID)
SELECT '20220522' AS JOB_EXE_DATE
     , 001 AS JOB_EXE_SEQ
     , 'TB_JOB_EXE_INFO_DET' AS TRANS_TABLE_NM
     , 100 AS TRANS_PAGE_SIZE
     , SYSDATE AS REG_DATE
     , 'admin' AS REG_USER_ID
     , SYSDATE AS CHG_DATE
     , 'admin' AS CHG_USER_ID
  FROM dual;

SELECT *
  FROM TB_JOB_EXE_INFO_DET;

REG_USER_ID,

  TB_SAMPLE_LIST


  SELECT *
    FROM TB_JOB_EXE_INFO

    JOB_EXE_DATE,

  SELECT *
    FROM TB_JOB_EXE_INFO_DET

  SELECT *
    FROM TB_MGT_CD


A.JOB_EXE_DATE, A.JOB_EXE_SEQ, A.TRANS_TABLE_NM, A.TRANS_PAGE_SIZE, A.REG_DATE, A.REG_USER_ID, A.CHG_DATE, A.CHG_USER_ID


SELECT *
  FROM TB_MGT_DB_INFO a


SELECT *
  FROM TB_JOB_EXE_INFO_DET
-- PRE_WORK_CD : 01
-- PRE_WORK_SQL : PRE_WORK_SQL
-- POST_WORK_SQL : 99
-- POST_WORK_SQL : NULL

SELECT *
  FROM TB_MGT_CD a


SELECT COUNT(*)
  FROM dual


DB_DRIVER_CLASS_NM,

