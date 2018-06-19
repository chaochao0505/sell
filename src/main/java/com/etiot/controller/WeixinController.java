/**
 * @company 西安一体物联网科技有限公司
 * @file WeixinController.java
 * @author zhaochao
 * @date 2018年4月27日 
 */
package com.etiot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月27日
 */
@RestController
@RequestMapping(value="/weixin")
public class WeixinController {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@GetMapping(value="/auth")
	public void auth(String code){
		logger.info("进入auth方法。。。");
		logger.info("code={}",code);
		
	}
}
