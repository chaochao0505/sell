/**
 * @company 西安一体物联网科技有限公司
 * @file ProductServiceImpl.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiot.dao.ProductCategoryDao;
import com.etiot.dao.ProductInfoDao;
import com.etiot.dto.CartDto;
import com.etiot.entity.ProductCategoryEntity;
import com.etiot.entity.ProductInfoEntity;
import com.etiot.enums.ProductStatusEnum;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.service.ProductService;
import com.etiot.utils.KeyUtil;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private ProductCategoryDao proDao;

	@Override
	public int deleteByPrimaryKey(String productId) {
		int count=productInfoDao.deleteByPrimaryKey(productId);
		return count;
	}

	@Override
	public int insertSelective(ProductInfoEntity record) {
		record.setProductId(KeyUtil.getUniqueKey());
		int count=productInfoDao.insertSelective(record);
		return count;
	}

	@Override
	public ProductInfoEntity selectByPrimaryKey(String productId) {
		ProductInfoEntity entity=productInfoDao.selectByPrimaryKey(productId);
		return entity;
	}

	@Override
	public int updateByPrimaryKeySelective(ProductInfoEntity record) {
		int count=productInfoDao.updateByPrimaryKeySelective(record);
		return count;
	}

	@Override
	public List<ProductInfoEntity> findUpAll() {
		List<ProductInfoEntity> list=productInfoDao.findUpAll(ProductStatusEnum.UP.getCode());
		return list;
	}

	@Override
	public PageList<ProductInfoEntity> findAll(PageQuery pageQuery) {
		PageList<ProductInfoEntity> list=productInfoDao.findAll(pageQuery);
		return list;
	}

	@Override
	public List<ProductCategoryEntity> findCategoryTypeIn(List<Integer> categoryTypeList) {
		List<ProductCategoryEntity> list=proDao.findCategoryTypeIn(categoryTypeList);
		return list;
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDto> cartDtoList) {
		for (CartDto cartDto : cartDtoList) {
			ProductInfoEntity productInfoEntity=productInfoDao.selectByPrimaryKey(cartDto.getProductId());
			if(productInfoEntity==null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result=productInfoEntity.getProductStock()+cartDto.getProductQuantity();
			productInfoEntity.setProductStock(result);
			
			productInfoDao.updateByPrimaryKeySelective(productInfoEntity);
		}
		
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDto> cartDtoList) {
		for (CartDto cartDto : cartDtoList) {
			ProductInfoEntity productInfoEntity=productInfoDao.selectByPrimaryKey(cartDto.getProductId());
			//商品不存在异常
			if(productInfoEntity==null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result=productInfoEntity.getProductStock()-cartDto.getProductQuantity();
			//内存不足异常
			if(result<0){
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfoEntity.setProductStock(result);
			
			productInfoDao.updateByPrimaryKeySelective(productInfoEntity);
		}
		
	}

	@Override
	public Integer onSale(String productId) {
		ProductInfoEntity entity=productInfoDao.selectByPrimaryKey(productId);
		if(entity==null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		if(entity.getProductStatusEnum()==ProductStatusEnum.UP){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		//更新
		entity.setProductStatus(ProductStatusEnum.UP.getCode());
		Integer result=productInfoDao.updateByPrimaryKeySelective(entity);
		return result;
	}

	@Override
	public Integer offSale(String productId) {
		ProductInfoEntity entity=productInfoDao.selectByPrimaryKey(productId);
		if(entity==null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		if(entity.getProductStatusEnum()==ProductStatusEnum.DOWN){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		//更新
		entity.setProductStatus(ProductStatusEnum.DOWN.getCode());
		Integer result=productInfoDao.updateByPrimaryKeySelective(entity);
		return result;
	}
	
}
