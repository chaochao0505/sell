/**
 * @company 西安一体物联网科技有限公司
 * @file WechatAccountConfig.java
 * @author zhaochao
 * @date 2018年4月28日 
 */
package com.etiot.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description : 微信公众号信息
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月28日
 */
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatAccountConfig {
	/**
	 * 公众平台id
	 */
	@Value("${wechat.appid}")
	private String appid;
	/**
	 * 公众平台秘钥
	 */
	@Value("${wechat.secret}")
	private String secret;
	
	/**
	 * 开放平台id
	 */
	@Value("${wechat.openAppid}")
	private String openAppid;
	
	/**
	 * 开放平台秘钥
	 */
	@Value("${wechat.openSecret}")
	private String openSecret;
    /**
     * 商户号
     */
	@Value("${wechat.mchId}")
    private String mchId;

    /**
     * 商户密钥
     */
    @Value("${wechat.mchKey}")
    private String mchKey;

    /**
     * 商户证书路径
     */
    @Value("${wechat.keyPath}")
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    @Value("${wechat.notify_url}")
    private String notifyUrl;
    
    /**
     * 消息推送模板id
     */
    private String templateId; 
    
    
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getOpenAppid() {
		return openAppid;
	}

	public void setOpenAppid(String openAppid) {
		this.openAppid = openAppid;
	}

	public String getOpenSecret() {
		return openSecret;
	}

	public void setOpenSecret(String openSecret) {
		this.openSecret = openSecret;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}
