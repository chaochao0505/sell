/**
 * @company 西安一体物联网科技有限公司
 * @file BuyerOrderController.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.etiot.converter.OrderForm2OrderDtoConverter;
import com.etiot.dto.OrderDto;
import com.etiot.entity.OrderMasterEntity;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.form.OrderForm;
import com.etiot.service.BuyerService;
import com.etiot.service.OrderService;
import com.etiot.utils.ResultVoUtil;
import com.etiot.utils.page.PageQuery;
import com.etiot.vo.ResultVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="/buyer/order")
public class BuyerOrderController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderService omService;
	@Autowired
	private BuyerService buyerService;
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ResultVo insert(OrderMasterEntity entity){
			int count=omService.insertSelective(entity);
		return ResultVoUtil.success(count);
		
	}
	
	/**
	 * 创建订单
	 * @param orderForm
	 * @param bindingResult
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value="/create")
	public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,BindingResult bindingResult) throws JsonParseException, JsonMappingException, IOException{
		if(bindingResult.hasErrors()){
			logger.error("【创建订单】 参数不正确,orderForm={}", orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
		}
		OrderDto orderDto=OrderForm2OrderDtoConverter.convert(orderForm);
		if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
			logger.error("【创建订单】 参数不正确,orderDetail={}", orderDto.getOrderDetailList());
			throw new SellException(ResultEnum.CART_EMPTY);
		}
		
		OrderDto createResult=omService.create(orderDto);
		Map<String,String> map=new HashMap<>();
		map.put("orderId", createResult.getOrderId());
		
		return ResultVoUtil.success(map);
		
	}
	
	/**
	 * 查询订单列表
	 * @param openid
	 * @param page
	 * @param size
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value="/list")
	public ResultVo<List<OrderDto>> list(String openid,Integer page,Integer size){
		if(StringUtils.isEmpty(openid)){
			logger.error("【查询订单列表】 openid为空 openid={}",openid);
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		PageQuery pageQuery=new PageQuery();
		pageQuery.setiDisplayStart(page*size);
		pageQuery.setiDisplayLength(size);
		List<OrderDto> list=omService.findList(openid, pageQuery);
		return ResultVoUtil.success(list);
		
	}
	
	/**
	 * 订单详情
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value="/detail")
	public ResultVo<OrderDto> detail(String openid,String orderId){
		OrderDto orderDto=buyerService.findOrderOne(openid, orderId);
		return ResultVoUtil.success(orderDto);
	}

	@PostMapping(value="/cancel")
	public ResultVo cancel(String openid,String orderId){
		buyerService.cancleOrder(openid, orderId);
		return ResultVoUtil.success();
	}
}
