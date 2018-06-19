package com.etiot.utils.page;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * MyBatis分页处理，扩展Mybatis的默认参数处理。
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
public class ExtDefaultParameterHandler implements ParameterHandler {
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final MappedStatement mappedStatement;
    private final Object parameterObject;
    private BoundSql boundSql;
    private Configuration configuration;
    
    /**
     * ExtDefaultParameterHandler的有参构造器。<br/>
     * 详细描述：该构造器实例化后可以调用setParameters方法，执行自定义的sql逻辑。<br/>
     * 使用方式：直接实例化ExtDefaultParameterHandler，传入必要的参数即可。
     * @param mappedStatement 会把mapper的sql语句节点对象化为一个一个的mappedStatement。
     * @param parameterObject 参数对象，保存dao方法中传入的所有参数。
     * @param boundSql 代表了一次sql语句的实际执行。
     */
    public ExtDefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject,
                                      BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }
    
    /**
     * parameterObject变量的get方法，返回参数对象。
     */
    public Object getParameterObject() {
        return parameterObject;
    }
    
    /**
     * 重新根据传入的PreparedStatement设置mybatis的执行参数。<br/>
     * 详细描述：重新设置mybatis的执行参数PreparedStatement。<br/>
     * 使用方式：ExtDefaultParameterHandler实例化后，直接调用即可。
     * @param ps 包含了已编译的sql语句，sql语句可具有一个或多个IN参数，由?作为占位符。
     */
    @SuppressWarnings("unchecked")
    public void setParameters(PreparedStatement ps) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
            .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            MetaObject metaObject = parameterObject == null ? null : configuration
                .newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++ ) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                               && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(
                                propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter "
                                                    + propertyName + " of statement "
                                                    + mappedStatement.getId());
                    }
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null)
                        jdbcType = configuration.getJdbcTypeForNull();
                    typeHandler.setParameter(ps, i + 1, value, jdbcType);
                }
            }
        }
    }
}