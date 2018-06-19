package com.etiot.utils.page;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * MyBatis分页处理MYSQL数据库方言实现类，对Dialect父类的部分抽象方法根据MYSQL的规则进行了相应的实现。
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
@Component
public class MySQLDialect extends Dialect {
    /**
     * 根据传入的日期类型数据，转换为字符型日期作为where条件之一。<br/>
     * 详细描述：把日期类型数据转换为字符型日期，主要是为了替换sql语句中where条件的参数。<br/>
     * 使用方式：MySQLDialect实例化后即可调用该方法。
     * @param value 日期类型参数。
     * @return 符合数据库类型的日期格式。
     */
    public String getDateCondByValue(Object value) {
        return "'" + new DateTime( ((Date)value).getTime()).toString("yyyy-MM-dd") + "'";
    }
    
    /**
     * MySQL数据库支持分页功能。<br/>
     * 详细描述：MySQL数据库支持分页功能因此返回true。<br/>
     * 使用方式：MySQLDialect实例化后，即可调用。
     * @return true支持分页。
     */
    public boolean supportsLimitOffset() {
        return true;
    }
    
    /**
     * MySQL数据库支持分页偏移量指定功能。<br/>
     * 详细描述：MySQL数据库支持分页偏移量的指定返回true。<br/>
     * 使用方式：MySQLDialect实例化后，即可调用。
     * @return true支持偏移量。
     */
    public boolean supportsLimit() {
        return true;
    }
    
    /**
     * 根据sql和分页信息返回拼接好的分页sql语句。<br/>
     * 详细描述：根据分页信息，把需要分页的sql语句拼接成MySQL支持的分页逻辑语句并返回，该逻辑利用了LIMIT函数实现。<br/>
     * 使用方式：MySQLDialect实例化后，即可调用。
     * @param sql 需要分页的sql语句。
     * @param offset 分页数据偏移量。
     * @param offsetPlaceholder offset转换为字符型。
     * @param limit 每页显示多少条。
     * @param limitPlaceholder limit转换为字符型。
     * @return 带有分页条件的sql语句。
     */
    public String getLimitString(String sql,int offset,String offsetPlaceholder,int limit,String limitPlaceholder) {
        if (offset > 0) {
            return sql + " LIMIT " + offsetPlaceholder + "," + limitPlaceholder;
        } else {
            return sql + " LIMIT " + limitPlaceholder;
        }
    }
}