/**
 * @company 西安一体物联网科技有限公司
 * @file PushMessageService.java
 * @author zhaochao
 * @date 2018年6月11日 
 */
package com.etiot.service;

import com.etiot.dto.OrderDto;

/**
 * @description :消息推送
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月11日
 */
public interface PushMessageService {

	
	void orderStatus(OrderDto orderDto);
}
