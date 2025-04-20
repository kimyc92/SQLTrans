package com.springboot.batch.common.aop;

import com.springboot.batch.common.exception.RoutingDataSourceException;
import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.RoutingDatabaseConfig;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(value=1)   // 다른 어떤 AOP 보다 먼저
// InitializingBean Bean 초기화시 Aop 구현, DisposableBean Bean 소멸시 Aop 구현
public class RoutingDatabaseAspect { //implements InitializingBean, DisposableBean {

    @Autowired
    RoutingDatabaseInfo routingDatabaseInfo;

    /*
    @Override
    public void afterPropertiesSet() throws Exception { // InitializingBean
        System.out.println(" !! InitializingBean !! ");
    }

    @Override
    public void destroy() throws Exception { // DisposableBean
        System.out.println(" !! DisposableBean !! ");
    }
    */

    // com.springboot.batch 하위 패키지 내 dao라는 이름의 패키지를 포함한 하위 패키지 중 selectTarget로 시작되는 메서드에서 동작
    /*
    @Pointcut("execution(* com.springboot.batch..dao..*.selectTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.updateTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.insertTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.deleteTarget*(..))")
    private void targetPointCut() {}
    */
    @Pointcut("execution(* com.springboot.batch..*..selectTarget*(..))" +
           "|| execution(* com.springboot.batch..*..updateTarget*(..))" +
           "|| execution(* com.springboot.batch..*..insertTarget*(..))" +
           "|| execution(* com.springboot.batch..*..deleteTarget*(..))"
            //+
           //"|| execution(* com.springboot.batch.service.job.SQLTransJob01.dao.Step3Dao.*(..))"
    )
//    @Pointcut("execution(* com.springboot.batch..dao..*.selectTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.updateTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.insertTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.deleteTarget*(..))")
    private void targetPointCut() {}

    @Pointcut("execution(* com.springboot.batch..*..selectSource*(..))" +
           "|| execution(* com.springboot.batch..*..updateSource*(..))" +
           "|| execution(* com.springboot.batch..*..insertSource*(..))" +
           "|| execution(* com.springboot.batch..*..deleteSource*(..))")
    private void sourcePointCut() {}

    @Around("targetPointCut()")
    public Object targetPointCutAround(ProceedingJoinPoint pjp) {
        log.debug("Routing to Target Database");
        // Before
        try {
//            RoutingDatabaseConfig routingDatabaseConfig = new RoutingDatabaseConfig();
//            RoutingDatabaseInfo rdi = (RoutingDatabaseInfo) routingDatabaseConfig.dataSources.get(DatabaseType.Target);
//            //System.out.println("t11111111111111 - "+rdi.toString());
//            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
            //System.out.println("t라우팅 전 데이터 확인 - "+RoutingDatabaseContextHolder.getRoutingDBInfo());

            //System.out.println("비교 - 1 "+);
            RoutingDatabaseContextHolder.set(DatabaseType.Target);
            Object result = pjp.proceed(); //핵심 기능 호출 == (이전예제) delegate.factorial(20);
            return result;
        } catch (Throwable e) {
            throw new RoutingDataSourceException("타겟 데이터 베이스 전환 중 오류 발생 \n" + e);
        } finally {
            // After
            RoutingDatabaseContextHolder.clear();
        }
    }

    @After("targetPointCut()")
    public void targetPointCutAfter(JoinPoint joinPoint) {
        log.debug("Target PointCut After Debug");

    }

    @AfterReturning(value = "targetPointCut()", returning = "result")
    public void targetPointCutAfterReturning(JoinPoint joinPoint, Object result){
        log.debug("Target PointCut AfterReturning Debug");
        log.debug("Result : "+result); // RESULT 값은 DB 결과값 그대로 받아 옴
    }

    @AfterThrowing(value = "targetPointCut()", throwing = "e")
    public void targetPointCutAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.debug("Target PointCut AfterThrowing Debug");
        log.debug("Throwable : "+e); //
    }

    @Around("sourcePointCut()")
    public Object sourcePointCutAround(ProceedingJoinPoint pjp) {
        log.debug("Routing to Source Database");
        // Before
        try {
            /*
            RoutingDatabaseInfo rdi = RoutingDatabaseContextHolder.getRoutingDBInfo(DatabaseType.Source);
            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
            System.out.println("라우팅 전 데이터 확인 - "+RoutingDatabaseContextHolder.getRoutingDBInfo());
            */

//            RoutingDatabaseConfig routingDatabaseConfig = new RoutingDatabaseConfig();
//            RoutingDatabaseInfo rdi = (RoutingDatabaseInfo) routingDatabaseConfig.dataSources.get(DatabaseType.Source);
//            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
            RoutingDatabaseContextHolder.set(DatabaseType.Source);
            Object result = pjp.proceed();
            return result;
        } catch (Throwable e) {
            throw new RoutingDataSourceException("소스 데이터 베이스 전환 중 오류 발생 \n"+e);
        } finally {
            // After
            RoutingDatabaseContextHolder.clear();
        }
    }

    //@Around("execution(*step1TaskLetProcess(..)")
    @Around("execution(* com.springboot.batch..*.Step1Dao.*(..))")
    public Object doStep1TaskLetProfiling(ProceedingJoinPoint joinPoint) throws Throwable {

        //Annotation을 읽어 들이기 위해 현재의 method를 읽어 들인다.
//        final String methodName = joinPoint.getSignature().getName();
//        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        if(method.getDeclaringClass().isInterface()){
//            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
//        }
        //Annotation을 가져온다.
        //DataSource dataSource = (DataSource) method.getAnnotation(RoutingDatabaseConfig.class);
        //Object ContextHolder = null;
        //ContextHolder.setDataSourceType(dataSource.value ());
        /*
        if(dataSource != null){
            //Method에 해당 dataSource관련 설정이 있을 경우 해당 dataSource의 value를 읽어 들인다.

        }else{
            //따로 annotation으로 datasource를 지정하지 않은 경우에는 메소드 이름으로 판단
            //get*, select* 의 경우는 default, 그 외의 경우에는 MASTER
            if(!(method.getName().startsWith(“get”) || method.getName().startsWith(“select”))){
                ContextHolder.setDataSourceType(DataSourceType.MASTER);
            }
        }
        */
        Object returnValue = joinPoint.proceed();
        //ContextHolder.clearDataSourceType();
        log.debug("@Service 끝");

        return returnValue;

    }


}
