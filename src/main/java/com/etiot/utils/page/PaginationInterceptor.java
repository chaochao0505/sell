package com.etiot.utils.page;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 为mybatis提供基于方言(Dialect)的分页查询的插件，将拦截Executor.query()方法实现分页方言的插入，
 * 如果带有分页参数，即生成分页查询sql语句在执行。同时还提供了将最近执行的5条sql保存到session中，供前台调试 角色用户查看。
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

    private static int MAPPED_STATEMENT_INDEX = 0;

    private static int PARAMETER_INDEX = 1;

    private static int ROWBOUNDS_INDEX = 2;

    /**
     * mybatis的拦截器，为了拦截执行的sql。<br/> 详细描述：基于mybatis的插件，实现了Interceptor的接口，在该方法中实现了自己的拦截机制，为了实现分页sql的拼装。<br/>
     * 使用方式：无需调用该方法，在sql执行时会自动被该方法拦截到。
     * 
     * @param invocation mybatis的反射实体对象，保存mybatis的所有操作数据库的上下文参数。
     * @return 返回Object对象，如果有分页参数即返回PageList对象，否则会调用invoke继续执行后面操作。
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object intercept(Invocation invocation) throws Throwable {
        RowBounds rowBounds = (RowBounds)invocation.getArgs()[ROWBOUNDS_INDEX];
        recordSql(invocation);
        if (rowBounds != null && rowBounds instanceof PageQuery) {
            Paginator paginator = processIntercept(invocation.getArgs());
            if (paginator != null) {
                List<Object> list = (List<Object>)invocation.proceed();
                return new PageList(list, paginator);
            }
            return null;
        } else {
            return invocation.proceed();
        }
    }

    /**
     * 缓存最近执行的5条sql到session中。<br/> 详细描述：在拦截方法中根据invocation参数，单独获取最近执行的5条sql并保存到session中供前台查看。<br/> 使用方式：在拦截方法中会调用该方法。
     * 
     * @param invocation mybatis的反射实体对象，保存mybatis的所有操作数据库的上下文参数。
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void recordSql(Invocation invocation) {
        String pageQuerySql = getPageQuerySql(invocation.getArgs());
        if (null != RequestContextHolder.getRequestAttributes()) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .getRequest();
            Object objMap = request.getSession().getAttribute("debugSQL");
            LinkedHashMap debugSqlMap = null;
            if (null != objMap) {
                debugSqlMap = (LinkedHashMap)objMap;
                // 将值设置到debugSqlMap
                debugSqlMap.put(request.getRequestURI(), pageQuerySql);
                // 新的debugSqlMap
                LinkedHashMap myDebugSqlMap = new LinkedHashMap();
                // 总计条数
                int listSize = debugSqlMap.entrySet().size();
                int indexNum = 0;
                // 取最新的5条SQL设置PUT
                for (Iterator it = debugSqlMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry)it.next();
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    if ( (listSize - indexNum) <= 5) {
                        myDebugSqlMap.put(key, value);
                    }
                    indexNum++ ;
                }
                debugSqlMap = myDebugSqlMap;
            } else {
                debugSqlMap = new LinkedHashMap();
                debugSqlMap.put(request.getRequestURI(), pageQuerySql);
            }
            request.getSession().setAttribute("debugSQL", debugSqlMap);
        }
    }

    /**
     * 返回完整的sql语句并替换了参数。<br/> 详细描述：根据mybatis的参数数组，解析sql和绑定的参数，以获取完整的sql语句。<br/> 使用方式：在recordSql方法中调用了该方法以获取完整的sql语句。
     * 
     * @param queryArgs mybatis的对象参数数组。
     * @return 完整的sql语句，并且参数也被替换。
     */
    public String getPageQuerySql(final Object[] queryArgs) {
        MappedStatement mappedStatement = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String dbType = MybatisUtil.getDBType(mappedStatement);
        Dialect dialect = MybatisUtil.getDBDialect(dbType);
        String sql = boundSql.getSql().trim();
        if (rowBounds != null && rowBounds instanceof PageQuery) {
            PageQuery pageQuery = (PageQuery)rowBounds;
            int offset = pageQuery.getOffset();
            int limit = pageQuery.getLimit();
            if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
                sql = dialect.getSortString(sql, pageQuery.getSortRule());
                if (dialect.supportsLimitOffset()) {
                    sql = dialect.getLimitString(sql, offset, limit);
                } else {
                    sql = dialect.getLimitString(sql, 0, limit);
                }
            }
        }
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        if (parameterMappingList != null) {
            MetaObject metaObject = parameter == null ? null : mappedStatement.getConfiguration().newMetaObject(
                parameter);
            ParameterMapping parameterMapping = null;
            String propertyName = null;
            for (int i = 0; i < parameterMappingList.size(); i++ ) {
                parameterMapping = parameterMappingList.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value = null;
                    propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (mappedStatement.getConfiguration().getTypeHandlerRegistry()
                        .hasTypeHandler(parameter.getClass())) {
                        value = parameter;
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (value != null) {
                        if (value instanceof String) {
                            sql = sql.replaceFirst("[?]", "'" + value + "'");
                        } else if (value instanceof Date) {
                            sql = sql.replaceFirst("[?]", dialect.getDateCondByValue(value));
                        } else {
                            sql = sql.replaceFirst("[?]", value.toString());
                        }
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 返回封装好的分页器Paginator。<br/> 详细描述：根据分页条件组装成带有分页和排序的sql语句，会先执行查询总条数，并把当前分页的信息一起封装到Paginator对象中返回，
     * 在把完整的sql信息存入到mybatis中，后续进行执行sql操作。<br/> 使用方式：在mybatis的拦截方法中调用该方法，返回分页器。
     * 
     * @param queryArgs mybatis的对象参数数组。
     * @return 封装好的Paginator分页器。
     */
    public Paginator processIntercept(final Object[] queryArgs) throws Exception {
        MappedStatement mappedStatement = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        final PageQuery pageQuery = (PageQuery)queryArgs[ROWBOUNDS_INDEX];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        int offset = pageQuery.getOffset();
        int limit = pageQuery.getLimit();
        Paginator paginator = null;
        Connection connection = null;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            String dbType = MybatisUtil.getDBType(mappedStatement);
            Dialect dialect = MybatisUtil.getDBDialect(dbType);
            if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
                String sql = boundSql.getSql().trim();
                try {
                    // 计算数据总量
                    if ("0".equals(pageQuery.getIsCount())) {
                        parameterMapSetValue(parameter, "countFlag", "true");
                        BoundSql countBoundSql = mappedStatement.getBoundSql(parameter);
                        int count = getCountSql(countBoundSql.getSql().trim(), connection, mappedStatement, parameter,
                            countBoundSql, dialect);
                        parameterMapSetValue(parameter, "countFlag", "false");
                        paginator = new Paginator(pageQuery.getCurPage(), limit, count);
                    } else {
                        paginator = new Paginator(pageQuery.getCurPage(), limit, 0);
                    }
                    // 计算数据总计
                    if ("0".equals(pageQuery.getIsTotal())) {
                        parameterMapSetValue(parameter, "totalFlag", "true");
                        BoundSql countBoundSql = mappedStatement.getBoundSql(parameter);
                        List<Object> totalList = getTotalSql(countBoundSql.getSql().trim(), connection,
                            mappedStatement, parameter, countBoundSql, dialect);
                        parameterMapSetValue(parameter, "totalFlag", "false");
                        paginator.setTotalList(totalList);
                    } else {
                        paginator.setTotalList(new ArrayList<Object>());
                    }
                } catch (SQLException e) {
                    logger.error("访问数据库错误 !", e);
                    throw e;
                }
                sql = dialect.getSortString(sql, pageQuery.getSortRule());
                if (dialect.supportsLimitOffset()) {
                    sql = dialect.getLimitString(sql, offset, limit);
                    offset = RowBounds.NO_ROW_OFFSET;
                } else {
                    sql = dialect.getLimitString(sql, 0, limit);
                }
                limit = RowBounds.NO_ROW_LIMIT;
                queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
                BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sql);
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
                queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
            }
        } catch (SQLException e) {
            logger.error("执行查询分页Count语句或总计语句时出错 ！", e);
            throw e;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("关闭数据库出错!", e);
            }
        }
        return paginator;
    }

    @SuppressWarnings("unchecked")
    private static void parameterMapSetValue(Object obj, String key, String value) {
        if (obj instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>)obj;
            map.put(key, value);
        }
    }

    /**
     * 执行解析好的sql，获取总数据量信息。<br/> 详细描述：根据解析的完整sql语句，以便获取该sql查询出来的总数量信息，供前台表格展示。<br/>
     * 使用方式：在processIntercept方法中调用了该方法查询数据量信息。
     * 
     * @param sql 解析好的完整sql语句。
     * @param connection 当前mybatis操作数据库的连接对象。
     * @param mappedStatement mapper中每个sql节点的对象化。
     * @param parameterObject sql的参数对象。
     * @param boundSql mybatis的绑定sql对象。
     * @param dialect 数据库方言对象。
     * @return 总数据量。
     */
    public static int getCountSql(final String sql, final Connection connection, final MappedStatement mappedStatement,
                                  final Object parameterObject, final BoundSql boundSql, Dialect dialect)
        throws SQLException {
        String count_sql = "";
        String sub = sql.toLowerCase().substring(0, sql.toLowerCase().indexOf("from") + 4);
        if (sub.matches("(select)[\n|\r|\t|\\s]+(count\\(1\\))[\n|\r|\t|\\s]+(from)")) {
            count_sql = sql;
        } else {
            count_sql = dialect.getCountString(sql);
        }
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(count_sql);
            ExtDefaultParameterHandler handler = new ExtDefaultParameterHandler(mappedStatement, parameterObject,
                boundSql);
            handler.setParameters(countStmt);
            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            logger.error("执行查询Count语句时出错 ！", e);
            return 0;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (countStmt != null) {
                countStmt.close();
            }
        }
    }

    /**
     * 执行解析好的sql，获取总计信息。<br/> 详细描述：根据解析的完整sql语句，以便获取该sql查询出来的总计数据，供前台表格展示。<br/> 使用方式：在processIntercept方法中调用了该方法查询数据量信息。
     * 
     * @param sql 解析好的完整sql语句。
     * @param connection 当前mybatis操作数据库的连接对象。
     * @param mappedStatement mapper中每个sql节点的对象化。
     * @param parameterObject sql的参数对象。
     * @param boundSql mybatis的绑定sql对象。
     * @param dialect 数据库方言对象。
     * @return 总计集合数据。
     */
    public static List<Object> getTotalSql(final String sql, final Connection connection,
                                           final MappedStatement mappedStatement, final Object parameterObject,
                                           final BoundSql boundSql, Dialect dialect) throws SQLException {
        List<Object> totalList = new ArrayList<Object>();
        Map<String, Object> totalMap = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(sql);
            ExtDefaultParameterHandler handler = new ExtDefaultParameterHandler(mappedStatement, parameterObject,
                boundSql);
            handler.setParameters(countStmt);
            rs = countStmt.executeQuery();
            while (rs.next()) {
                totalMap = new HashMap<String, Object>();
                ResultSetMetaData resMeta = rs.getMetaData();
                for (int i = 1; i <= resMeta.getColumnCount(); i++ ) {
                    if (resMeta.getColumnClassName(i).equals("java.lang.String")) {
                        String value = rs.getString(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.lang.Integer")) {
                        int value = rs.getInt(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.lang.Long")) {
                        long value = rs.getLong(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.lang.Short")) {
                        short value = rs.getShort(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.lang.Float")) {
                        float value = rs.getFloat(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.lang.Double")) {
                        double value = rs.getDouble(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else if (resMeta.getColumnClassName(i).equals("java.math.BigDecimal")) {
                        BigDecimal value = rs.getBigDecimal(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    } else {
                        Object value = rs.getObject(resMeta.getColumnName(i));
                        totalMap.put(resMeta.getColumnName(i), value);
                    }
                }
                totalList.add(totalMap);
            }
            return totalList;
        } catch (Exception e) {
            logger.error("执行查询总计语句时出错 ！", e);
            return totalList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (countStmt != null) {
                countStmt.close();
            }
        }
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
            boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * mybatis的Interceptor接口中的方法，把拦截器插入到目标对象的方法。<br/> 详细描述：会根据当前的对象把拦截器插入到该对象中。<br/> 使用方式：mybatis内置的方法。
     * 
     * @param target 要插入的目标对对象。
     * @return 要插入的目标对象。
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 该方法暂无用到。<br/> 详细描述：暂无用到。<br/> 使用方式：mybatis内置的方法。
     * 
     * @param properties 配置在mybatis中的properties文件。
     */
    public void setProperties(Properties properties) {
    }

    /**
     * 封装BoundSql的对象类。
     * 
     * @author liyi
     * @version V0.0.1-SNAPSHOT 日期：2013-10-12
     * @since 0.0.1-SNAPSHOT
     */
    public static class BoundSqlSqlSource implements SqlSource {

        /**
         * BoundSql对象。
         */
        BoundSql boundSql;

        /**
         * BoundSqlSqlSource有参构造器。<br/> 详细描述：封装了BoundSql对象的实体类。 使用方式：直接实例化BoundSqlSqlSource。
         * 
         * @param boundSql mybatis的绑定sql对象。
         */
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        /**
         * boundSql的get方法。
         */
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}