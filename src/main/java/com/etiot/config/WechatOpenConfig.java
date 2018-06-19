/**
 * @company 西安一体物联网科技有限公司
 * @file WechatOpenConfig.java
 * @author zhaochao
 * @date 2018年6月1日 
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
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月1日
 */

@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig wechatConfig;
	
	@Bean
	public WxMpService wxOpenService(){
		WxMpService wxOpenService=new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(vxOpenConfigStorage());
		return wxOpenService;
	}
	@Bean
	public WxMpConfigStorage vxOpenConfigStorage(){
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage=new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(wechatConfig.getOpenAppid());
		wxMpInMemoryConfigStorage.setSecret(wechatConfig.getSecret());
		return wxMpInMemoryConfigStorage;
	}
	
}
