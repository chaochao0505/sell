/**
 * @company 西安一体物联网科技有限公司
 * @file ProductCategoryServiceImpl.java
 * @author zhaochao
 * @date 2018年5月29日 
 */
package com.etiot.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiot.dao.ProductCategoryDao;
import com.etiot.entity.ProductCategoryEntity;
import com.etiot.service.ProductCategoryService;

/**
 * @description :商品类目业务层
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月29日
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategory;
	
	@Override
	public List<ProductCategoryEntity> findAll() {
		List<ProductCategoryEntity> list=productCategory.findAll();
		return list;
	}

	@Override
	public int insertSelective(ProductCategoryEntity record) {
		record.setCategoryId(Integer.valueOf(createData()));
		
		return productCategory.insertSelective(record);
	}

	@Override
	public ProductCategoryEntity selectByPrimaryKey(Integer categoryId) {
		ProductCategoryEntity entity=productCategory.selectByPrimaryKey(categoryId);
		return entity;
	}

	@Override
	public int updateByPrimaryKeySelective(ProductCategoryEntity record) {
		
		return productCategory.updateByPrimaryKeySelective(record);
	}

	 //根据指定长度生成纯数字的随机数
	 public  String createData() {
	        StringBuilder sb=new StringBuilder();
	        Random rand=new Random();
	        for(int i=0;i<9;i++)
	        {
	            sb.append(rand.nextInt(10));
	        }
	        String data=sb.toString();
	        
	       return data;
	    }
}
