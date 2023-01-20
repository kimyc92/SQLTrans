package com.springboot.batch.common.chunk;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBatisWriter  {

	public MyBatisBatchItemWriter<Object> getItemWriter(SqlSessionTemplate sqlSession
													   , String queryId) {
		MyBatisBatchItemWriter<Object> writer = new MyBatisBatchItemWriter<Object>();
		try {
			writer.setSqlSessionTemplate(sqlSession);
		    writer.setStatementId(queryId); 
		    log.info("[MYBATIS] getItemWriter Execute");
		    
		} catch (RuntimeException e) {
			log.info("[MYBATIS] getItemWriter RuntimeException ERROR");
			e.printStackTrace();
		
		} catch (Exception e) {
			log.info("[MYBATIS] getItemWriter Exception ERROR");
			e.printStackTrace();
			
		}
		return writer;
	}
	
    /*
	@Bean
    @StepScope
    public MyBatisBatchItemWriter<Object> myBatisBatchItemWriter() {
    	System.out.println("[MyBatisBatchItemWriter] ����");
    	MyBatisBatchItemWriter<Object> writer = new MyBatisBatchItemWriter<Object>();
    	try {
        	writer.setSqlSessionTemplate(sqlSessionTemplate);
            writer.setStatementId("badaro.dev.smt.sampleJob.registerUserInfo"); 
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return writer;


    	//log.info("Current Pay={}", testJobDTO);
    	
        
    	//return new MyBatisBatchItemWriterBuilder<TestJobDTO>()
    	//		.sqlSessionFactory(sqlSessionFactory)
    	//		.statementId("com.springboot.batch.sampleJob.registerUserInfo")
    	//		.build();
        
    }
    */
	
} 
