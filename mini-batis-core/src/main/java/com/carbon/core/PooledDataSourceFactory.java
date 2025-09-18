package com.carbon.core;

import javax.sql.DataSource;
import java.util.Map;

public class PooledDataSourceFactory implements DataSourceFactory {

    @Override
    public DataSource getDataSource(Map<String, String> properties) {
        return new PooledDataSource();
    }
}
