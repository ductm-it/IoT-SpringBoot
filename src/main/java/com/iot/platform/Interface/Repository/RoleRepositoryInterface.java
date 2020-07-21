package com.iot.platform.Interface.Repository;

import java.util.Optional;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.JoinStatement;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Entity.RoleInterface;

public interface RoleRepositoryInterface<T extends RoleInterface> extends RepositoryInterface<T> {

    public default ResponseData<T> getCurrentUserRole(String userId) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey());
        hqlQueryBuilder.getWhereStatements().add(new WhereStatement("u.userId", "=", userId));
        hqlQueryBuilder.getJoinStatements().add(new JoinStatement(SystemConst.UserTable, "u", "u.roleId=c.roleId"));
        Optional<T> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
        if (optional.isPresent()) {
            return ResponseData.success(optional.get());
        }
        return ResponseData.notFound();
    }

}