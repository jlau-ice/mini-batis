package com.carbon.core;

import java.util.Objects;

/**
 * sql 标签的所有信息
 */
public class MappedStatement {

    public MappedStatement() {
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    /**
     * sql
     */
    private String sql;

    /**
     * resultType
     */
    private String resultType;

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public String getResultType() {
        return resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}


