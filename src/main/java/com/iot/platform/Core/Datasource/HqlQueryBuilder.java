package com.iot.platform.Core.Datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.iot.platform.Interface.Function.ConvertFilterDataFunctionInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HqlQueryBuilder {

    private List<JoinStatement> joinStatements = new ArrayList<>();
    private List<OrderStatement> orderStatements = new ArrayList<>();
    private List<WhereStatement> whereStatements = new ArrayList<>();
    private List<WhereOrStatement> whereOrStatements = new ArrayList<>();

    private String selector = "c";
    private String primaryKey = "";
    private String table = "";
    private Map<String, ConvertFilterDataFunctionInterface> sortMap = new HashMap<>();

    public HqlQueryBuilder(String table, String primaryKey) {
        this.table = table;
        this.primaryKey = primaryKey;
    }

    public HqlQueryBuilder(String table, String primaryKey, Map<String, ConvertFilterDataFunctionInterface> sortMap) {
        this.table = table;
        this.primaryKey = primaryKey;
        this.sortMap = sortMap;
    }

    public HqlQueryData getHqlQueryData(String selector, Boolean skipOrder) {
        String query = "SELECT " + selector + " FROM " + this.getTable() + " c";

        // JOIN
        if (this.getJoinStatements().size() > 0) {
            query += " JOIN " + this.getJoinStatements().stream().map(e -> {
                return e.toString();
            }).collect(Collectors.joining(", "));
        }

        // WHERE
        Map<String, Object> param = new HashMap<String, Object>();
        if (this.getWhereStatements().size() > 0) {
            query += " WHERE " + this.getWhereStatements().stream().map(whereStatement -> {
                String _key = whereStatement.getKey();
                String _ext = "";
                while (param.containsKey(_key)) {
                    _key += "_";
                    _ext += "_";
                }

                param.put(_key, whereStatement.getValue());
                return whereStatement.toString(_ext);
            }).collect(Collectors.joining(" AND "));
        }

        // WHERE OR
        if (this.getWhereOrStatements().size() > 0) {
            if (this.getWhereOrStatements().size() == 0) {
                query += " WHERE ";
            } else {
                query += " AND ";
            }

            query += this.getWhereOrStatements().stream().map(whereOrStatement -> {
                return "(" + whereOrStatement.getListWhereStatements().stream().map(whereStatements -> {
                    return "(" + whereStatements.stream().map(whereStatement -> {
                        String _key = whereStatement.getKey();
                        String _ext = "";
                        while (param.containsKey(_key)) {
                            _key += "_";
                            _ext += "_";
                        }
                        param.put(_key, whereStatement.getValue());
                        return whereStatement.toString(_ext);
                    }).collect(Collectors.joining(" AND ")) + ")";
                }).collect(Collectors.joining(" OR ")) + ")";
            }).collect(Collectors.joining(" AND "));
        }

        // SORT BY
        Map<String, ConvertFilterDataFunctionInterface> sortMap = this.getSortMap();
        if (skipOrder == false && sortMap != null) {
            if (this.getOrderStatements() != null && this.getOrderStatements().size() > 0) {
                List<OrderStatement> sorts = this.getOrderStatements().stream()
                        .filter(e -> sortMap.containsKey(e.getActive())).collect(Collectors.toList());
                if (sorts.size() > 0) {
                    query += " ORDER BY " + sorts.stream().map(e -> e.toString()).collect(Collectors.joining(", "));
                } else {
                    query += " ORDER BY c." + this.getPrimaryKey() + " DESC";
                }
            } else {
                query += " ORDER BY c." + this.getPrimaryKey() + " DESC";
            }
        }

        HqlQueryData hqlQueryData = new HqlQueryData(query, param);
        return hqlQueryData;
    }

    public HqlQueryData getHqlQueryData(String selector) {
        return this.getHqlQueryData(selector, false);
    }

    public HqlQueryData getHqlQueryData() {
        return this.getHqlQueryData(this.getSelector(), false);
    }

}