/**
 * @company 西安一体物联网科技有限公司
 * @file ProjectUrl.java
 * @author zhaochao
 * @date 2018年6月1日 
 */
package com.etiot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月1日
 */
@Component
@ConfigurationProperties(prefix="project")
public class ProjectUrlConfig {
	
	/**
	 * 微信公众平台授权url
	 */
	@Value("${project.wechatMpAuthorize}")
	private String wechatMpAuthorize;
	
	/**
	 * 微信开放平台授权url
	 */
	@Value("${project.wechatOpenAuthorize}")
	private String wechatOpenAuthorize;
	
	/**
	 * 点餐url
	 */
	@Value("${project.sell}")
	private String sell;

	public String getWechatMpAuthorize() {
		return wechatMpAuthorize;
	}

	public void setWechatMpAuthorize(String wechatMpAuthorize) {
		this.wechatMpAuthorize = wechatMpAuthorize;
	}

	public String getWechatOpenAuthorize() {
		return wechatOpenAuthorize;
	}

	public void setWechatOpenAuthorize(String wechatOpenAuthorize) {
		this.wechatOpenAuthorize = wechatOpenAuthorize;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}
	
	
}
