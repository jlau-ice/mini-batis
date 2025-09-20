package com.carbon.core;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 执行Sql 的会话对象
 */
public class SqlSession {

    private SqlSessionFactory factory;

    public SqlSession() {
    }

    public SqlSession(SqlSessionFactory sqlSessionFactory) {
        this.factory = sqlSessionFactory;
    }

    // insert
    public int insert(String sqlId, Object pojo) {
        Connection connection = factory.getTransaction().getConnection();
        // insert in to user(id,name,age) values(#{a},#{b},#{c})
        String statementSql = factory.getMappedStatementMap().get(sqlId).getSql();
        String sql = statementSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 给问号传值
            int fromIndex = 0;
            int index = 1;
            while (true) {
                int index1 = statementSql.indexOf("#",fromIndex);
                if (index1 < 0) {
                    break;
                }
                int index2 = statementSql.indexOf("}",fromIndex);
                String property = statementSql.substring(index1 + 2, index2).trim();
                fromIndex = index2 + 1;
                String methodName = "get" + property.toUpperCase().charAt(0) + property.substring(1);
                Method declaredMethod = pojo.getClass().getDeclaredMethod(methodName);
                Object invoke = declaredMethod.invoke(pojo);
                preparedStatement.setString(index, invoke.toString());
                index++;
            }
            count = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // selectOne
    public void commit() {
        factory.getTransaction().commit();
    }

    public void rollback() {
        factory.getTransaction().rollback();
    }

    public void close() {
        factory.getTransaction().close();
    }

    public static void main(String[] args) {

    }
}


