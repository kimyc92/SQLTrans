package com.springboot.batch.service.job.SQLTransJob01.forkJoinPool;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Step3RecursiveTask extends RecursiveTask<StringBuffer> {

    // 분할해서 처리해야 될 기준 수
    private static final int THRESHOLD = 4;

    private List<Map<String, Object>> reqObj;

    // return 값
    private StringBuffer sql;

    public Step3RecursiveTask(List<Map<String, Object>> reqObj) {
        this.reqObj = reqObj;
    }

    @Override
    protected StringBuffer compute() {
        String threadName = Thread.currentThread().getName();
        StringBuffer sql = new StringBuffer();

        if (reqObj.size() > THRESHOLD) {
            System.out.println("[1] threadName - "+threadName);
            ForkJoinTask.invokeAll((ForkJoinTask<?>) createSubTasks());
        } else {
            System.out.println("[2] threadName - "+threadName);
            createSubTasks();
        }

        return sql;
    }

    private List<Map<String, Object>> createSubTasks() {
        List<Map<String, Object>> subtasks = new ArrayList<>();
        System.out.println("[3] createSubTasks");

        int listSize = reqObj.size();
        subtasks = reqObj.subList(0, (listSize + 1) / 2);
        Step3RecursiveTask subtask1 = new Step3RecursiveTask(subtasks);
        Step3RecursiveTask subtask2 = new Step3RecursiveTask(reqObj.subList((listSize + 1) / 2, listSize));

        //subtasks.add(subtask1);
        //subtasks.add(subtask2);

        return subtasks;
    }
}
