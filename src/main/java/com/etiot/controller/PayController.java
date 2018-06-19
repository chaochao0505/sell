/**
 * @company 西安一体物联网科技有限公司
 * @file PayController.java
 * @author zhaochao
 * @date 2018年5月15日 
 */
package com.etiot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.etiot.dto.OrderDto;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.service.OrderService;
import com.etiot.service.PayService;
import com.lly835.bestpay.model.PayResponse;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月15日
 */
@Controller
/*@RequestMapping("/pay")*/
public class PayController {

	private Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayService payService;

    @GetMapping("/pay")
    public ModelAndView index(@RequestParam("openid") String openid,
                              @RequestParam("orderId") String orderId,
                              @RequestParam("returnUrl") String returnUrl,
                              Map<String,Object> map){
        log.info("openid={}",openid);
        //1,查询订单
        OrderDto orderDto = orderService.findOne(orderId);
        if(orderDto==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //2.发起支付
        //orderDTO.setBuyerOpenid(openid);
        PayResponse payResponse = payService.create(orderDto);
        map.put("payResponse",payResponse);
       try {
		String decode=URLDecoder.decode(returnUrl, "UTF-8");
		 map.put("returnUrl",decode);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		log.error("【微信支付】返回的returnUrl异常");
	}
       
        return new ModelAndView("pay/create",map);
    }
/*	@GetMapping(value="/create")
	public ModelAndView create(@RequestParam("openid") String openid,@RequestParam("orderId")String orderId,
			@RequestParam("returnUrl")String returnUrl,
			Map<String,Object> map){
		//1查询订单
		OrderDto orderDto=orderService.findOne(orderId);
		if(orderDto==null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		//2发起支付
		PayResponse payResponse=payService.create(orderDto);
		map.put("payResponse", payResponse);
		map.put("returnUrl", returnUrl);
		return new ModelAndView("pay/create",map);
	}*/
    
    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
    	
    	payService.notify(notifyData);	
    	//返回给微信的处理结果
    	return new ModelAndView("pay/success");
    }
}
