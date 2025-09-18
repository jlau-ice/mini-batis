package com.carbon.core;

import javax.sql.DataSource;
import java.util.Map;

public class UnPooledDataSourceFactory implements DataSourceFactory {
    @Override
    public DataSource getDataSource(Map<String, String> properties) {
        return new UnPooledDataSource(
                properties.get("driver"),
                properties.get("url"),
                properties.get("username"),
                properties.get("password")
        );
    }
}
