/**
 * @company 西安一体物联网科技有限公司
 * @file SellerInfoServiceImpl.java
 * @author zhaochao
 * @date 2018年6月1日 
 */
package com.etiot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiot.dao.SellerInfoDao;
import com.etiot.entity.SellerInfoEntity;
import com.etiot.service.SellerInfoService;
import com.etiot.utils.KeyUtil;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月1日
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService{

	@Autowired
	private SellerInfoDao sellerInfoDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(SellerInfoEntity record) {
		record.setId(KeyUtil.getUniqueKey());
		
		return sellerInfoDao.insertSelective(record);
	}

	@Override
	public SellerInfoEntity selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(SellerInfoEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SellerInfoEntity findByOpenid(String openid) {
		SellerInfoEntity entity=sellerInfoDao.findByOpenid(openid);
		return entity;
	}

}
