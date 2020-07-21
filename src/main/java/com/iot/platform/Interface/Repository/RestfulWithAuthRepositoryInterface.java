package com.iot.platform.Interface.Repository;

import java.util.Optional;

import javax.persistence.TypedQuery;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Core.AccessInterface;
import com.iot.platform.Interface.Entity.EntityInterface;

public interface RestfulWithAuthRepositoryInterface<T extends EntityInterface> extends RestfulRepositoryInterface<T> {

    public default ResponseData<T> create(T entity, SystemUser systemUser) {
        if (systemUser.getRoleAccess().getWriteAccess(this.getEntityPerm(), ServerActionEnum.CREATE, systemUser)) {
            entity.setCreatedBy(systemUser.getId());
            entity.setChangedBy(systemUser.getId());
            return this.create(entity);
        }
        return ResponseData.forbidden();
    }

    public default ResponseData<T> read(String id, SystemUser systemUser) {
        AccessInterface access = systemUser.getRoleAccess().getReadAccess(this.getEntityPerm(), ServerActionEnum.READ,
                systemUser);
        if (access.getCanAccess()) {
            HqlQueryBuilder hqlQueryBuilder = access.getHqlQueryBuilder();
            hqlQueryBuilder.setPrimaryKey(this.getPrimaryKey());
            hqlQueryBuilder.setTable(this.getTable());
            hqlQueryBuilder.getWhereStatements().add(WhereStatement.equal(this.getPrimaryKey(), id));
            TypedQuery<T> typedQuery = this.runHql(access.getHqlQueryBuilder());
            Optional<T> optional = typedQuery.getResultStream().findFirst();
            if (optional.isPresent()) {
                return ResponseData.success(optional.get());
            }
            return ResponseData.notFound();
        }
        return ResponseData.forbidden();
    }

    public default ResponseData<T> update(String id, T entity, SystemUser systemUser) {
        ResponseData<T> responseData = this.read(id);
        if (responseData.getStatus()) {
            Boolean canAccess = systemUser.getRoleAccess().getWriteAccess(this.getEntityPerm(), ServerActionEnum.UPDATE,
                    systemUser, responseData.getData());
            if (canAccess) {
                this.initUpdate(entity, responseData.getData());
                return ResponseData.success(this.update(entity));
            }
            return ResponseData.forbidden();
        }
        return responseData;
    }

    public default ResponseData<T> delete(String id, SystemUser systemUser) {
        ResponseData<T> responseData = this.read(id);
        if (responseData.getStatus()) {
            T oldEntity = responseData.getData();
            Boolean canAccess = systemUser.getRoleAccess().getWriteAccess(this.getEntityPerm(), ServerActionEnum.DELETE,
                    systemUser, responseData.getData());
            if (canAccess) {
                this.initUpdate(oldEntity, oldEntity, systemUser);
                oldEntity.setIsDelete(true);
                return ResponseData.success(this.update(oldEntity));
            }
            return ResponseData.forbidden();
        }
        return responseData;
    }

}