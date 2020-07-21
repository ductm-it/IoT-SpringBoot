package com.iot.platform.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Function.ConvertFilterDataFunctionInterface;
import com.iot.platform.Interface.Repository.CmsRepositoryInterface;
import com.iot.platform.Interface.Repository.RestfulWithAuthRepositoryInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Util.EntityUtil;

import lombok.Getter;

public abstract class BaseRepository<T extends EntityInterface>
        implements RestfulWithAuthRepositoryInterface<T>, CmsRepositoryInterface<T> {

    @PersistenceContext
    @Getter
    protected EntityManager entityManager;

    @Getter
    protected final Class<T> type;
    @Getter
    protected final String table;
    @Getter
    protected final String primaryKey;
    @Getter
    protected final EntityPermissionInterface entityPerm;
    @Getter
    protected final Map<String, ConvertFilterDataFunctionInterface> sortMap;

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        Type superClass = this.getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.type = (Class<T>) type;
        this.primaryKey = EntityUtil.getTablePrimaryKey(this.type);
        this.sortMap = EntityUtil.getSortMap(this.type);
        this.table = EntityUtil.getTableName(this.type);
        this.entityPerm = this.type.getAnnotation(EntityPermissionInterface.class);
    }

}