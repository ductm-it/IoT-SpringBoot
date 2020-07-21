package com.iot.platform.Interface.Core;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;

public interface AccessInterface {

    Boolean getCanAccess();

    void setCanAccess(Boolean canAccess);

    HqlQueryBuilder getHqlQueryBuilder();

    void setHqlQueryBuilder(HqlQueryBuilder hqlQueryBuilder);

}