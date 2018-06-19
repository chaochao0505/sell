/**
 * @company 西安一体物联网科技有限公司
 * @file OrderMasterService.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.service;

import java.util.List;

import com.etiot.dto.OrderDto;
import com.etiot.entity.OrderMasterEntity;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;


/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
public interface OrderService {
	
    int deleteByPrimaryKey(String orderId);

    int insertSelective(OrderMasterEntity record);

    OrderMasterEntity selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMasterEntity record);
    
    List<OrderMasterEntity> findByBuyerOpenid(String buyerOpenid,PageQuery pageQuery);
    
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    OrderDto create(OrderDto orderDto);
    
    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderDto findOne(String orderId);
    
    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageQuery
     * @return
     */
    List<OrderDto> findList(String buyerOpenid,PageQuery pageQuery);

    /**
     * 取消订单
     * @param orderDto
     * @return
     */
    OrderDto cancle(OrderDto orderDto);
    
    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    OrderDto finish(OrderDto orderDto);
    
    /**
     * 支付订单
     * @param orderDto
     * @return
     */
    OrderDto paid(OrderDto orderDto);
    
    /**
     * 查询订单列表(商家)
     * @param buyerOpenid
     * @param pageQuery
     * @return
     */
    PageList<OrderDto> findList(PageQuery pageQuery);
}
