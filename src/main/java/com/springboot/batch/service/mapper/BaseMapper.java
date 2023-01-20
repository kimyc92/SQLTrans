package com.springboot.batch.service.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

/* Mybatis사용시 Mapper Interface는 반드시 1개 이상 설정해두어야 한다.
※ MapperScan이 자동으로 Mapper경로를 확인함. 미설정시 Mapper 경로를 찾을 수 없다고 표기됨
Ex : No MyBatis mapper was found in '[com.springboot.batch.mappe]' package. 
Please check your configuration.
Mapper Interface를 이용한 방법과 SqlSession을 이용한 방법이 있음
1. Mapper interface bean 선언
	장점 : Java 소스 내에 Mybatis 에 종속적인 API 를 사용하지 않음
	단점 : Mapper interface 개수가 늘어날수록 선언해야 하는 Bean 도 늘어남
2. SqlSession bean 선언 
장점 : DAO 클래스가 늘어날수록 Bean 선언이 용이함 (DAO를 @Component 로 선언하는 경우)
단점 : Sql 종류, Parameter type에 따라 SqlSession API 를 구분해서 사용해야 함 
*/

@Mapper
@Primary
public interface BaseMapper  {

}