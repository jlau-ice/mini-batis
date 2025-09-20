package com.carbon.test;

import com.carbon.core.SqlSession;
import com.carbon.core.SqlSessionFactory;
import com.carbon.core.SqlSessionFactoryBuilder;
import com.carbon.model.User;
import com.carbon.utils.Resources;
import org.junit.Test;

public class MiniBatisTest {

    @Test
    public void test() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        System.out.printf(sqlSessionFactory.toString());
    }

    @Test
    public void test2() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User("aaa", "bb", "ccc");
        int insert = sqlSession.insert("com.carbon.mapper.UserMapper.insertUser", user);
        System.out.println(insert);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void test3() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        String id = "aaa";
        Object user = sqlSession.selectOne("com.carbon.mapper.UserMapper.selectOne", id);
        System.out.println(user);
        sqlSession.close();
    }
}
