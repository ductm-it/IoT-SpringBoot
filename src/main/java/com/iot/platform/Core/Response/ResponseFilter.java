package com.iot.platform.Core.Response;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ResponseFilter {

    String id;
    String display;
    @Setter
    Map<String, Object> addition;

    public ResponseFilter(String id, String display) {
        this.id = id;
        this.display = display;
        this.addition = new HashMap<>();
    }

}