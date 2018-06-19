package com.etiot.utils.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储查询中需要排序的列信息。
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016-4-20
 * @since 1.0.0-RELEASE
 */
public class SortRule implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SortRule.class);
    
    private static final long serialVersionUID = 743228633117033956L;
    
    private String columnName;
    
    private String sortType;
    
    /**
     * SortRule无参构造器。
     */
    public SortRule() {}
    
    /**
     * SortRule有参构造器。
     * @param columnName 字段名称。
     * @param sortType 排序类型。
     */
    public SortRule(String columnName, String sortType) {
        super();
        this.columnName = columnName;
        this.sortType = sortType;
    }
    
    /**
     * columnName的get方法。
     * @return 字段名称。
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     * columnName的set方法。
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * sortType的get方法。
     * @return 排序类型。
     */
    public String getSortType() {
        return sortType;
    }
    
    /**
     * sortType的set方法。
     */
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    /**
     * 解析排序信息字符串为排序对象集合。
     * 详细描述：根据排序规则字符串，格式化为排序数据集合。<br/>
     * 使用方式：SortRule.parseSortColumns进行调用。
     * @param sortRules 格式为 ： 如：排序列1#DESC,拍序列2#ASC。
     * @return 排序规则集合。
     */
    public static List<SortRule> parseSortColumns(String sortRules) {
        if (StringUtils.isBlank(sortRules)) {
            return new ArrayList<SortRule>(0);
        }
        List<SortRule> results = new ArrayList<SortRule>();
        String[] sortSegments = sortRules.trim().split(",");
        for (int i = 0; i < sortSegments.length; i++ ) {
            String sortSegment = sortSegments[i].trim();
            String[] ary = sortSegment.split("#");
            SortRule sortColumn = new SortRule();
            sortColumn.setColumnName(ary[0]);
            if (ary.length == 2) {
                if ( !ary[1].equalsIgnoreCase("DESC") && !ary[1].equalsIgnoreCase("ASC")) {
                    log.error("设置排序类型不正确，正序为：ASC 反序为： DESC ");
                    sortColumn.setSortType("");
                }
                sortColumn.setSortType(ary[1]);
            } else {
                sortColumn.setSortType("ASC");
            }
            results.add(sortColumn);
        }
        return results;
    }
    
    /**
     * SortRule的toString方法。
     * @return 格式化好的分页信息。
     */
    public String toString() {
        return columnName + (sortType == null ? "" : " " + sortType);
    }
}
