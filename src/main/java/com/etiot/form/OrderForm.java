/**
 * @company 西安一体物联网科技有限公司
 * @file OrderForm.java
 * @author zhaochao
 * @date 2018年4月19日 
 */
package com.etiot.form;

import javax.validation.constraints.NotNull;


/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月19日
 */
public class OrderForm {

	/**
	 * 买家姓名
	 */
	@NotNull(message="姓名必填")
	private String name;
	
	/**
	 * 买家电话
	 */
	@NotNull(message="手机号必填")
	private String phone;
	
	/**
	 * 买家地址
	 */
	@NotNull(message="地址必填")
	private String address;
	
	/**
	 * 买家openid
	 */
	@NotNull(message="id必填")
	private String openid;
	
	/**
	 * 购物车
	 */
	@NotNull(message="购物车不能为空")
	private String items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}
	
}
