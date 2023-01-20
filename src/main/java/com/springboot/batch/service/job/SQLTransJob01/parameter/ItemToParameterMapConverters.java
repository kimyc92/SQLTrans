package com.springboot.batch.service.job.SQLTransJob01.parameter;

import com.springboot.batch.config.database.DatabaseType;
import com.springboot.batch.config.database.context.RoutingDatabaseContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemToParameterMapConverters {

    /*
    tbJobExeInfoDetList.forEach(item -> {
        mapper.deleteTargetTableTruncate(String.valueOf(item.get("TABLE_NAME")));
        //step2Dao.deleteTargetTableTruncate(String.valueOf(String.valueOf(item.get("TABLE_NAME"))));
    });
    */

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public <T> Converter<T, Map<String, Object>> createItemToParameterMapConverter(Map<String, Object> tableName) {
        //System.out.println("[Step3Chunk] createItemToParameterMapConverter");
        System.out.println("테스트테스트!!!!!");
        RoutingDatabaseContextHolder.set(DatabaseType.Target);
        return item -> {
            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz!!!!!");
            //RoutingDatabaseContextHolder.set(DatabaseType.Target);
            Map<String, Object> parameter = new HashMap<>();
            Map<String, Object> itemMap = (Map<String, Object>) item;
            itemMap.remove("RN"); // PAGE ROWNUM 컬럼 삭제
            //System.out.println("asdaSD?ASD?AS?DA?SDAS?DAS?DAS?DD?AS");
            /*
            for(String key : itemMap.keySet()){
                System.out.println("키 : " + key);
            }
*/
            String header = "";
            String data   = "";

            List<String> keyList = new ArrayList(itemMap.keySet());
            List<String> valueList = new ArrayList(itemMap.values());

//            System.out.println("keyList - "+keyList);
//            System.out.println("valueList - "+valueList);
//            System.out.println("Stringjoin_keyList --- > "+String.join(",",  keyList));
//            System.out.println("Stringjoin_valueList --- > "+String.join(",",  String.valueOf(valueList)));

/*
            for(Map.Entry<String, Object> elem : itemMap.entrySet()){
                System.out.println("키 : " + elem.getKey() + "값 : " + elem.getValue());
            }

 */
//
            /*
            for(Map.Entry<String, Object> elem : itemMap.entrySet()){
                System.out.println(elem.getValue());
            }

            System.out.println("item - "+item);
            System.out.println("map - "+tableName.get("TABLE_NAME"));


             */
            parameter.put("keyList", String.join(",",  keyList));
            parameter.put("valueList", valueList);
            parameter.put("TABLE_NAME", tableName.get("TABLE_NAME"));
            return parameter;
        };
    }

}
