package com.iot.platform.Core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UiConfig {

    private String name;
    private String type;
    private Map<String, Object> param;
    private List<Validator> validators;

    public UiConfig(String name, String type) {
        this.name = name;
        this.type = type;
        this.param = new HashMap<>();
        this.validators = new ArrayList<>();
    }

}