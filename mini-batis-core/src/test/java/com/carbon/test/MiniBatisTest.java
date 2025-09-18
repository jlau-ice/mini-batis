package com.carbon.test;

import com.carbon.core.SqlSessionFactory;
import com.carbon.core.SqlSessionFactoryBuilder;
import com.carbon.utils.Resources;
import org.junit.Test;

public class MiniBatisTest {

    @Test
    public void test() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        System.out.printf(sqlSessionFactory.toString());
    }
}
