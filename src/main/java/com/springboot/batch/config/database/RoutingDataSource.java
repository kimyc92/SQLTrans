package com.springboot.batch.config.database;

import com.springboot.batch.common.exception.RoutingDataSourceException;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.springboot.batch.config.database.DataSourceConfig.*;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    //static Map<DatabaseType, DataSource> dataSources = new HashMap<DatabaseType, DataSource>();
    //static Map<Object, Object> dataSources = new HashMap<Object, Object>();

    @Autowired
    RoutingDatabaseConfig routingDatabaseConfig;

    @Autowired
    RoutingDatabaseInfo routingDatabaseInfo;

    @Resource
    private Environment env;

    public DataSource get(DatabaseType databaseType) {
        return (DataSource) routingDatabaseConfig.dataSources.get(databaseType);
    }

    public void add(DatabaseType databaseType, DataSource ds) {
        routingDatabaseConfig.dataSources.put(databaseType, ds);
    }

    private boolean contains(DatabaseType databaseType) {
        return routingDatabaseConfig.dataSources.containsKey(databaseType);
    }

    @Override
    protected DataSource determineTargetDataSource() { // 최초 DataBase 연결시 사용
        DatabaseType databaseType = determineCurrentLookupKey();
        if(databaseType == null){
            throw new RoutingDataSourceException("데이터베이스 정보가 없습니다.");
        }

        try {

            if(databaseType == DatabaseType.Target){

                // 비어 있을 때만 현재 routingDatabaseInfo 값으로 Setting
                if(!(contains(databaseType))){
                    throw new RoutingDataSourceException("Target 데이터 베이스 정보가 비어 있습니다.");
                    /*
                    DataSource ds = createCustomDataSource(routingDatabaseInfo);
                    add(DatabaseType.Target, ds);
                    */
                }
                log.debug("Target DB Dynamic Setting Completed");
                return get(DatabaseType.Target);
            }

            if(databaseType == DatabaseType.Source){
                if(!(contains(databaseType))){
                    throw new RoutingDataSourceException("Source 데이터 베이스 정보가 비어 있습니다.");
                    /*
                    DataSource ds = createCustomDataSource(routingDatabaseInfo);
                    add(DatabaseType.Source, ds);
                    */
                }
                log.debug("Source DB Dynamic Setting Completed");
                return get(DatabaseType.Source);
            }
            // Default Database Config
            return get(DatabaseType.Master);

        } catch(Exception e){
            throw new RoutingDataSourceException(e);
        }
    }

    @Override
    protected DatabaseType determineCurrentLookupKey() {
        //System.out.println("RoutingDatabaseContextHolder.get() : "+ RoutingDatabaseContextHolder.get());
        return RoutingDatabaseContextHolder.get();
    }

}