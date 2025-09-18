package com.carbon.core;

import com.carbon.utils.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactoryBuilder() {
    }

    public SqlSessionFactory build(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultId = environments.attributeValue("default");
            // 正确的写法，移除开头的 /
            Element environment = (Element) environments.selectSingleNode("environment[@id='" + defaultId + "']");
            //Element environment = (Element) environments.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']");
            // 获取事务管理器
            Element transactionElement = environment.element("transactionManager");
            Element dataSourceElement = environment.element("dataSource");
            List<Node> nodes = document.selectNodes("//mapper");
            List<String> SqlMapperXMLPathList = new ArrayList<String>();
            for (Node node : nodes) {
                Element mapperElement = (Element) node;
                String resource = mapperElement.attributeValue("resource");
                SqlMapperXMLPathList.add(resource);
            }
            DataSource dataSource = null;
            Transaction transaction = null;
            dataSource = getDataSource(dataSourceElement);
            transaction = getTransaction(transactionElement, dataSource);
            Map<String, MappedStatement> mappedStatementMap = getMappedStatementMap(SqlMapperXMLPathList);
            return new SqlSessionFactory(transaction, mappedStatementMap);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, MappedStatement> getMappedStatementMap(List<String> sqlMapperXMLPathList) {
        SAXReader saxReader = new SAXReader();
        Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
        sqlMapperXMLPathList.forEach(SqlMapperXMLPath -> {
            try {
                Document document = saxReader.read(Resources.getResourceAsStream(SqlMapperXMLPath));
                Element mapper = (Element) document.selectSingleNode("mapper");
                String namespace = mapper.attributeValue("namespace");
                List<Element> elements = mapper.elements();
                elements.forEach(element -> {
                    String id = element.attributeValue("id");
                    String resultType = element.attributeValue("resultType");
                    String key = namespace + "." + id;
                    String sql = element.getTextTrim();
                    MappedStatement statementEvent = new MappedStatement();
                    statementEvent.setSql(sql);
                    statementEvent.setResultType(resultType);
                    mappedStatementMap.put(key, statementEvent);
                });
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });
        return mappedStatementMap;
    }


    private DataSource getDataSource(Element dataSourceElement) {
        String type = dataSourceElement.attributeValue("type").trim().toUpperCase();
        DataSource dataSource = null;
        List<Element> elements = dataSourceElement.elements();
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            map.put(name, value);
        }
        if (Const.UN_POLLED_DATA_SOURCE.equals(type)) {
            dataSource = new UnPooledDataSource(
                    map.get("driver"),
                    map.get("url"),
                    map.get("username"),
                    map.get("password")
            );
        }
        if (Const.POOLED_DATA_SOURCE.equals(type)) {
            dataSource = new PooledDataSource();
        }
        if (Const.JNDI_DATA_SOURCE.equals(type)) {
            dataSource = new JNDIDataSource();
        }
        return dataSource;
    }


    private Transaction getTransaction(Element transactionElement, DataSource dataSource) {
        String transactionManager = transactionElement.attributeValue("transactionManager").trim().toUpperCase();
        Transaction transaction = null;
        if (Const.JDBC_TRANSACTION.equals(transactionManager)) {
            transaction = new JdbcTransaction(dataSource, false);
        }
        if (Const.MANAGER_TRANSACTION.equals(transactionManager)) {
            transaction = new ManagerTransaction();
        }
        return transaction;

    }


}


