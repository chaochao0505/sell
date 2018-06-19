package com.etiot.dao;

import com.etiot.entity.SellerInfoEntity;

public interface SellerInfoDao {
	
	SellerInfoEntity findByOpenid(String openid);
	
    int deleteByPrimaryKey(String id);

    int insertSelective(SellerInfoEntity record);

    SellerInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SellerInfoEntity record);
}