/**
 * @company 西安一体物联网科技有限公司
 * @file OrderForm2OrderDtoConverter.java
 * @author zhaochao
 * @date 2018年4月19日 
 */
package com.etiot.converter;

import java.io.IOException;
import java.util.List;

import com.etiot.dto.OrderDto;
import com.etiot.entity.OrderDetailEntity;
import com.etiot.form.OrderForm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月19日
 */
public class OrderForm2OrderDtoConverter {

	public static OrderDto convert(OrderForm orderForm) throws JsonParseException, JsonMappingException, IOException{
		OrderDto orderDto=new OrderDto();
		orderDto.setBuyerName(orderForm.getName());
		orderDto.setBuyerPhone(orderForm.getPhone());
		orderDto.setBuyerAddress(orderForm.getAddress());
		orderDto.setBuyerOpenid(orderForm.getOpenid());
		Gson gson=new Gson();
		List<OrderDetailEntity> list =gson.fromJson(orderForm.getItems(), 
				new TypeToken<List<OrderDetailEntity>>(){}
				.getType());
		orderDto.setOrderDetailList(list);
		return orderDto;
		
	}
}
