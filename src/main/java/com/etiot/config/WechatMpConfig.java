/**
 * @company 西安一体物联网科技有限公司
 * @file WechatMpConfig.java
 * @author zhaochao
 * @date 2018年4月27日 
 */
package com.etiot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * @description :微信公众号配置
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月27日
 */
@Component
public class WechatMpConfig {

	@Autowired
	private WechatAccountConfig wechatAccountConfig;
	@Bean
	public WxMpService wxMpService(){
		WxMpService wxMpService =new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
		
	}
	@Bean
	public WxMpConfigStorage wxMpConfigStorage(){
		WxMpInMemoryConfigStorage wxMpConfigStorage=new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(wechatAccountConfig.getAppid());
		wxMpConfigStorage.setSecret(wechatAccountConfig.getSecret());
		return wxMpConfigStorage;
	}
}
