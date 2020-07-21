package com.iot.platform.Interface.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.HqlQueryData;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Request.RequestFilter;
import com.iot.platform.Core.Request.RequestPage;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.Response.ResponseFilter;
import com.iot.platform.Core.Response.ResponsePage;
import com.iot.platform.Core.Response.UiConfig;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Function.ConvertFilterDataFunctionInterface;
import com.iot.platform.Util.EntityUtil;
import com.iot.platform.Util.ListUtil;
import com.iot.platform.Util.StringUtil;

public interface CmsRepositoryInterface<T extends EntityInterface> extends RepositoryInterface<T> {

    public Map<String, ConvertFilterDataFunctionInterface> getSortMap();

    public default List<WhereStatement> getFilterQuery(Map<String, List<Object>> filter) {
        Map<String, ConvertFilterDataFunctionInterface> sortMap = this.getSortMap();
        List<WhereStatement> list = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : filter.entrySet()) {
            if (sortMap.containsKey(entry.getKey()) && (entry.getValue() != null && entry.getValue().size() > 0)) {
                List<Object> listObject = ListUtil.unique(entry.getValue().stream().map(t -> {
                    if (sortMap.get(entry.getKey()) != null) {
                        return sortMap.get(entry.getKey()).convert(t);
                    }
                    return t;
                }).filter(t -> t != null).collect(Collectors.toList()));

                if (listObject.size() > 0) {
                    list.add(WhereStatement.in(entry.getKey(), listObject));
                }
            }
        }
        return list;
    }

    public default void updateHqlQueryBuilder(HqlQueryBuilder hqlQueryBuilder, RequestFilter requestFilter) {
        if (requestFilter.getSearch() != null) {
            String queryString = StringUtil.toQueryString(requestFilter.getSearch());
            if (queryString.length() > 0) {
                hqlQueryBuilder.getWhereStatements()
                        .add(WhereStatement.like("query", StringUtil.toQueryString(requestFilter.getSearch())));
            }
        }

        if (requestFilter.getFilter() != null) {
            List<WhereStatement> list = this.getFilterQuery(requestFilter.getFilter());
            if (list.size() >= 0) {
                hqlQueryBuilder.getWhereStatements().addAll(list);
            }
        }

        if (requestFilter.getExcludeIds() != null && requestFilter.getExcludeIds().size() > 0) {
            hqlQueryBuilder.getWhereStatements()
                    .add(new WhereStatement(this.getPrimaryKey(), "NOT IN", requestFilter.getExcludeIds()));
        }

        if (requestFilter.getOrders() != null) {
            hqlQueryBuilder.setOrderStatements(requestFilter.getOrders());
        }
    }

    public default ResponseData<Long> count(HqlQueryBuilder hqlQueryBuilder) {
        HqlQueryData hqlQueryData = hqlQueryBuilder.getHqlQueryData("COUNT(c)", true);
        TypedQuery<Long> typeQuery = this.runHql(hqlQueryData.getQuery(), hqlQueryData.getParam(), Long.class);
        List<Long> list = typeQuery.getResultList();

        if (list.size() == 1) {
            Long l = list.get(0);
            if (l != null) {
                return ResponseData.success(l);
            }
        }
        return ResponseData.success(0L);
    }

    public default ResponseData<List<ResponseFilter>> filter(RequestFilter requestFilter) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey(), this.getSortMap());
        this.updateHqlQueryBuilder(hqlQueryBuilder, requestFilter);
        TypedQuery<T> typedQuery = this.runHql(hqlQueryBuilder);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(SystemConst.ResponseFilterSize);
        List<ResponseFilter> responseFilters = typedQuery.getResultStream().map(t -> t.toResponseFilter())
                .collect(Collectors.toList());
        return ResponseData.success(responseFilters);
    }

    public default ResponseData<ResponsePage<T>> page(RequestPage requestPage) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey(), this.getSortMap());
        this.updateHqlQueryBuilder(hqlQueryBuilder, requestPage);
        ResponseData<Long> res1 = this.count(hqlQueryBuilder);
        if (res1.getStatus() == false) {
            return ResponseData.from(res1);
        }
        ResponsePage<T> responsePage = new ResponsePage<T>();
        responsePage.setPageSize(requestPage.getPageSize());
        responsePage.setPageIndex(requestPage.getPageIndex());
        responsePage.setTotalRecord(res1.getData());

        TypedQuery<T> typedQuery = this.runHql(hqlQueryBuilder);
        typedQuery.setFirstResult(requestPage.getFirstResult());
        typedQuery.setMaxResults(requestPage.getMaxResults());
        responsePage.setRecords(typedQuery.getResultList());
        return ResponseData.success(responsePage);
    }

    public default ResponseData<List<UiConfig>> uiConfig() {
        return ResponseData.success(EntityUtil.getUiConfigs(this.getType()));
    }

    public default ResponseData<List<ResponseFilter>> getNames(List<String> ids) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey(), this.getSortMap());
        if (ids.size() > 0) {
            hqlQueryBuilder.getWhereStatements().add(WhereStatement.in(this.getPrimaryKey(), ids));
        }
        return ResponseData.success(this.runHql(hqlQueryBuilder).getResultStream().map(e -> e.toResponseFilter())
                .collect(Collectors.toList()));
    }

    public default ResponseData<ResponseFilter> getName(String id) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey(), this.getSortMap());
        hqlQueryBuilder.getWhereStatements().add(WhereStatement.equal(this.getPrimaryKey(), id));
        Optional<T> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
        if (optional.isPresent()) {
            return ResponseData.success(optional.get().toResponseFilter());
        }
        return ResponseData.notFound();
    }

}