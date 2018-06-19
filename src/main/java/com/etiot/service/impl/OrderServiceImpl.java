/**
 * @company 西安一体物联网科技有限公司
 * @file OrderMasterServiceImpl.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.etiot.converter.OrderMaster2OrderDtoConverter;
import com.etiot.dao.OrderDetailDao;
import com.etiot.dao.OrderMasterDao;
import com.etiot.dao.ProductInfoDao;
import com.etiot.dto.CartDto;
import com.etiot.dto.OrderDto;
import com.etiot.entity.OrderDetailEntity;
import com.etiot.entity.OrderMasterEntity;
import com.etiot.entity.ProductInfoEntity;
import com.etiot.enums.OrderStatusEnum;
import com.etiot.enums.PayStatusEnum;
import com.etiot.enums.ResultEnum;
import com.etiot.exception.ResponseBankException;
import com.etiot.exception.SellException;
import com.etiot.service.OrderService;
import com.etiot.service.PayService;
import com.etiot.service.ProductService;
import com.etiot.service.PushMessageService;
import com.etiot.service.WebSocket;
import com.etiot.utils.KeyUtil;
import com.etiot.utils.page.PageList;
import com.etiot.utils.page.PageQuery;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrderMasterDao orderMasterDao;
	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private PayService payService;
	@Autowired
	private PushMessageService pushService;
	@Autowired
	private WebSocket webSocket;
	
	@Override
	public int deleteByPrimaryKey(String orderId) {
		
		return 0;
	}

	@Override
	public int insertSelective(OrderMasterEntity record) {
		record.setOrderStatus(OrderStatusEnum.NEW.getCode());
		record.setPayStatus(PayStatusEnum.WAIT.getCode());
		int count=orderMasterDao.insertSelective(record);
		return count;
	}

	@Override
	public OrderMasterEntity selectByPrimaryKey(String orderId) {
		
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(OrderMasterEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderMasterEntity> findByBuyerOpenid(String buyerOpenid, PageQuery pageQuery) {
		List<OrderMasterEntity> list=orderMasterDao.findByBuyerOpenid(buyerOpenid, pageQuery);
		return list;
	}

	@Override
	public OrderDto create(OrderDto orderDto) {
		BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);//订单总价
		String orderId=KeyUtil.getUniqueKey();//生成订单id
		//1查询商品（数量，价格）
		for (OrderDetailEntity orderDetailEntity : orderDto.getOrderDetailList()) {
			ProductInfoEntity productInfoEntity=productInfoDao.selectByPrimaryKey(orderDetailEntity.getProductId());
			if(productInfoEntity==null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			/*	throw new ResponseBankException();*/
			}
		//2计算订单总价
			orderAmount=productInfoEntity.getProductPrice()
					.multiply(new BigDecimal(orderDetailEntity.getProductQuantity()))
					.add(orderAmount);
			BeanUtils.copyProperties(productInfoEntity, orderDetailEntity);
			orderDetailEntity.setDetailId(KeyUtil.getUniqueKey());
			orderDetailEntity.setOrderId(orderId);
			
			orderDetailDao.insertSelective(orderDetailEntity);
		}
		//3写入订单数据库
		OrderMasterEntity orderMasterEntity=new OrderMasterEntity();
		orderDto.setOrderId(orderId);
		BeanUtils.copyProperties(orderDto, orderMasterEntity);
		orderMasterEntity.setOrderAmount(orderAmount);
		orderMasterEntity.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMasterEntity.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterDao.insertSelective(orderMasterEntity);
		//4 扣库存
		
			//获取商品id和数量
		List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream()
				.map(e-> new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
		productService.decreaseStock(cartDtoList);
		//发送websocket消息
		webSocket.sendMessage(orderDto.getOrderId());
		return orderDto;
	}

	@Override
	public OrderDto findOne(String orderId) {
		OrderDto orderDto=new OrderDto();
		OrderMasterEntity orderMaster=orderMasterDao.selectByPrimaryKey(orderId);
		if(orderMaster==null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetailEntity> orderDetailList=orderDetailDao.findByOrderId(orderMaster.getOrderId());
		BeanUtils.copyProperties(orderMaster, orderDto);
		orderDto.setOrderDetailList(orderDetailList);
		return orderDto;
	}

	@Override
	public List<OrderDto> findList(String buyerOpenid, PageQuery pageQuery) {
		List<OrderMasterEntity> orderMasterList=orderMasterDao.findByBuyerOpenid(buyerOpenid, pageQuery);
		
		return OrderMaster2OrderDtoConverter.convert(orderMasterList);
	}

	@Override
	@Transactional
	public OrderDto cancle(OrderDto orderDto) {
		OrderMasterEntity entity=new OrderMasterEntity();
	
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			logger.error("【取消订单】 订单状态不正确, orderId{}, orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDto.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
		BeanUtils.copyProperties(orderDto, entity);
		int result=orderMasterDao.updateByPrimaryKeySelective(entity);
		if(result==0){
			logger.error("【取消订单】 更新失败,OrderMasterEntity={}",entity);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		//返回库存
		if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
			logger.error("【取消订单】 订单中无商品详情,orderDto={}",orderDto);
			throw new SellException(ResultEnum.ORDER_DEATIL_EMPTY);
		}
		List<CartDto> cartDtoList=orderDto.getOrderDetailList()
				.stream()
				.map(e -> new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
		productService.increaseStock(cartDtoList);
		//如果支付，需要退款
		if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
			payService.refund(orderDto);
		}
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto finish(OrderDto orderDto) {
		// 判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			logger.error("【订单完结】 订单状态不正确,orderId,orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMasterEntity orderMasterEntity=new OrderMasterEntity();
		BeanUtils.copyProperties(orderDto, orderMasterEntity);
		int result=orderMasterDao.updateByPrimaryKeySelective(orderMasterEntity);
		if(result==0){
			logger.error("【订单完结】 更新失败,OrderMasterEntity={}",orderMasterEntity);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto paid(OrderDto orderDto) {
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			logger.error("【订单支付完成】 订单状态不正确,orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//判断订单支付状态
		if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
			logger.error("【订单支付完成】 订单支付状态不正确,orderId={},orderPayStatus={}",orderDto.getOrderId(),orderDto.getPayStatus());
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		//修改订单支付状态
		orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMasterEntity orderMasterEntity=new OrderMasterEntity();
		BeanUtils.copyProperties(orderDto, orderMasterEntity);
		int result=orderMasterDao.updateByPrimaryKeySelective(orderMasterEntity);
		if(result==0){
			logger.error("【订单支付完成】 更新失败,OrderMasterEntity={}",orderMasterEntity);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		//推送微信模板消息
		pushService.orderStatus(orderDto);
		return orderDto;
	}

	@Override
	public PageList<OrderDto> findList(PageQuery pageQuery) {
	PageList<OrderDto> orderMasterList=orderMasterDao.findAll(pageQuery);
		
		return orderMasterList;
	}
}
