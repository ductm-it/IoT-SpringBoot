package com.iot.platform.Core.Datasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhereStatement {

    public static WhereStatement like(String key, Object value) {
        return new WhereStatement(key, "LIKE", "%" + value + "%");
    }

    public static WhereStatement equal(String key, Object value) {
        return new WhereStatement(key, "=", value);
    }

    public static WhereStatement greater(String key, Object value) {
        return new WhereStatement(key, ">", value);
    }

    public static WhereStatement greaterOrEqual(String key, Object value) {
        return new WhereStatement(key, ">=", value);
    }

    public static WhereStatement less(String key, Object value) {
        return new WhereStatement(key, "<", value);
    }

    public static WhereStatement lessOrEqual(String key, Object value) {
        return new WhereStatement(key, "<=", value);
    }

    public static WhereStatement in(String key, Object value) {
        return new WhereStatement(key, "IN", value);
    }

    protected String key;
    protected String operator;
    protected Object value;

    public WhereStatement(String key, String operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return this.key.replaceAll("[^a-zA-Z]+", "_");
    }

    public String toString(String ext) {
        if (this.key.contains(".")) {
            return this.key + " " + this.operator + " :" + this.getKey() + ext;
        }
        return "c." + this.key + " " + this.operator + " :" + this.getKey() + ext;
    }
}