/**
 * @company 西安一体物联网科技有限公司
 * @file ResultEnum.java
 * @author zhaochao
 * @date 2018年4月18日 
 */
package com.etiot.enums;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月18日
 */
public enum ResultEnum {
	SUCCESS(0,"成功"),
	
	PARAM_ERROR(1,"参数不正确"),
	
	PRODUCT_NOT_EXIST(10,"商品不从在"),
	
	PRODUCT_STOCK_ERROR(11,"商品库存不足"),
	
	ORDER_NOT_EXIST(12,"订单不存在"),
	
	ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
	
	ORDER_STATUS_ERROR(14,"订单状态不正确"),
	
	ORDER_UPDATE_FAIL(15,"更新订单失败"),
	
	ORDER_DEATIL_EMPTY(16,"订单详情为空"),
	
	ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
	
	CART_EMPTY(18,"购物车为空"),
	
	ORDER_OWNER_ERROR(19,"订单不属于当前用户"),

	WECHAT_MP_ERROR(20,"微信公众账号方面错误"),
	
	WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额校验不通过"),
	
	ORDER_CANCEL_SUCCESS(22,"订单取消成功"),
	
	ORDER_FINISH_SUCCESS(23,"订单完结成功"),
	
	PRODUCT_STATUS_ERROR(24,"商品状态不正确"),
	
	LOGIN_FAIL(25,"登录失败，登录信息不正确"),
	
	LOGOUT_SUCCESS(26,"退出成功！"),
	;
	private Integer code;
	
	private String message;

	private ResultEnum(Integer code, String message) {
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
