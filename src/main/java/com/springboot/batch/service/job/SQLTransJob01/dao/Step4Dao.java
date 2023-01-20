package com.springboot.batch.service.job.SQLTransJob01.dao;

import com.springboot.batch.service.job.SQLTransJob01.mapper.SQLTransJob01Step4Mapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class Step4Dao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectTargetTbConstrints() {
        SQLTransJob01Step4Mapper mapper = sqlSession.getMapper(SQLTransJob01Step4Mapper.class);
        List<Map<String, Object>> rtnList = mapper.selectTargetTbConstrints();
        return rtnList;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updateTargetForeignKeyEnabled(List<Map<String, Object>> targetTbConstrintsList) {
        SQLTransJob01Step4Mapper mapper = sqlSession.getMapper(SQLTransJob01Step4Mapper.class);
        targetTbConstrintsList.forEach(item -> {
            // Foreign Key Disable Work
            mapper.updateTargetForeignKeyEnabled(String.valueOf(item.get("CONSTRAINT_NAME"))
                    , String.valueOf(item.get("TABLE_NAME")));
        });
    }
}
