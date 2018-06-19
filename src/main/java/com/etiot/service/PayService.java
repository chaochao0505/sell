/**
 * @company 西安一体物联网科技有限公司
 * @file PayService.java
 * @author zhaochao
 * @date 2018年5月15日 
 */
package com.etiot.service;

import com.etiot.dto.OrderDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月15日
 */
public interface PayService {

	/**
	 * 发起支付
	 * @param orderDto
	 * @return
	 */
	PayResponse create(OrderDto orderDto);
	
	/**
	 * 微信异步通知
	 * @param notifyData
	 * @return
	 */
	PayResponse notify(String notifyData);
	
	/**
	 * 微信退款
	 * @param orderDto
	 */
	RefundResponse refund(OrderDto orderDto);
}
