/**
 * @company 西安一体物联网科技有限公司
 * @file OrderDetailDao.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.dao;

import java.util.List;

import com.etiot.entity.OrderDetailEntity;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
public interface OrderDetailDao {

    int deleteByPrimaryKey(String detailId);

    int insertSelective(OrderDetailEntity record);

    OrderDetailEntity selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(OrderDetailEntity record);
    
    List<OrderDetailEntity> findByOrderId(String orderId);
}
