/**
 * @company 西安一体物联网科技有限公司
 * @file BuyerServiceImpl.java
 * @author zhaochao
 * @date 2018年4月26日 
 */
package com.etiot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiot.dto.OrderDto;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.service.BuyerService;
import com.etiot.service.OrderService;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月26日
 */
@Service
public class BuyerServiceImpl implements BuyerService{

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderService orderService;
	
	@Override
	public OrderDto findOrderOne(String openid, String orderId) {
		
		return checkOrderOwner(openid,orderId);
	}

	@Override
	public OrderDto cancleOrder(String openid, String orderId) {
		OrderDto orderDto=checkOrderOwner(openid, orderId);
		if(orderDto==null){
			logger.error("【取消订单】订单不存在,orderId={}",orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancle(orderDto);
	}

	public OrderDto checkOrderOwner(String openid, String orderId) {
		OrderDto orderDto=orderService.findOne(orderId);
		if(orderDto==null){
			return null;
		}
		if(!orderDto.getBuyerOpenid().equals(openid)){
			logger.error("【查询订单】订单的openid不一致,openid={},orderId{}",openid,orderId);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDto;
	}
}
