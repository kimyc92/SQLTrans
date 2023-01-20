package com.springboot.batch.service.job.SQLTransJob01.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class SQLTransChunkListener extends ChunkListenerSupport {

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("["+context.getStepContext().getId()+"] AFTER CHUNK !!!");
        System.out.println("["+context.getStepContext().getId()+"] AFTER CHUNK !!!");
        super.afterChunk(context);
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("["+context.getStepContext().getId()+"] BEFORE CHUNK !!!");
        System.out.println("["+context.getStepContext().getId()+"] BEFORE CHUNK !!!");
        context.attributeNames();
        super.beforeChunk(context);
    }
}
