package com.springboot.batch.service.job.SQLTransJob02.parameter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


public class ItemToParameterMapConverters {

    /*
    tbJobExeInfoDetList.forEach(item -> {
        mapper.deleteTargetTableTruncate(String.valueOf(item.get("TABLE_NAME")));
        //step2Dao.deleteTargetTableTruncate(String.valueOf(String.valueOf(item.get("TABLE_NAME"))));
    });
    */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public static <T> Converter<T, Map<String, Object>> createItemToParameterMapConverter(Map<String, Object> tableName) {

        return item -> {
            Map<String, Object> parameter = new HashMap<>();
            Map<String, Object> itemMap = (Map<String, Object>) item;
            itemMap.remove("RN"); // PAGE ROWNUM 컬럼 삭제

//            for(String key : itemMap.keySet()){
//                System.out.println("키 : " + key);
//            }

            String header = "";
            String data   = "";

            List<String> keyList = new ArrayList(itemMap.keySet());
            List<String> valueList = new ArrayList(itemMap.values());

//            System.out.println("keyList - "+keyList);
//            System.out.println("valueList - "+valueList);
//            System.out.println("Stringjoin_keyList --- > "+String.join(",",  keyList));
//            System.out.println("Stringjoin_valueList --- > "+String.join(",",  String.valueOf(valueList)));


//            for(Map.Entry<String, Object> elem : itemMap.entrySet()){
//                System.out.println("키 : " + elem.getKey() + "값 : " + elem.getValue());
//            }
//
//            for(Map.Entry<String, Object> elem : itemMap.entrySet()){
//                System.out.println(elem.getValue());
//            }



            //System.out.println("item - "+item);
            //System.out.println("item - "+item.);
            //System.out.println("map - "+tableName.get("TABLE_NAME"));

            parameter.put("keyList", String.join(",",  keyList));
            parameter.put("valueList", valueList);
            parameter.put("TABLE_NAME", tableName.get("TABLE_NAME"));
            return parameter;
        };
    }

}
