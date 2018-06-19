/**
 * @company 西安一体物联网科技有限公司
 * @file ProductService.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.service;

import java.util.List;

import com.etiot.dto.CartDto;
import com.etiot.entity.ProductCategoryEntity;
import com.etiot.entity.ProductInfoEntity;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
public interface ProductService {

    int deleteByPrimaryKey(String productId);

    int insertSelective(ProductInfoEntity record);

    ProductInfoEntity selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(ProductInfoEntity record);
    
    /**
     * 查询所有上架的商品
     * @return
     */
    List<ProductInfoEntity> findUpAll();
    
    /**
     * 查询所有商品列表
     * @param pageable
     * @return
     */
    PageList<ProductInfoEntity> findAll(PageQuery pageQuery);
    
    /**
     * 查询商品类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategoryEntity> findCategoryTypeIn(List<Integer> categoryTypeList);
    
    //加库存
    void increaseStock(List<CartDto> cartDtoList);
    
    //减库存
    void decreaseStock(List<CartDto> cartDtoList);
    
    /**
     * 上架
     * @param productId
     * @return
     */
    Integer onSale(String productId);
    
    
    /**
     * 下架
     * @param productId
     * @return
     */
    Integer offSale(String productId);
}
