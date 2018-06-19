/**
 * @company 西安一体物联网科技有限公司
 * @file OrderDto.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.dto;

import java.util.List;

import com.etiot.entity.OrderDetailEntity;
import com.etiot.entity.OrderMasterEntity;
import com.etiot.enums.OrderStatusEnum;
import com.etiot.enums.PayStatusEnum;
import com.etiot.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends OrderMasterEntity {

	private List<OrderDetailEntity> orderDetailList;
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum(){
		return EnumUtil.getByCode(super.getOrderStatus(), OrderStatusEnum.class);
	}
	@JsonIgnore
	public PayStatusEnum getPayStatusEnum(){
		return EnumUtil.getByCode(super.getPayStatus(), PayStatusEnum.class);
	}
	
	public List<OrderDetailEntity> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetailEntity> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
	
}
