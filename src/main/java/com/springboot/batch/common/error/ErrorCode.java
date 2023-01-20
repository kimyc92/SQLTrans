package com.springboot.batch.common.error;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ErrorCode {

    public static Map<String, String> errorCodeMap;
    static {
        errorCodeMap = new HashMap<>();
        errorCodeMap.put("rtnCd", "99");
        errorCodeMap.put("rtnMsg", "ERROR");
    }
}
