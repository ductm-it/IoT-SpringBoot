package com.iot.platform.Core.Datasource;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HqlQueryData {

    private String query;
    private Map<String, Object> param;

}