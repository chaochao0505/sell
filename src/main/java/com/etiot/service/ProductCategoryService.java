/**
 * @company 西安一体物联网科技有限公司
 * @file ProductCategoryService.java
 * @author zhaochao
 * @date 2018年5月29日 
 */
package com.etiot.service;

import java.util.List;

import com.etiot.entity.ProductCategoryEntity;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月29日
 */
public interface ProductCategoryService {

	List<ProductCategoryEntity> findAll();
	
    int insertSelective(ProductCategoryEntity record);

    ProductCategoryEntity selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategoryEntity record);
}
