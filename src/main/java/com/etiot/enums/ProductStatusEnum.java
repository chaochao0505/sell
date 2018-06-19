/**
 * @company 西安一体物联网科技有限公司
 * @file ProductStatusEnum.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.enums;

/**
 * @description :商品状态枚举
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
public enum ProductStatusEnum implements CodeEnum{

	UP(0,"在架"),
	DOWN(1,"下架")
	;
	private Integer code;
	
	private String message;
	
	private ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
