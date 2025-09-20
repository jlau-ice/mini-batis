package com.carbon.core;


import java.util.Map;

/**
 * 一个数据库对应一个SqlSessionFactory
 * 开启SqlSession会话
 */
public class SqlSessionFactory {

    public SqlSessionFactory() {
    }

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatementMap) {
        this.transaction = transaction;
        this.mappedStatementMap = mappedStatementMap;
    }

    /**
     * 事务管理器
     */
    private Transaction transaction;

    /**
     * SQL 语句的map  key: sqlId  value: 标签集合
     */
    private Map<String, MappedStatement> mappedStatementMap;

    public SqlSession openSession() {
        transaction.openConnection();
        return new SqlSession(this);
    }



    public Transaction getTransaction() {
        return transaction;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}


