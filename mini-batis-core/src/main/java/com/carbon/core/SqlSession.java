package com.carbon.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

public class SqlSession {

    private SqlSessionFactory factory;

    public SqlSession(SqlSessionFactory factory) {
        this.factory = factory;
    }

    /**
     * 提交事务
     */
    public void commit() {
        factory.getTransaction().commit();
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        factory.getTransaction().rollback();
    }


    /**
     * 关闭事务
     */
    public void close() {
        factory.getTransaction().close();
    }

    public int insert(String sqlId, Object pojo) {
        int count = 0;
        try {
            Connection connection = factory.getTransaction().getConnection();
            String insertSql = factory.getMappedStatementMap().get(sqlId).getSql();
            System.out.println(factory.getMappedStatementMap().get(sqlId));
            System.out.println(factory.getMappedStatementMap().get(sqlId).getSql());
            String sql = insertSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int formIndex = 0;
            int index = 1;
            while (true) {
                int jingIndex = insertSql.indexOf("#", formIndex);
                if (jingIndex < 0) {
                    break;
                }
                System.out.println(index);
                int youKuoHaoIndex = insertSql.indexOf("}", formIndex);
                String propertyName = insertSql.substring(jingIndex + 2, youKuoHaoIndex).trim();
                System.out.println(propertyName);
                formIndex = youKuoHaoIndex + 1;
                String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                Object propertyValue = getMethod.invoke(pojo);
                preparedStatement.setString(index, propertyValue.toString());
                index++;
            }
            count = preparedStatement.executeUpdate();
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}


