# mini-batis

一个极简版的 MyBatis 实现，用于学习 ORM 框架的核心原理。
功能包括：SQL 映射、参数绑定、结果映射、事务管理等。

## ✨ 功能特性

- 配置管理：通过 XML/Map 维护 sqlId -> SQL 的映射关系。

- SQL 执行：支持 #{} 参数占位符，自动替换成 ?，并绑定参数。

- 结果映射：通过反射，将 ResultSet 的查询结果自动映射到 Java Bean。

- 事务管理：支持 begin/commit/rollback，基于 Connection 控制。

- 简洁 API：提供 selectOne、insert、update、delete 等方法。