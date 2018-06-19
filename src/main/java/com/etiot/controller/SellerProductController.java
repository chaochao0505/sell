/**
 * @company 西安一体物联网科技有限公司
 * @file SellerProductController.java
 * @author zhaochao
 * @date 2018年5月28日 
 */
package com.etiot.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.etiot.entity.ProductCategoryEntity;
import com.etiot.entity.ProductInfoEntity;
import com.etiot.enums.ProductStatusEnum;
import com.etiot.exception.SellException;
import com.etiot.form.ProductForm;
import com.etiot.service.ProductCategoryService;
import com.etiot.service.ProductService;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;

/**
 * @description :卖家商品列表
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月28日
 */
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

	private final Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService categoryService;
	
	/**
	 * 查询订单列表
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="size",defaultValue="10")Integer size,Map<String,Object> map){
		PageQuery pageQuery=new PageQuery();
		pageQuery.setiDisplayStart(page==1?0:(page-1)*size);
		pageQuery.setiDisplayLength(size);
		PageList<ProductInfoEntity> list=productService.findAll(pageQuery);
		map.put("list", list);
		map.put("totalCount",list.getPaginator().getTotalCount());
		map.put("currentPage",list.getPaginator().getCurPage());
		map.put("TotalPages",list.getPaginator().getTotalPages());
		return new ModelAndView("product/list",map);
	}
	@GetMapping("/on_sale")
	public ModelAndView onSale(String productId,Map<String,Object> map){
		try {
			productService.onSale(productId);
		} catch (SellException e) {
			map.put("msg",e.getMessage());
			map.put("url", "/seller/product/list");
			
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/seller/product/list");
		return new ModelAndView("common/success",map);
	}
	
	@GetMapping("/off_sale")
	public ModelAndView offSale(String productId,Map<String,Object> map){
		try {
			productService.offSale(productId);
		} catch (SellException e) {
			map.put("msg",e.getMessage());
			map.put("url", "/seller/product/list");
			
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/seller/product/list");
		return new ModelAndView("common/success",map);
	}
	
	/**
	 * 查看商品详情
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(String productId,Map<String,Object> map){
		if(!StringUtils.isEmpty(productId)){
			ProductInfoEntity entity=productService.selectByPrimaryKey(productId);
			map.put("productInfo", entity);
		}
		//查询所有的类目
		List<ProductCategoryEntity> list=categoryService.findAll();
		map.put("categoryList", list);

		return new ModelAndView("product/index",map);
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid ProductForm form,BindingResult bingdReuslt,Map<String,Object> map){
		if(bingdReuslt.hasErrors()){
			map.put("msg", bingdReuslt.getFieldError().getDefaultMessage());
			map.put("url", "/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		try {
			if(StringUtils.isEmpty(form.getProductId())){
				ProductInfoEntity entity=new ProductInfoEntity();
				BeanUtils.copyProperties(form, entity);
				entity.setProductStatus(ProductStatusEnum.UP.getCode());
				productService.insertSelective(entity);
			}else{
				ProductInfoEntity entity=productService.selectByPrimaryKey(form.getProductId());
				BeanUtils.copyProperties(form, entity);
				productService.updateByPrimaryKeySelective(entity);
			}	
			
		} catch (Exception e) {
			map.put("msg",e.getMessage());
			map.put("url", "/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/seller/product/list");

		return new ModelAndView("common/success",map);
	}
	
}
