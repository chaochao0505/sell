/**
 * @company 西安一体物联网科技有限公司
 * @file SellerOrderController.java
 * @author zhaochao
 * @date 2018年5月22日 
 */
package com.etiot.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.etiot.dto.OrderDto;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.SellException;
import com.etiot.service.OrderService;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月22日
 */
@RestController
@RequestMapping("/seller/order")
public class SellerOrderController {

	private final Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderService orderService;
	
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
		PageList<OrderDto> list=orderService.findList(pageQuery);
		map.put("list", list);
		map.put("totalCount",list.getPaginator().getTotalCount());
		map.put("currentPage",list.getPaginator().getCurPage());
		map.put("TotalPages",list.getPaginator().getTotalPages());
		return new ModelAndView("order/list",map);
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId")String orderId,Map<String,Object> map){
		
		try {
			OrderDto orderDto = orderService.findOne(orderId);
			orderService.cancle(orderDto);
		} catch (SellException e) {
			log.error("【卖家端取消订单】查询不到订单",e);
			
			map.put("msg", e.getMessage());
			map.put("url","/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
		map.put("url","/seller/order/list");
		
		return new ModelAndView("common/success",map);
	}
	
	/**
	 * 订单详情
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId")String orderId,Map<String,Object> map){
		OrderDto orderDto=null;
		try {
			orderDto=orderService.findOne(orderId);
		} catch (SellException e) {
			log.error("【卖家端查看订单详情】查询不到订单",e);
			
			map.put("msg", e.getMessage());
			map.put("url","/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("orderDto", orderDto);
		return new ModelAndView("order/detail",map);
	}
	
	/**
	 * 完结订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/finish")
	public ModelAndView finish(@RequestParam("orderId")String orderId,Map<String,Object> map){
		OrderDto orderDto=null;
		try {
			orderDto=orderService.findOne(orderId);
			orderService.finish(orderDto);
		} catch (SellException e) {
			log.error("【卖家端完结订单】查询不到订单",e);
			
			map.put("msg", e.getMessage());
			map.put("url","/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
		map.put("url","/seller/order/list");
		
		return new ModelAndView("common/success",map);
	}
}
