package com.springboot.batch.service.job.SQLTransJob01.forkJoinPool;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyRecursiveTask extends RecursiveTask<StringBuffer> {
    private long workLoad = 0;

    private static StringBuffer sql;

    private List<Map<String, Object>> reqObj;

    public MyRecursiveTask(long workLoad, List<Map<String, Object>> reqObj) {
        this.workLoad = workLoad;
        this.reqObj = reqObj;
    }

    public StringBuffer getSql(){
        return sql;
    }

    protected StringBuffer compute() {
        String threadName = Thread.currentThread().getName();

        //if work is above threshold, break tasks up into smaller tasks
        if(this.workLoad > 500) {
            //System.out.println("[" + LocalTime.now() + "][" + threadName + "]" + " Splitting workLoad : " + this.workLoad);
            sleep(1000);
            List<MyRecursiveTask> subtasks =
                    new ArrayList<MyRecursiveTask>();
            subtasks.addAll(createSubtasks());

            for(MyRecursiveTask subtask : subtasks){
                subtask.fork();
            }
            StringBuffer rtnSql = new StringBuffer();
            long result = 0;
            for(MyRecursiveTask subtask : subtasks) {
                //sql.append(subtask.join());
                rtnSql.append(subtask.join());
                //System.out.println("[" + LocalTime.now() + "][" + threadName + "]" + "Received result from subtask");
            }
            //System.out.println("SQL => "+sql);
            return rtnSql;

        } else {
            sleep(1000);
            //System.out.println("[" + LocalTime.now() + "][" + " Doing workLoad myself: " + this.workLoad + " List 사이즈 - " +reqObj.size());
            StringBuffer rtnSql = new StringBuffer();
            IntStream.range(0, reqObj.size()).parallel().forEach(idx -> {
                reqObj.get(idx).remove("RN");
                rtnSql.append("\n SELECT " + reqObj.get(idx).values().parallelStream()
                        .map(name -> "'" + name + "'")
                        .collect(Collectors.joining(",")) + " FROM DUAL UNION ALL");
            });
            return rtnSql;
        }
    }

    private List<MyRecursiveTask> createSubtasks() {
        List<MyRecursiveTask> subtasks = new ArrayList<>();
        int size = reqObj.size();
        MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2, reqObj.subList(0, (size + 1) / 2));
        MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2, reqObj.subList((size + 1) / 2, size));

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
