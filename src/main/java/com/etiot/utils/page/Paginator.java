package com.etiot.utils.page;

import java.util.List;

/**
 * mybatis的分页对象。
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016-4-20
 * @since 1.0.0-RELEASE
 */
public class Paginator implements java.io.Serializable {
    private static final long serialVersionUID = 6089482156906595931L;
    
    // 分页大小，每页记录条数
    private int pageSize;
    
    // 当前页码，第一页为：1
    private int curPage;
    
    // 总记录数
    private int totalCount;
    
    private List<Object> totalList;

    /**
     * Paginator有参构造器。
     * @param page 当前页。
     * @param pageSize 每页显示多少条。
     * @param totalCount 总条数。
     */
    public Paginator(int page, int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        if (page <= 1) {
            this.curPage = 1;
        } else if (page >= this.getTotalPages() || page == Integer.MAX_VALUE) {
            this.curPage = this.getTotalPages();
        } else {
            this.curPage = page;
        }
    }

    /**
     * curPage的get方法。
     * @return 当前页。
     */
    public int getCurPage() {
        return curPage;
    }

    /**
     * pageSize的get方法。
     * @return 每页显示多少条。
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * totalCount的get方法。
     * @return 取得总记录数。
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 判读是否为第一页。
     * @return 是否为第一页。
     */
    public boolean isFirstPage() {
        return curPage <= 1;
    }

    /**
     * 判读是否为最后一页。
     * @return 是否为最后一页。
     */
    public boolean isLastPage() {
        return curPage >= getTotalPages();
    }

    /**
     * 获取前一页。
     * @return 前一页。
     */
    public int getPrePage() {
        return (curPage - 1 >= 1) ? curPage - 1 : curPage;
    }

    /**
     * 获取下一页。
     * @return 下一页。
     */
    public int getNextPage() {
        return (curPage + 1 <= this.getTotalPages()) ? curPage + 1 : curPage;
    }

    /**
     * 获取总页数。
     * @return 获取总页数。
     */
    public int getTotalPages() {
        if (totalCount <= 0)
            return 0;
        if (pageSize <= 0)
            return 0;
        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++ ;
        }
        return count;
    }
    
    /**
     * 获取总计数据。
     * @return 获取总计数据。
     */
    public List<Object> getTotalList() {
		return totalList;
	}
    
    /**
     * 设置总计数据。
     */
	public void setTotalList(List<Object> totalList) {
		this.totalList = totalList;
	}

	/**
     * 页码链接，并将当前页尽可能地放在滑动窗口的中间部位。
     * @return int对象数组。
     */
    public Integer[] getPageLinks(int pageLinkCount) {
        int totalPage = this.getTotalPages();
        int avg = pageLinkCount / 2;
        int startPageLink = this.curPage - avg;
        if (startPageLink <= 0) {
            startPageLink = 1;
        }
        int endPageLink = startPageLink + pageLinkCount - 1;
        if (endPageLink > totalPage) {
            endPageLink = totalPage;
        }
        if (endPageLink - startPageLink < pageLinkCount) {
            startPageLink = endPageLink - pageLinkCount;
            if (startPageLink <= 0) {
                startPageLink = 1;
            }
        }
        Integer[] result = new Integer[endPageLink - startPageLink];
        for (int i = startPageLink, j = 0; i <= endPageLink; i++ , j++ ) {
            result[j] = i;
        }
        return result;
    }

    /**
     * Paginator的toString方法。
     * @return 格式化好的分页信息。
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Paginator:").append("{当前页=").append(curPage).append(", 分页大小=").append(pageSize)
            .append(",总页数=").append(this.getTotalPages()).append(", 总记录数=").append(totalCount)
            .append('}');
        return sb.toString();
    }
}