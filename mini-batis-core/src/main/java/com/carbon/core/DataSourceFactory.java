package com.carbon.core;

import javax.sql.DataSource;
import java.util.Map;

public interface DataSourceFactory {
    // 根据配置创建 DataSource 实例
    DataSource getDataSource(Map<String, String> properties);
}
