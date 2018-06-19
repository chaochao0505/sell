package com.etiot.utils.page;

import java.util.List;

/**
 * 查询所有数据源
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
public abstract class Dialect {
    /**
     * 说明该数据库是否支持分页功能。<br/>
     * 详细描述：如果该数据库支持分页返回true，如果不支持分页则返回false。<br/>
     * 使用方式：子类需要实现该方法，对子类实例化后直接调用该方法即可。
     * @return boolean类型。
     */
    public abstract boolean supportsLimit();
    
    /**
     * 说明该数据库是否支持分页功能中偏移量的指定。<br/>
     * 详细描述：如果该数据库支持分页偏移量的指定返回true，如果不支持则返回false。<br/>
     * 使用方式：子类需要实现该方法，对子类实例化后直接调用该方法即可。
     * @return boolean类型。
     */
    public abstract boolean supportsLimitOffset();
    
    /**
     * 根据sql和分页信息返回拼接好的分页sql语句。<br/>
     * 详细描述：子类实现该抽象方法，根据数据库类型把分页变量转换为符合数据库分页的条件，并把拼接好的分页sql返回。<br/>
     * 使用方式：子类需要实现该抽象方法，该方法作为内部调用使用，在该方法外部包了一层非抽象方法getLimitString供外部调用。
     * @param sql 需要分页的sql语句。
     * @param offset 分页数据偏移量。
     * @param offsetPlaceholder offset转换为字符型。
     * @param limit 每页显示多少条。
     * @param limitPlaceholder limit转换为字符型。
     * @return 带有分页条件的sql语句。
     */
    public abstract String getLimitString(String sql, int offset, String offsetPlaceholder,
                                          int limit, String limitPlaceholder);
    
    /**
     * 子类实现后，对日期类型数据根据不同数据库进行相应的格式化。<br/>
     * 详细描述：该方法需要子类去实现，依据不同的数据库类型对其进行相应的格式化处理后，替换sql语句中的参数。<br/>
     * 使用方式：Dialect实例化后即可调用子类实现后的该方法。
     * @param value 日期类型参数。
     * @return 符合数据库类型的日期格式。
     */
    public abstract String getDateCondByValue(Object value);
    
    /**
     * 调用抽象的getLimitString方法。<br/>
     * 详细描述：该方法的内部实现其实是调用了抽象的getLimitString方法，真正的实现逻辑是由各数据库实现类进行的。<br/>
     * 使用方式：Dialect类实例化后即可调用该方法。
     * @param sql 需要分页的sql语句。
     * @param offset 分页数据偏移量。
     * @param limit 每页显示多少条。
     * @return 调用抽象的getLimitString方法。
     */
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    /**
     * 将sql转换为总记录数sql。<br/>
     * 详细描述：根据传入的sql语句，转换为能够查询该sql总记录数的语句。<br/>
     * 使用方式：在子类中可直接使用this.getCountString(sql)，或者在任何类中使用Dialect.getCountString(sql)。
     * @param sql 需要转换查询数量的sql语句。
     * @return 拼接好查询数量的sql语句。
     */
    public String getCountString(String sql) {
        return "SELECT COUNT(1) FROM (" + sql + ") TMP_COUNT";
    }

    /**
     * 根据sortColumnList排序规则集合，支持多字段规则的排序。<br/>
     * 详细描述：根据传入的sortColumnList排序规则集合，把sql转换为支持多字段排序的语句。<br/>
     * 使用方式：在子类中可直接使用this.getSortString(sql,sortColumnList)，或者在任何类中使用Dialect.getSortString(sql,sortColumnList)。
     * @param sql 需要添加排序的sql语句。
     * @param sortColumnList 字段和排序规则的数据集合，里面保存的是实体泛型SortRule。
     * @return 根据sortColumnList把sql转换为带有排序功能的sql。
     */
    public String getSortString(String sql, List<SortRule> sortColumnList) {
        if (sortColumnList == null || sortColumnList.isEmpty()) {
            return sql;
        }
        StringBuffer buffer = new StringBuffer(sql).append(" ORDER BY ");
        for (SortRule sortRule : sortColumnList) {
            buffer.append(sortRule.getColumnName()).append(" ").append(sortRule.getSortType()).append(", ");
        }
        buffer.delete(buffer.length() - 2, buffer.length());
        return buffer.toString();
    }
}