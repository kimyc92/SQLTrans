package com.springboot.batch.service.job.SQLTransJob01.dao;

import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.RoutingDataSource;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step1Mapper;
import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step3Mapper;
import com.springboot.batch.service.job.SQLTransJob01.parameter.ItemToParameterMapConverters;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.springboot.batch.config.database.DataSourceConfig.createHikariDataSource;

@Slf4j
@Repository
public class Step3Dao_bak {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    @Resource(name="routeSqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    @Resource
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;  // 스프링 IoC Container를 사용하기 위한 applicationContext 주입


    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public MyBatisPagingItemReader<List<Map<String, Object>>> selectSourceTbInfo() throws Exception {
        System.out.println("selectSourceTbInfo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //Step3Mapper mapper = sqlSession.getMapper(Step3Mapper.class);
        //List<Map<String, Object>> rtnList = mapper.selectSourceTbInfo(tableName);
        //mapper.equals("selectSourceTbInfo");


        //List<Map<String, Object>> result = sqlSession.selectList(Step3Mapper.class.getName()+".selectSourceTbInfo");
        //List<Map<String, Object>> result = sqlSession.selectList("springbatchtest.selectSourceTbInfo");
        //System.out.println("클래스 결과 : "+result.toString());



        Map<String,Object> parameterValues = new HashMap<>();
        //parameterValues.put("TABLE_NAME", tableName);

//        SelectStatementProvider selectStatement =  SpringBatchUtility.selectForPaging()
//                .from(SqlTable.of(tableName))
//                //.where("TABLE_NAME", isEqualTo(tableName))
//                .build()
//                .render(); // renders for MyBatisPagingItemReader

//        MyBatisReader reader = new MyBatisReader();
//        parameterValues.put("userId","KYCZZ");
//        //parameterValues.put("status","A");
//        //parameterValues.put("status","A");
//        //MyBatisPagingItemReader<?> dd = reader.getPagingItemReader(10, sqlSessionFactory, Step3Mapper.class.getName() + ".selectSourceTbInfo", parameterValues);
//        //System.out.println("bbb    "+dd.getClass().getName());
//        return (MyBatisPagingItemReader<List<Map<String, Object>>>) reader.getPagingItemReader(10, sqlSessionFactory, Step3Mapper.class.getName()+".selectSourceTbInfo", parameterValues);

        MyBatisPagingItemReader<List<Map<String, Object>>> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSession.getSqlSessionFactory());
        reader.setQueryId(SQLTransJob01Step1Mapper.class.getName()+".selectSourceTbInfo");
        //reader.setQueryId("springbatchtest.selectSourceTbInfo");
        reader.setParameterValues(parameterValues);
        //reader.setParameterValues(SpringBatchUtility.toParameterValues(selectStatement)); // create parameter map
        reader.setPageSize(10);

        return reader;
    }
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public SynchronizedItemStreamReader<List<Map<String, Object>>> selectSourceItemReader() {
        //RoutingDatabaseContextHolder.set(DatabaseType.Source);
        Map<String,Object> parameterValues = new HashMap<>();
        //parameterValues.put("tableName", "TB_SAMPLE_LIST");
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST");
        //System.out.println("확인 - "+ SQLTransJob01Step3Mapper.class.getName());
        MyBatisPagingItemReader<List<Map<String, Object>>> notSafetyReader = new MyBatisPagingItemReaderBuilder<List<Map<String, Object>>>()
                .pageSize(5000)
                .sqlSessionFactory(sqlSession.getSqlSessionFactory())
                .queryId(SQLTransJob01Step3Mapper.class.getName()+".selectSourceTbInfo")
                .parameterValues(parameterValues)
                .build();
        //System.out.println("zzz - "+notSafetyReader);
        return new SynchronizedItemStreamReaderBuilder<List<Map<String, Object>>>()
                .delegate(notSafetyReader)
                .build();
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public MyBatisBatchItemWriter<List<Map<String, Object>>> insertTargetTbInfo() {
        System.out.println("insert 부분 itemwrite ");
        //Step3Mapper mapper = sqlSession.getMapper(Step3Mapper.class);

        MyBatisBatchItemWriter<List<Map<String, Object>>> writer = new MyBatisBatchItemWriter<>();

        writer.setSqlSessionFactory(sqlSession.getSqlSessionFactory());
        //writer.setItemToParameterConverter(convertor);x
        writer.setStatementId(SQLTransJob01Step1Mapper.class.getName()+".insertTargetTbInfo");

        return writer;
//        return new MyBatisBatchItemWriterBuilder<List<Map<String, Object>>>()
//                .sqlSessionFactory(sqlSessionFactory)
//                .statementId(String.valueOf(Step3Mapper.class.getName()+".insertTargetTbInfo"))
//                .build();
    }
    public static Map<Object, Object> dataSources = new HashMap<>();
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public MyBatisBatchItemWriter<List<Map<String, Object>>> insertTargetItemWriter() throws Exception {
        System.out.println("asdasdasdasdasdasdasdasdasdasdasd111111111111");
        dataSources.put(DatabaseType.Target, createHikariDataSource(env, "prd.target.datasource"));
        RoutingDataSource dataSourceRouter = new RoutingDataSource();
        dataSourceRouter.setTargetDataSources(dataSources);
        //dataSourceRouter.setDefaultTargetDataSource(dataSources.get(DatabaseType.Target));

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSourceRouter);
        sessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.getObject().getConfiguration().addMappers("com.springboot.batch"); // 다음 패키지 아래에 모든 @Mapper 들을 등록 함
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:**/sql/*.xml")); // 해당 경로를 mapper 스캔 설정
        //sqlSessionFactoryBean.getObject().getConfiguration().addMapper(MapperStep1.class);  // 개별로 등록하는 방법

        System.out.println("asdasdasdasdasdasdasdasdasdasdasd222222222222222");

        System.out.println("[STEP3] zz itemWriter");
        RoutingDatabaseContextHolder.set(DatabaseType.Target);
        ItemToParameterMapConverters mytest = new ItemToParameterMapConverters();
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("TABLE_NAME", "TB_SAMPLE_LIST_COPY");
        System.out.println("dddzz - "+sqlSession.getSqlSessionFactory().getConfiguration().getVariables());
                System.out.println("CHECK1 - "+sqlSession.getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource().getConnection());
        //RoutingDatabaseContextHolder.set(DatabaseType.Target);
                try {
            System.out.println("CHECK2 - "+sqlSession.getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource().getConnection().getClientInfo());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MyBatisBatchItemWriterBuilder<List<Map<String, Object>>>()
                .assertUpdates(false)
                .sqlSessionFactory(sessionFactoryBean.getObject())
                .statementId(SQLTransJob01Step3Mapper.class.getName() + ".insertTargetTbInfo")
                .itemToParameterConverter(mytest.createItemToParameterMapConverter(parameterValues))
                .build();
    }
}
