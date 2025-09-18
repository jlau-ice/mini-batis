package com.carbon.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
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
            Element environment = (Element) environments.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']");
            // 获取事务管理器
            Element transactionElement = environment.element("transactionManager");
            Element dataSourceElement = environment.element("dataSource");
            DataSource dataSource = null;
            Transaction transaction = null;
            Map<String, MappedStatement> mappedStatementMap = null;
            return new SqlSessionFactory(transaction, mappedStatementMap);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


}


