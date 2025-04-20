package com.springboot.batch.config.database;

import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static com.springboot.batch.config.database.DataSourceConfig.createHikariDataSource;

@Configuration
@Slf4j
public class RoutingDatabaseConfig {

    @Resource
    private Environment env;

    public static Map<Object, Object> dataSources = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;  // 스프링 IoC Container를 사용하기 위한 applicationContext 주입

    //@Autowired
    //TargetDatabase targetDatabase;
//
//    @Setter
//    @Getter
//    private DataSource master;
//
//    @Setter
//    @Getter
//    private DataSource source;
//
//    @Setter
//    @Getter
//    private DataSource target;

    @Bean(name="routeDataSource")
    @Primary
    public DataSource clientDatasource() {
        //System.out.println("clientDatasource clientDatasource");
        //Map<Object, Object> dataSources = new HashMap<>();
        //RoutingDataSource routingDataSource = new RoutingDataSource();
        //RoutingDatabaseInfo dbInfo = new RoutingDatabaseInfo();
                //routingDataSource.determineCurrentLookupKey();
        //RoutingDataSource dataSourceRouter = new RoutingDataSource();

        //RoutingDatabaseInfo = RoutingDatabaseInfo

            //dataSources.put(DatabaseType.Master, DataSourceConfig.createHikariDataSource(env, "prd.master.datasource"));
        //dataSources.put(DatabaseType.Source, DataSourceConfig.createHikariDataSource(env, "prd.source.datasource"));
        //dataSources.put(DatabaseType.Target, DataSourceConfig.createHikariDataSource(env, "prd.target.datasource"));
        //RoutingDatabaseContextHolder.set(DatabaseType.Master);
        //DataSource ds = createHikariDataSource(env, "prd.master.datasource");
        //routingDataSource.add(dbInfo, ds);
        /*
        dbInfo.setJdbcUrl(env.getProperty("prd.master.datasource.jdbcUrl"));
        dbInfo.setDriverClassName(env.getProperty("prd.master.datasource.driverClassName"));
        dbInfo.setUsername(env.getProperty("prd.master.datasource.username"));
        dbInfo.setPassword(env.getProperty("prd.master.datasource.password"));
        */

        /*
        routingDataSource.add(dbInfo, ds);
        System.out.println("check : "+routingDataSource.get(dbInfo));
        RoutingDataSource dataSourceRouter = new RoutingDataSource();
        System.out.println("11111111111111111");
        dataSourceRouter.determineTargetDataSource();
         */

        //dataSourceRouter.setTargetDataSources(ds);
        //System.out.println("22222222222222222");
        //DataSource ds = ;
        //System.out.println("ds - "+ds);
        //routingDataSource.add(DatabaseType.Master, createHikariDataSource(env, "prd.master.datasource"));
        //dataSources.put(DatabaseType.Master, createHikariDataSource(env, "prd.master.datasource"));
        dataSources.put(DatabaseType.Master, createHikariDataSource(env, "prd.master.datasource"));

        //routingDataSource.setTargetDataSources((Map<Object, Object>) routingDataSource.get(dbInfo));
        //routingDataSource.setDefaultTargetDataSource((Map<Object, Object>) routingDataSource.get(dbInfo));
        //System.out.println(routingDataSource.get(DatabaseType.Master));

        RoutingDataSource dataSourceRouter = new RoutingDataSource();
        dataSourceRouter.setTargetDataSources(dataSources);
        dataSourceRouter.setDefaultTargetDataSource(dataSources.get(DatabaseType.Master));
        return dataSourceRouter;
    }
//
//    @Bean(name="routeDataSource2")
//    @Primary
//    public DataSource clientDatasource2() {
//        dataSources.put(DatabaseType.Target, createHikariDataSource(env, "prd.target.datasource"));
//        RoutingDataSource dataSourceRouter = new RoutingDataSource();
//        dataSourceRouter.setTargetDataSources(dataSources);
//        dataSourceRouter.setDefaultTargetDataSource(dataSources.get(DatabaseType.Target));
//        return dataSourceRouter;
//    }

    @Bean
    public DataSource lazyRoutingDataSource(
            @Qualifier(value = "routeDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier(value = "lazyRoutingDataSource") DataSource lazyRoutingDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(lazyRoutingDataSource);
        return transactionManager;
    }

    @Bean(name = "routeSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSession(@Qualifier("routeDataSource") DataSource dataSource) throws Exception {
        log.info("routeSqlSessionFactory init");
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.getObject().getConfiguration().addMappers("com.springboot.batch"); // 다음 패키지 아래에 모든 @Mapper 들을 등록 함
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:**/sql/*.xml")); // 해당 경로를 mapper 스캔 설정
        //sqlSessionFactoryBean.getObject().getConfiguration().addMapper(MapperStep1.class);  // 개별로 등록하는 방법
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "routeSqlSessionTemplate", destroyMethod = "clearCache") // clearCache -> Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("routeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);   // BATCH  INSERT / UPDATE
    }


}
