/**
 * @company 西安一体物联网科技有限公司
 * @file BuyerProductContoller.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etiot.entity.ProductCategoryEntity;
import com.etiot.entity.ProductInfoEntity;
import com.etiot.service.ProductService;
import com.etiot.utils.ResultVoUtil;
import com.etiot.vo.ProductInfoVo;
import com.etiot.vo.ProductVo;
import com.etiot.vo.ResultVo;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductContoller {
	@Autowired
	private ProductService piService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ResultVo list(){

		//1.查询所有的上架商品
		List<ProductInfoEntity> productInfoList=piService.findUpAll();
		//2.查询类目
		//传统方法
		/*List<Integer>  categoryList=new ArrayList<>();
		for (ProductInfoEntity productInfoEntity : infoList) {
			categoryList.add(productInfoEntity.getCategoryType());
		}*/
		//java8新特性
		List<Integer>  categoryTypeList=productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		//商品类目列表
		List<ProductCategoryEntity> productCateGoryList=piService.findCategoryTypeIn(categoryTypeList);
		List<ProductVo> productVoList=new ArrayList<>();
		for (ProductCategoryEntity productCategoryEntity : productCateGoryList) {
			ProductVo productVo=new ProductVo();
			productVo.setCategoryType(productCategoryEntity.getCategoryType());
			productVo.setCategoryNmae(productCategoryEntity.getCategoryName());
			List<ProductInfoVo> productInfoVoList=new ArrayList<>();
			for (ProductInfoEntity productInfoEntity : productInfoList) {
				if(productInfoEntity.getCategoryType().equals(productCategoryEntity.getCategoryType())){
					ProductInfoVo productInfoVo=new ProductInfoVo();
					BeanUtils.copyProperties(productInfoEntity, productInfoVo);
					productInfoVoList.add(productInfoVo);
				}
			}
			productVo.setProductInfoVoList(productInfoVoList);
			productVoList.add(productVo);
		}
		return ResultVoUtil.success(productVoList);
		
	}
}
