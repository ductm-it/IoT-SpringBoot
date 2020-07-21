package com.iot.platform.Core.System;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Interface.Core.AccessInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Access implements AccessInterface {

    Boolean canAccess = false;
    HqlQueryBuilder hqlQueryBuilder;

}