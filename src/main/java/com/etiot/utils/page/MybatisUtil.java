package com.etiot.utils.page;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mybatis工具类，获取完整的执行sql语句。
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
public class MybatisUtil {
    private static final Logger logger = LoggerFactory.getLogger(MybatisUtil.class);

    /**
     * 根据传入的参数获取完整的静态sql。<br/>
     * 详细描述：根据mybatis sqlmapperId、传入参数的信息获取静态SQL。<br/>
     * 使用方式：MybatisUtil.getSql方式即可调用。
     * @param sessionFactory mybatis的session工厂。
     * @param mapperId mapper中定义sql的id值。
     * @param parameterObject sql的参数条件。
     * @param rowBounds 分页对象。
     * @return 完整的sql语句。
     */
    public static String getSql(SqlSessionFactory sessionFactory, String mapperId,
                                Object parameterObject, RowBounds rowBounds) {
        MappedStatement mapStmt = sessionFactory.getConfiguration().getMappedStatement(mapperId);
        BoundSql boundSql = mapStmt.getBoundSql(parameterObject);
        String _sql = boundSql.getSql();
        String dbType = getDBType(mapStmt);
        Dialect dialect = MybatisUtil.getDBDialect(dbType);
        if (boundSql.getParameterMappings() != null) {
            MetaObject metaObject = parameterObject == null ? null : mapStmt.getConfiguration()
                .newMetaObject(parameterObject);
            for (int i = 0; i < boundSql.getParameterMappings().size(); i++ ) {
                ParameterMapping parameterMapping = boundSql.getParameterMappings().get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (mapStmt.getConfiguration().getTypeHandlerRegistry()
                        .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (value instanceof String) {
                        _sql = _sql.replaceFirst("[?]", "'" + value + "'");
                    } else if (value instanceof Date) {
                        _sql = _sql.replaceFirst("[?]", dialect.getDateCondByValue(value));
                    } else {
                        _sql = _sql.replaceFirst("[?]", value.toString());
                    }
                }
            }
        }
        if (rowBounds != null && dialect.supportsLimit()) {
            if (rowBounds instanceof PageQuery) {
                PageQuery pageQuery = (PageQuery)rowBounds;
                _sql = dialect.getSortString(_sql, pageQuery.getSortRule());
            }
            if (dialect.supportsLimitOffset()) {
                _sql = dialect.getLimitString(_sql, rowBounds.getOffset(), rowBounds.getLimit());
            } else {
                _sql = dialect.getLimitString(_sql, 0, rowBounds.getLimit());
            }
        }
        return _sql.replaceAll("\n", " ");
    }

    /**
     * 根据传入的参数获取完整的静态sql，不带分页对象。<br/>
     * 详细描述：根据mybatis sqlmapperId、传入参数的信息获取静态SQL，该方法可以不带分页对象。<br/>
     * 使用方式：MybatisUtil.getSql方式即可调用。
     * @param sessionFactory mybatis的session工厂。
     * @param mapperId mapper中定义sql的id值。
     * @param parameterObject sql的参数条件。
     * @return 完整的sql语句。
     */
    public static String getSql(SqlSessionFactory sessionFactory, String mapperId,
                                Object parameterObject) {
        return getSql(sessionFactory, mapperId, parameterObject, null);
    }

    /**
     * 根据传入的参数获取完整的静态sql，不带分页对象。<br/>
     * 详细描述：根据传入参数的信息获取静态SQL，该方法可以不带分页对象。<br/>
     * 使用方式：MybatisUtil.getSql方式即可调用。
     * @param sessionFactory mybatis的session工厂。
     * @param methodName 调用的接口方法名称。
     * @param parameterObj sql的参数条件。
     * @param clazz 接口的class。
     * @return 完整的sql语句。
     */
    public static String getSql(SqlSessionFactory sessionFactory, Class<?> clazz,
                                String methodName, Object parameterObj) {
        return getSql(sessionFactory, clazz, methodName, parameterObj, null);
    }

    /**
     * 根据传入的参数获取完整的静态sql，可带分页对象的。<br/>
     * 详细描述：根据传入参数的信息获取静态SQL，可带分页对象的。<br/>
     * 使用方式：MybatisUtil.getSql方式即可调用。
     * @param sessionFactory mybatis的session工厂。
     * @param methodName 调用的接口方法名称。
     * @param parameterObj sql的参数条件。
     * @param clazz 接口的class。
     * @param rowBounds 分页对象。
     * @return 完整的sql语句。
     */
    public static String getSql(SqlSessionFactory sessionFactory, Class<?> clazz,
                                String methodName, Object parameterObj, RowBounds rowBounds) {
        String mapperId = clazz.getName() + "." + methodName;
        return getSql(sessionFactory, mapperId, parameterObj, rowBounds);

    }

    /**
     * 获取数据库类型。<br/>
     * 详细描述：根据MappedStatement,获取数据库类型名称。<br/>
     * 使用方式：MybatisUtil.getDBType方式即可调用。
     * @param mapStmt mybatis转换的mapper对象。
     * @return 数据库类型名称。
     */
    public static String getDBType(MappedStatement mapStmt) {
        String dbType = mapStmt.getConfiguration().getDatabaseId();
        if (dbType == null) {
            Connection conn = null;
            try {
                conn = mapStmt.getConfiguration().getEnvironment().getDataSource().getConnection();
                dbType = conn.getMetaData().getDatabaseProductName().toLowerCase();

            } catch (SQLException e) {
                logger.error("访问数据库错误", e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error("关闭数据库错误.", e);
                }
            }
        }
        return dbType;
    }

    /**
     * 根据数据类型名称获取方言对象。<br/>
     * 详细描述：根据数据库类型获取相应的数据库方言对象。<br/>
     * 使用方式：MybatisUtil.getDBDialect方式即可调用。
     * @param dbType 数据库类型名称。
     * @return Dialect方言对象。
     */
    public static Dialect getDBDialect(String dbType) {
        Dialect paginationDialect = null;
        dbType = dbType.toLowerCase();
/*        if (dbType.startsWith("oracle")) {
            paginationDialect = SpringContextHolder.getBean(OracleDialect.class);
        } else if (dbType.startsWith("db2")) {
            paginationDialect = SpringContextHolder.getBean(DB2Dialect.class);
        } else*/ if (dbType.startsWith("mysql")) {
            paginationDialect = SpringContextHolder.getBean(MySQLDialect.class);
        } else {
            logger.error("未实现{}数据库的分页方言", dbType);
        }
        return paginationDialect;
    }
    
}