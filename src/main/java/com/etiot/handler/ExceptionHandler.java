/**
 * @company 西安一体物联网科技有限公司
 * @file ExceptionHandler.java
 * @author zhaochao
 * @date 2018年6月7日 
 */
package com.etiot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.etiot.config.ProjectUrlConfig;
import com.etiot.exception.ResponseBankException;
import com.etiot.exception.SellException;
import com.etiot.exception.SellerAuthozrieException;
import com.etiot.utils.ResultVoUtil;
import com.etiot.vo.ResultVo;

/**
 * @description :捕获异常
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月7日
 */
@ControllerAdvice
public class ExceptionHandler {

	@Autowired
	private ProjectUrlConfig urlConfig;
	//拦截登录异常
	@org.springframework.web.bind.annotation.ExceptionHandler(value = SellerAuthozrieException.class)
	public ModelAndView handlerAuthorizeException(){
		return new ModelAndView("redirect:"
				.concat(urlConfig.getWechatOpenAuthorize())
				.concat("/wechat/qrAuthorize")
				.concat("?returnUrl=")
				.concat(urlConfig.getSell())
				.concat("/seller/login"));
	}
	
	@SuppressWarnings("rawtypes")
	@org.springframework.web.bind.annotation.ExceptionHandler(value=SellException.class)
	@ResponseBody
	public ResultVo handlerSellerException(SellException e){
			
		return ResultVoUtil.error(e.getCode(), e.getMessage());
		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value=ResponseBankException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void handlerResponseException(){
		
	}
}
