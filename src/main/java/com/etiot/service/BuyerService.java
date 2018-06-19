/**
 * @company 西安一体物联网科技有限公司
 * @file BuyerService.java
 * @author zhaochao
 * @date 2018年4月26日 
 */
package com.etiot.service;

import com.etiot.dto.OrderDto;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月26日
 */
public interface BuyerService {

	/**
	 * 查询一个订单
	 * @param openid
	 * @param orderId
	 * @return
	 */
	OrderDto findOrderOne(String openid,String orderId);
	
	/**
	 * 取消订单
	 * @param openid
	 * @param orderId
	 * @return
	 */
	OrderDto cancleOrder(String openid,String orderId);
}
