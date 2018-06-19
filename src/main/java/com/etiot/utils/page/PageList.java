/**
 * @company 西安一体物联网科技有限公司
 * @file PageList.java
 * @author zhaochao
 * @date 2018年5月23日 
 */
package com.etiot.utils.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页结果集合，继承自ArrayList，用于Mybatis拦截器返回List时，将分页信息返回。
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016-4-20
 * @since 1.0.0-RELEASE
 */
public class PageList<E> extends ArrayList<E> implements Serializable {
    private static final long serialVersionUID = 1412759446332294208L;
    
    private Paginator paginator;

	/**
     * PageList默认的无参构造器。
     */
    public PageList() {}
    
    /**
     * PageList有参构造器。
     * @param c Collection接口。
     */
    public PageList(Collection<? extends E> c) {
        super(c);
    }
    
    /**
     * PageList有参构造器。
     * @param c Collection接口。
     * @param p 分页器。
     */
    public PageList(Collection<? extends E> c, Paginator p) {
        super(c);
        this.paginator = p;
    }
    
    /**
     * PageList有参构造器。
     * @param p 分页器。
     */
    public PageList(Paginator p) {
        this.paginator = p;
    }

    /**
     * 得到分页器，通过Paginator可以得到总页数等值。
     * @return 分页器。
     */
    public Paginator getPaginator() {
        return paginator;
    }
    
    /**
     * Paginator的set方法。
     */
    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
    
    /**
     * 根据当前集合下标替换内部的数据对象。
     * @param i 当前集合下标。
     * @param formateObj 需要替换的数据对象。
     */
	public void setPageList(int i, E formateObj) {
		super.set(i, formateObj);
	}
}