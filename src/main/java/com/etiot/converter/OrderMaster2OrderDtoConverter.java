/**
 * @company 西安一体物联网科技有限公司
 * @file OrderMaster2OrderDtoConverter.java
 * @author zhaochao
 * @date 2018年4月19日 
 */
package com.etiot.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.etiot.dto.OrderDto;
import com.etiot.entity.OrderMasterEntity;

/**
 * @description :list属性转换器
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月19日
 */
public class OrderMaster2OrderDtoConverter {
	
	public static OrderDto convert(OrderMasterEntity orderMaster){
		OrderDto orderDto=new OrderDto();
		BeanUtils.copyProperties(orderMaster, orderDto);
		return orderDto;
	}

	public static List<OrderDto> convert(List<OrderMasterEntity> orderMasterList){
		List<OrderDto> list=orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
		
		return list;
		
	}
}
