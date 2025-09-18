package com.carbon.core;

import javax.sql.DataSource;

public interface TransactionFactory {

    Transaction getTransaction(DataSource dataSource);

}
