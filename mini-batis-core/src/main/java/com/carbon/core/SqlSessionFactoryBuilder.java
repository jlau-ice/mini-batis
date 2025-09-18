package com.carbon.core;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactoryBuilder() {
    }

    public SqlSessionFactory build(InputStream inputStream) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        return sqlSessionFactory;
    }

}


