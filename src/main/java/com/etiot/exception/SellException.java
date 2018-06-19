/**
 * @company 西安一体物联网科技有限公司
 * @file SellException.java
 * @author zhaochao
 * @date 2018年4月18日 
 */
package com.etiot.exception;

import com.etiot.enums.ResultEnum;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月18日
 */
@SuppressWarnings("serial")
public class SellException extends RuntimeException {

	
	private Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code=resultEnum.getCode();
	}
	
	public SellException(Integer code,String message) {
		super(message);
		this.code=code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
