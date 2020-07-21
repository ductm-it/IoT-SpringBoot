package com.iot.platform.Interface.Repository;

import java.util.Optional;

import javax.persistence.TypedQuery;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Interface.Entity.EntityInterface;

public interface RestfulRepositoryInterface<T extends EntityInterface> extends RepositoryInterface<T> {

    public default void initUpdate(T newEntity, T oldEntity, SystemUser systemUser) {
        this.initUpdate(newEntity, oldEntity);
        newEntity.setChangedBy(systemUser.getId());
    }
    
    public default void initUpdate(T newEntity, T oldEntity) {
        newEntity.setId(oldEntity.getId());
        newEntity.setVersion(oldEntity.getVersion());
        newEntity.setIsDelete(oldEntity.getIsDelete());
        newEntity.setCreatedBy(oldEntity.getCreatedBy());
        newEntity.setChangedDate(oldEntity.getChangedDate());
        newEntity.setCreatedDate(oldEntity.getCreatedDate());
    }

    public default ResponseData<T> create(T entity) {
        this.save(entity);
        return ResponseData.success(entity);
    }

    public default ResponseData<T> read(String id) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey());
        hqlQueryBuilder.getWhereStatements().add(WhereStatement.equal(this.getPrimaryKey(), id));
        TypedQuery<T> typedQuery = this.runHql(hqlQueryBuilder);
        Optional<T> optional = typedQuery.getResultStream().findFirst();
        if (optional.isPresent()) {
            return ResponseData.success(optional.get());
        }
        return ResponseData.notFound();
    }

    public default ResponseData<T> update(String id, T entity) {
        ResponseData<T> responseData = this.read(id);
        if (responseData.getStatus()) {
            this.initUpdate(entity, responseData.getData());
            entity.setId(id);
            return ResponseData.success(this.update(entity));
        }
        return responseData;
    }

    public default ResponseData<T> delete(String id) {
        ResponseData<T> responseData = this.read(id);
        if (responseData.getStatus()) {
            T entity = responseData.getData();
            entity.setIsDelete(true);
            return ResponseData.success(this.update(entity));
        }
        return responseData;
    }

}