/**
 * @company 西安一体物联网科技有限公司
 * @file ResultVo.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.vo;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
public class ResultVo<T> {

	/**
	 * 错误码
	 */
	private Integer code;
	
	/**
	 * 提示信息
	 */
	private String msg;
	
	/**
	 * 返回具体信息
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
