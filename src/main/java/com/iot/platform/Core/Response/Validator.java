package com.iot.platform.Core.Response;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Validator {

    private String type;
    private Map<String, Object> param;
    private String message;

    public Validator() {
        this.param = new HashMap<>();
    }

}