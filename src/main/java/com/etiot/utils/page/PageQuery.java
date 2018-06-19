package com.etiot.utils.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * mybatis查询封装的分页信息实体对象。
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016-4-20
 * @since 1.0.0-RELEASE
 */
public class PageQuery extends RowBounds implements java.io.Serializable {
	private static final long serialVersionUID = -8000900575354501298L;

	// 起始数据条数
	private int iDisplayStart;

	// 每页多少条
	private int iDisplayLength = 10;

	// 表格所有列
	private String sColumns;

	// 待排序的列索引
	private String columnIndex;

	// 排序规则
	private String sortTypes;

	// 是否需要计算总数,默认0为计算
	private String isCount = "0";

	// 是否需要计算总计,默认1为不计算
	private String isTotal = "1";

	// 无参构造器
	public PageQuery() {
		super();
	}
	/**
	 * 每页显示多少条
	 */
	@Override
	public int getLimit() {
		return iDisplayLength;
	}
	/**
	 * 分页的偏移量
	 */
	@Override
	public int getOffset() {
		return iDisplayStart;
	}
	
	/**
	 * curPage的get方法。
	 * @return 当前页。
	 */
	public int getCurPage() {
		int curPage = iDisplayStart / iDisplayLength + 1;
		return curPage;
	}

	/**
	 * sortRuleList的get方法。
	 * @return 排序规则集合。
	 */
	public List<SortRule> getSortRuleList() {
		if (null != columnIndex && !"".equals(columnIndex) && null != sColumns
				&& !"".equals(sColumns) && null != sortTypes
				&& !"".equals(sortTypes)) {
			List<SortRule> sortRuleList = new ArrayList<SortRule>();
			String[] allColumns = sColumns.split(",");
			String[] allColumnIndex = columnIndex.split(",");
			String[] allSortDir = sortTypes.split(",");
			for (int i = 0; i < allColumnIndex.length; i++) {
				String sortName = allColumns[Integer
						.parseInt(allColumnIndex[i])];
				String sortDir = allSortDir[i];
				SortRule sortRule = new SortRule(sortName, sortDir);

				if (sortRuleList.size() == 0) {
					sortRuleList.add(sortRule);
				} else {
					for (int j = 0; j < sortRuleList.size(); j++) {
						boolean flag = false;
						for (int k = 0; k < sortRuleList.size(); k++) {
							SortRule sort = sortRuleList.get(k);
							if (sort.getSortType().equals(
									sortRule.getSortType())
									&& sort.getColumnName().equals(
											sortRule.getColumnName())) {
								flag = true;
							}
						}
						if (!flag) {
							sortRuleList.add(sortRule);
						}
					}
				}
			}
			return sortRuleList;
		}
		return null;
	}
	
	/**
	 * sortRuleList的get方法。
	 * @return 排序规则集合。
	 */
	public List<SortRule> getSortRule() {
		if (null != columnIndex && !"".equals(columnIndex) && null != sortTypes
				&& !"".equals(sortTypes)) {
			List<SortRule> sortRuleList = new ArrayList<SortRule>();
			SortRule sRule = new SortRule(columnIndex, sortTypes);
			sortRuleList.add(sRule);
			return sortRuleList;
		}
		return null;
	}

	/**
	 * iDisplayStart的set方法。
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}


	/**
	 * iDisplayLength的set方法。
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * sColumns的get方法。
	 */
	public String getsColumns() {
		return sColumns;
	}

	/**
	 * sColumns的set方法。
	 */
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	/**
	 * ColumnIndex的get方法。
	 */
	public String getColumnIndex() {
		return columnIndex;
	}

	/**
	 * ColumnIndex的set方法。
	 */
	public void setColumnIndex(String columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * SortTypes的get方法。
	 */
	public String getSortTypes() {
		return sortTypes;
	}

	/**
	 * SortTypes的set方法。
	 */
	public void setSortTypes(String sortTypes) {
		this.sortTypes = sortTypes;
	}


	/**
	 * isCount的get方法。
	 */
	public String getIsCount() {
		return isCount;
	}

	/**
	 * isCount的set方法。
	 */
	public void setIsCount(String isCount) {
		this.isCount = isCount;
	}

	/**
	 * isTotal的get方法。
	 */
	public String getIsTotal() {
		return isTotal;
	}

	/**
	 * isTotal的set方法。
	 */
	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}
}