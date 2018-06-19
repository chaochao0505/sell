/**
 * @company 西安一体物联网科技有限公司
 * @file OrderStatusEnum.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.enums;

/**
 * @description :订单状态枚举
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
public enum OrderStatusEnum implements CodeEnum{

	NEW(0,"新订单"),
	FINISHED(1,"完结"),
	CANCLE(2,"取消")
	;
	private Integer code;
	private String message;
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
	private OrderStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
}
