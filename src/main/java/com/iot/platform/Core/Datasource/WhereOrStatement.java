package com.iot.platform.Core.Datasource;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhereOrStatement {

    private List<List<WhereStatement>> listWhereStatements = new ArrayList<>();

}