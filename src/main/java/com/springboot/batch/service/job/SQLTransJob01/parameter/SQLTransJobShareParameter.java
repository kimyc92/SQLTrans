package com.springboot.batch.service.job.SQLTransJob01.parameter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class SQLTransJobShareParameter<T> {
    private Map<String, T> shareDataMap;

    public SQLTransJobShareParameter() {
        this.shareDataMap = new ConcurrentHashMap<>();
    }

    public void putData(String key, T data) {
        if (this.shareDataMap == null) {
            throw new NullPointerException("[StepExecution] Data is NULL");
        }
        shareDataMap.put(key, data);
    }

    public T getData(String key) {
        if (this.shareDataMap == null) {
            throw new NullPointerException("[StepExecution] Data Key is NULL");
        }
        return shareDataMap.get(key);
    }
}
