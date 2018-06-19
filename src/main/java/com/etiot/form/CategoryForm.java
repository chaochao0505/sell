/**
 * @company 西安一体物联网科技有限公司
 * @file CategoryForm.java
 * @author zhaochao
 * @date 2018年5月31日 
 */
package com.etiot.form;

/**
 * @description :商品类目表单
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月31日
 */
public class CategoryForm {

	private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

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
    
    
}
