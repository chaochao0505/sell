/**
 * @company 西安一体物联网科技有限公司
 * @file ProductCategoryDao.java
 * @author zhaochao
 * @date 2018年4月15日 
 */
package com.etiot.dao;

import java.util.List;

import com.etiot.entity.ProductCategoryEntity;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月15日
 */
public interface ProductCategoryDao {
	
    int deleteByPrimaryKey(Integer categoryId);
    
    int insertSelective(ProductCategoryEntity record);

    ProductCategoryEntity selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategoryEntity record);
    
    List<ProductCategoryEntity> findAll();
    
    List<ProductCategoryEntity> findCategoryTypeIn(List<Integer> categoryTypeList);
}
