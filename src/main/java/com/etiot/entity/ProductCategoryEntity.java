/**
 * @company 西安一体物联网科技有限公司
 * @file ProductCategoryEntity.java
 * @author zhaochao
 * @date 2018年4月15日 
 */
package com.etiot.entity;

import java.util.Date;

/**
 * @description :商品类目实体
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月15日
 */
public class ProductCategoryEntity {

		private Integer categoryId;

	    private String categoryName;

	    private Integer categoryType;

	    private Date createTime;

	    private Date updateTime;

	    public Integer getCategoryId() {
	        return categoryId;
	    }

	    public void setCategoryId(Integer categoryId) {
	        this.categoryId = categoryId;
	    }

	    public String getCategoryName() {
	        return categoryName;
	    }

	    public void setCategoryName(String categoryName) {
	        this.categoryName = categoryName;
	    }

	    public Integer getCategoryType() {
	        return categoryType;
	    }

	    public void setCategoryType(Integer categoryType) {
	        this.categoryType = categoryType;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }
}
