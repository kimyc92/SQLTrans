package com.springboot.batch.config.database.context;

import com.springboot.batch.config.database.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoutingDatabaseContextHolder {

    private static ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();
//    private static Map<DatabaseType, RoutingDatabaseInfo> routingDBInfo = new HashMap<>();
//
//    public static void setRoutingDBInfo(Map<DatabaseType, RoutingDatabaseInfo> routingDBInfo) {
//        RoutingDatabaseContextHolder.routingDBInfo = routingDBInfo;
//    }
//
//    public static Map<DatabaseType, RoutingDatabaseInfo> getRoutingDBInfo(DatabaseType databaseType) {
//        return (Map<DatabaseType, RoutingDatabaseInfo>) routingDBInfo.get(databaseType);
//    }

//    public static Map<DatabaseType, RoutingDatabaseInfo> getRoutingDBInfo(){
//        return routingDBInfo;
//    }
    public static void set(DatabaseType databaseType) {
        log.debug("RoutingDatabaseContextHolder Set DB - "+databaseType);
        Assert.notNull(databaseType, "RoutingDatabase cannot be null");
        contextHolder.set(databaseType);
    }

    public static DatabaseType get() {
        log.debug("RoutingDatabaseContextHolder Get DB - "+contextHolder.get());
        if(contextHolder.get() == null){
            return DatabaseType.Master;
        } else {
            return contextHolder.get();
        }
    }

    public static void clear() {
        contextHolder.remove();
    }
//
//    public static void setRoutingDBInfo(DatabaseType databaseType, RoutingDatabaseInfo routingDatabaseInfo) {
//        System.out.println("셋팅 확인 - "+ databaseType);
//        RoutingDatabaseContextHolder.routingDBInfo.put(databaseType, routingDatabaseInfo);
//        //System.out.println("확인22 - "+getRoutingDBInfo(databaseType));
//    }
//
//    public static RoutingDatabaseInfo getRoutingDBInfo(DatabaseType databaseType) {
//        System.out.println("여기 확인 - "+ databaseType);
//        return RoutingDatabaseContextHolder.routingDBInfo.get(databaseType);
//    }

//    private static final ThreadLocal<RoutingDatabaseInfo> contextHolder = new ThreadLocal<>();
//
//    public static void set(RoutingDatabaseInfo account, RoutingDatabaseInfo dbInfo) {
//        Assert.notNull(account, "account cannot be null");
//        contextHolder.set(account);
//    }
//
//    public static RoutingDatabaseInfo get() {
//        return contextHolder.get();
//    }
//
//    public static void clear() {
//        contextHolder.remove();
//    }
}