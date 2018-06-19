/**
 * @company 西安一体物联网科技有限公司
 * @file CartDto.java
 * @author zhaochao
 * @date 2018年4月18日 
 */
package com.etiot.dto;

/**
 * @description :购物车
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月18日
 */
public class CartDto {

	private String productId;
	
	private Integer productQuantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public CartDto(String productId, Integer productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	
	
}
