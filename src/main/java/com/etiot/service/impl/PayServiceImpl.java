/**
 * @company 西安一体物联网科技有限公司
 * @file PayServiceImpl.java
 * @author zhaochao
 * @date 2018年5月15日 
 */
package com.etiot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.etiot.dto.OrderDto;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.service.OrderService;
import com.etiot.service.PayService;
import com.etiot.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月15日
 */
@Service
public class PayServiceImpl implements PayService{

	private Logger log=LoggerFactory.getLogger(this.getClass());
	private static final String ORDER_NAME="微信点餐订单";
	@Autowired
	private BestPayServiceImpl bestPayServiceImpl;
	@Autowired
	private OrderService orderService;
	
	@Override
	public PayResponse create(OrderDto orderDto) {
		PayRequest payRequest=new PayRequest();
		payRequest.setOpenid(orderDto.getBuyerOpenid());
		payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
		payRequest.setOrderId(orderDto.getOrderId());
		payRequest.setOrderName(ORDER_NAME);
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("【微信支付：】 request={}", JSON.toJSON(payRequest));
		PayResponse payResponse=bestPayServiceImpl.pay(payRequest);
		log.info("【微信支付：】  ={}",JSON.toJSON(payResponse));
		return payResponse;
	}

	@Override
	public PayResponse notify(String notifyData) {
		//1.验证签名
		//2.支付的状态
		//3.支付的金额
		//4支付人（下单人=支付人）
		PayResponse payResponse=bestPayServiceImpl.asyncNotify(notifyData);
		log.info("【微信支付异步通知】 payResponse={}",payResponse);
		//修改订单状态
		
		OrderDto orderDto=orderService.findOne(payResponse.getOrderId());//根据id查询订单
		//判断订单是否存在
		if(orderDto==null){
			log.error("【微信支付】 异步通知，订单不存在！ orderId={}",payResponse.getOrderId());
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		//判断支付金额是否一致
		if(!MathUtil.equals(payResponse.getOrderAmount(), orderDto.getOrderAmount().doubleValue())){
			log.error("【微信支付】 异步通知，订单金额不一致！orderId={} 微信通知金额={} 系统金额={}",
					payResponse.getOrderId(),
					payResponse.getOrderAmount(),
					orderDto.getOrderAmount()
					);
			throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
		}
		//修改订单的状态
		orderService.paid(orderDto);
		return payResponse;
		
	}

	@Override
	public RefundResponse refund(OrderDto orderDto) {
		RefundRequest refundRequest=new RefundRequest();
		refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
		refundRequest.setOrderId(orderDto.getOrderId());
		refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
			log.info("【微信退款】refundRequest={}",JSON.toJSON(refundRequest));
		
		RefundResponse refundResponse=bestPayServiceImpl.refund(refundRequest);		
			log.info("【微信退款】refundResponse={}",JSON.toJSON(refundResponse));
		return refundResponse;
	}

}
