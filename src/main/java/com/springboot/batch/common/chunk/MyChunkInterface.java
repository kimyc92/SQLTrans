package com.springboot.batch.common.chunk;

import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.item.ItemWriter;

import javax.batch.api.chunk.ItemProcessor;
import java.util.List;
import java.util.Map;

public interface MyChunkInterface {

    public MyBatisPagingItemReader<?> itemReader() throws Exception;
    public ItemProcessor itemProcessor();
    public MyBatisBatchItemWriter<?> itemWriter(List<Map<String, Object>> list);

}
