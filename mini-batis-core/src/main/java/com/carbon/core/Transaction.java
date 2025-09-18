package com.carbon.core;

import javax.sql.DataSource;
import java.sql.Connection;

public interface Transaction {

    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 关闭事务
     */
    void close();

    /**
     * 开启事务
     */
    void begin();

    /**
     * 打开数据库连接
     */
    void openConnection();

    Connection getConnection();
}
