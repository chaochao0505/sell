/**
 * @company 西安一体物联网科技有限公司
 * @file SellerInfoService.java
 * @author zhaochao
 * @date 2018年6月1日 
 */
package com.etiot.service;

import com.etiot.entity.SellerInfoEntity;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月1日
 */
public interface SellerInfoService {
	
	SellerInfoEntity findByOpenid(String openid);

    int deleteByPrimaryKey(String id);

    int insertSelective(SellerInfoEntity record);

    SellerInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SellerInfoEntity record);
}
