package com.springboot.batch.common.chunk;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBatisReader  {

	public MyBatisPagingItemReader<?> getPagingItemReader(Integer pageSize
			                                                   , SqlSessionFactory sqlSessionFactory
			                                                   , String queryId
			                                                   , Map<String, Object> parameterValues) {
		MyBatisPagingItemReader<?> reader = new MyBatisPagingItemReader<Object>();
		try {
			
			reader.setParameterValues(parameterValues);        // SELECT PARAMETER
		    reader.setPageSize(pageSize);                      // PAGING SIZE
		    reader.setSqlSessionFactory(sqlSessionFactory); 
		    reader.setQueryId(queryId);
	
		    log.info("[MYBATIS] getPagingItemReader Execute");
		    
		} catch (RuntimeException e) {
			log.info("[MYBATIS] getPagingItemReader RuntimeException ERROR");
			e.printStackTrace();
		
		} catch (Exception e) {
			log.info("[MYBATIS] getPagingItemReader Exception ERROR");
			e.printStackTrace();
			
		}
		return reader;
	}
	  
} 
