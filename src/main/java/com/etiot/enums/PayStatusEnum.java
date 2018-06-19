/**
 * @company 西安一体物联网科技有限公司
 * @file PayStatusEnum.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.enums;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
public enum PayStatusEnum implements CodeEnum{

	WAIT(0,"未支付"),
	SUCCESS(1,"支付成功")
	;
	
	private Integer code;
	
	private String message;

	private PayStatusEnum(Integer code, String message) {
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
