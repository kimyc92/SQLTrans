package com.springboot.batch.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
public class DynamicTransVO {

    private List<Map<String, Object>> dataListMap;
    private Map<String, Object> headerListMap;

}
