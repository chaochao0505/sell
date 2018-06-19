/**
 * @company 西安一体物联网科技有限公司
 * @file WebSocketConfig.java
 * @author zhaochao
 * @date 2018年6月11日 
 */
package com.etiot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月11日
 */
@Component
public class WebSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		
		return new ServerEndpointExporter();
	}
}
