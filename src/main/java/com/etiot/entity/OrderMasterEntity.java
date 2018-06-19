/**
 * @company 西安一体物联网科技有限公司
 * @file OrderMasterEntity.java
 * @author zhaochao
 * @date 2018年4月17日 
 */
package com.etiot.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description :订单实体类
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月17日
 */
public class OrderMasterEntity {

	  private String orderId;

	    private String buyerName;

	    private String buyerPhone;

	    private String buyerAddress;

	    private String buyerOpenid;

	    private BigDecimal orderAmount;
	    
	    /**
	     * 订单状态，默认为0新下单
	     */
	    private Integer orderStatus;
	    
	    /**
	     * 支付状态，默认为0未支付
	     */
	    private Integer payStatus;

	    private Date createTime;

	    private Date updateTime;

	    public String getOrderId() {
	        return orderId;
	    }

	    public void setOrderId(String orderId) {
	        this.orderId = orderId;
	    }

	    public String getBuyerName() {
	        return buyerName;
	    }

	    public void setBuyerName(String buyerName) {
	        this.buyerName = buyerName;
	    }

	    public String getBuyerPhone() {
	        return buyerPhone;
	    }

	    public void setBuyerPhone(String buyerPhone) {
	        this.buyerPhone = buyerPhone;
	    }

	    public String getBuyerAddress() {
	        return buyerAddress;
	    }

	    public void setBuyerAddress(String buyerAddress) {
	        this.buyerAddress = buyerAddress;
	    }

	    public String getBuyerOpenid() {
	        return buyerOpenid;
	    }

	    public void setBuyerOpenid(String buyerOpenid) {
	        this.buyerOpenid = buyerOpenid;
	    }

	    public BigDecimal getOrderAmount() {
	        return orderAmount;
	    }

	    public void setOrderAmount(BigDecimal orderAmount) {
	        this.orderAmount = orderAmount;
	    }

	    public Integer getOrderStatus() {
	        return orderStatus;
	    }

	    public void setOrderStatus(Integer orderStatus) {
	        this.orderStatus = orderStatus;
	    }

	    public Integer getPayStatus() {
	        return payStatus;
	    }

	    public void setPayStatus(Integer payStatus) {
	        this.payStatus = payStatus;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }
}
