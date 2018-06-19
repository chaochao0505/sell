/**
 * @company 西安一体物联网科技有限公司
 * @file OrderMasterDao.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.dao;

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
public interface OrderMasterDao {

    int deleteByPrimaryKey(String orderId);

    int insertSelective(OrderMasterEntity record);

    OrderMasterEntity selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMasterEntity record);
    
    /**
     * 买家分页查询订单列表
     * @param buyerOpenid
     * @param pageQuery
     * @return
     */
    List<OrderMasterEntity> findByBuyerOpenid(String buyerOpenid,PageQuery pageQuery);
    
    /**
     * 商家查询订单列表
     * @param pageQuery
     * @return
     */
    PageList<OrderDto> findAll(PageQuery pageQuery);

}
