/**
 * @company 西安一体物联网科技有限公司
 * @file WechatPayConfig.java
 * @author zhaochao
 * @date 2018年5月16日 
 */
package com.etiot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

/**
 * @description :微信支付配置
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月16日
 */
@Component
public class WechatPayConfig {
	
	@Autowired
	private WechatAccountConfig accountConfig;
	@Bean
	public BestPayServiceImpl bestService(){
		BestPayServiceImpl bestPayServiceImpl=new BestPayServiceImpl();
		bestPayServiceImpl.setWxPayH5Config(wxPayH5Config());
		return bestPayServiceImpl;
	}
	
	@Bean
	public WxPayH5Config wxPayH5Config(){
		WxPayH5Config wxPayH5Config=new WxPayH5Config();
		wxPayH5Config.setAppSecret(accountConfig.getSecret());
		wxPayH5Config.setAppId(accountConfig.getAppid());
		wxPayH5Config.setMchId(accountConfig.getMchId());
		wxPayH5Config.setMchKey(accountConfig.getMchKey());
		wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
		wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
		return wxPayH5Config;
	}
}
