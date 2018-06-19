/**
 * @company 西安一体物联网科技有限公司
 * @file ProductInfoVo.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
public class ProductInfoVo {

	@JsonProperty("id")
	private String productId;
	
	@JsonProperty("name")
	private String productName;
	
	@JsonProperty("price")
	private BigDecimal productPrice;
	
	@JsonProperty("description")
	private String productDescription;
	
	@JsonProperty("icon")
	private String productIcon;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}
	
	
}
