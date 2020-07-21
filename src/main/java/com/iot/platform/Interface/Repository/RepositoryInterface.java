package com.iot.platform.Interface.Repository;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.HqlQueryData;
import com.iot.platform.Interface.System.EntityPermissionInterface;

public interface RepositoryInterface<T> {

    public EntityManager getEntityManager();

    public String getTable();

    public String getPrimaryKey();

    public Class<T> getType();

    public EntityPermissionInterface getEntityPerm();

    public default TypedQuery<T> runHql(String query, Map<String, Object> param) {
        TypedQuery<T> typedQuery = this.getEntityManager().createQuery(query, this.getType());
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }

    public default <U> TypedQuery<U> runHql(String query, Map<String, Object> param, Class<U> clazz) {
        TypedQuery<U> typedQuery = this.getEntityManager().createQuery(query, clazz);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }

    public default TypedQuery<T> runHql(String query) {
        TypedQuery<T> typedQuery = this.getEntityManager().createQuery(query, this.getType());
        return typedQuery;
    }

    public default Query runSql(String query, Map<String, Object> param) {
        Query typedQuery = this.getEntityManager().createNativeQuery(query);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }

    public default Query runSql(String query) {
        Query typedQuery = this.getEntityManager().createNativeQuery(query);
        return typedQuery;
    }

    public default TypedQuery<T> runHql(HqlQueryBuilder hqlQueryBuilder) {
        HqlQueryData hqlQueryData = hqlQueryBuilder.getHqlQueryData();
        return this.runHql(hqlQueryData.getQuery(), hqlQueryData.getParam());
    }

    public default <U> TypedQuery<U> runHql(HqlQueryBuilder hqlQueryBuilder, Class<U> clazz) {
        HqlQueryData hqlQueryData = hqlQueryBuilder.getHqlQueryData();
        TypedQuery<U> typedQuery = this.getEntityManager().createQuery(hqlQueryData.getQuery(), clazz);
        for (Map.Entry<String, Object> entry : hqlQueryData.getParam().entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery;
    }

    public default void clearTransaction() {
        this.getEntityManager().clear();
    }

    public default void save(T entity) {
        this.getEntityManager().persist(entity);
    }

    public default T update(T entity) {
        return this.getEntityManager().merge(entity);
    }

}