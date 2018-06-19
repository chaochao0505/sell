/**
 * @company 西安一体物联网科技有限公司
 * @file ProductVo.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
public class ProductVo {
	
	@JsonProperty("name")
	private String categoryNmae;
	
	@JsonProperty("type")
	private Integer categoryType;
	
	@JsonProperty("foods")
	private List<ProductInfoVo> productInfoVoList;

	public String getCategoryNmae() {
		return categoryNmae;
	}

	public void setCategoryNmae(String categoryNmae) {
		this.categoryNmae = categoryNmae;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public List<ProductInfoVo> getProductInfoVoList() {
		return productInfoVoList;
	}

	public void setProductInfoVoList(List<ProductInfoVo> productInfoVoList) {
		this.productInfoVoList = productInfoVoList;
	}
	
	
}
