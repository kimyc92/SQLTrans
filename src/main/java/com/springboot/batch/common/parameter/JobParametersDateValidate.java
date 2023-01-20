package com.springboot.batch.common.parameter;

import org.springframework.batch.core.JobParametersInvalidException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JobParametersDateValidate {
    public JobParametersDateValidate(String date) throws JobParametersInvalidException {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd"); //검증할 날짜 포맷 설정
            dateFormatParser.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
            dateFormatParser.parse(date); //대상 값 포맷에 적용되는지 확인
        } catch (ParseException e) {
            throw new JobParametersInvalidException("JobParameters Application CLI 인수 중 날짜 형식(yyyyMMdd)에 맞지 않는 값이 존재합니다.(현재 값 : "+date+")");
        } catch (Exception e) {
            throw new JobParametersInvalidException("JobParameters Application CLI 인수 중 날짜 형식(yyyyMMdd) 검증 중 오류 "+e);
        }
    }
}
