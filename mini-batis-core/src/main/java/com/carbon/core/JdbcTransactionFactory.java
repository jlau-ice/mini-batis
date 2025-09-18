package com.carbon.core;

import javax.sql.DataSource;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new JdbcTransaction(dataSource, false);
    }
}
