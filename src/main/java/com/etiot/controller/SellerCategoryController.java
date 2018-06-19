/**
 * @company 西安一体物联网科技有限公司
 * @file SellerCategoryController.java
 * @author zhaochao
 * @date 2018年5月29日 
 */
package com.etiot.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.etiot.entity.ProductCategoryEntity;
import com.etiot.form.CategoryForm;
import com.etiot.service.ProductCategoryService;

/**
 * @description :卖家类目控制层
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月29日
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private ProductCategoryService categoryService;

	/**
	 * 类目列表查询
	 * @param map
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(Map<String,Object> map){
		
	List<ProductCategoryEntity> list=categoryService.findAll();
	map.put("categoryList", list);
	return new ModelAndView("category/list",map);
	}
	
	/**
	 * 类目查看
	 * @param categoryId
	 * @param map
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(Integer categoryId,Map<String,Object> map){
		if(categoryId !=null){
			ProductCategoryEntity entity=categoryService.selectByPrimaryKey(categoryId);
			map.put("category", entity);
		}

		return new ModelAndView("category/index",map);
	}
	
	/**
	 * 保存修改
	 * @param record
	 * @param bingdReuslt
	 * @param map
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryForm record,BindingResult bingdReuslt,Map<String,Object> map){
		if(bingdReuslt.hasErrors()){
			map.put("msg", bingdReuslt.getFieldError().getDefaultMessage());
			map.put("url", "/seller/category/index");
			return new ModelAndView("common/error",map);
		}
		ProductCategoryEntity entity=new ProductCategoryEntity();
		try {
			if (record.getCategoryId() != null) {
				entity = categoryService.selectByPrimaryKey(record.getCategoryId());
				BeanUtils.copyProperties(record, entity);
				categoryService.updateByPrimaryKeySelective(entity);
			}else{
				BeanUtils.copyProperties(record, entity);
				categoryService.insertSelective(entity);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		map.put("url", "/seller/category/list");
		return new ModelAndView("common/success",map);
	}
}
