/**
 * @company 西安一体物联网科技有限公司
 * @file WechatController.java
 * @author zhaochao
 * @date 2018年4月27日 
 */
package com.etiot.controller;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etiot.config.ProjectUrlConfig;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月27日
 */
@Controller
@RequestMapping(value="/wechat")
public class WechatController {

	private Logger logger=LoggerFactory.getLogger(getClass()); 
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	@Autowired
    private WxMpService wxOpenService;
	
	@GetMapping(value="/authorize")
	public String authrize(String returnUrl){
		String url=projectUrlConfig.getWechatMpAuthorize()+"/wechat/userInfo";
		
		String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
			logger.info("【微信网页授权】 获取code,result={}",redirectUrl);
		return "redirect:"+redirectUrl;
	}
	
	@GetMapping(value="/userInfo")
	public String userInfo(@RequestParam("code")String code,@RequestParam("state") String redirectUrl){
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
				try {
					wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
				} catch (WxErrorException e) {
					logger.error("【微信网页授权】{}",e);
					throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
				}
				String openId=wxMpOAuth2AccessToken.getOpenId();
		return "redirect:"+redirectUrl+"?openid="+openId;
		
	}
	
	@GetMapping("/qrAuthorize")
	public String  qrAuthorize(String returnUrl){
		//String url=projectUrlConfig.getWechatOpenAuthorize() + "/wechat/userInfo";
		String url="http://sell.springboot.cn/sell/wechat/qrUserInfo";
		@SuppressWarnings("deprecation")
		String redirectUrl=wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}
	
	@GetMapping(value="/qruserInfo")
	public String qruserInfo(@RequestParam("code")String code,@RequestParam("state") String redirectUrl){
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
				try {
					wxMpOAuth2AccessToken=wxOpenService.oauth2getAccessToken(code);
				} catch (WxErrorException e) {
					logger.error("【微信网页授权】{}",e);
					throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
				}
				logger.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
				String openId=wxMpOAuth2AccessToken.getOpenId();
		return "redirect:"+redirectUrl+"?openid="+openId;
		
	}
}
