/**
 * @company 西安一体物联网科技有限公司
 * @file ProductInfoEntity.java
 * @author zhaochao
 * @date 2018年4月16日 
 */
package com.etiot.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.etiot.enums.ProductStatusEnum;
import com.etiot.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @description :商品详情实体
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月16日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoEntity {

		private String productId;

	    private String productName;

	    private BigDecimal productPrice;

	    private Integer productStock;

	    private String productDescription;

	    private String productIcon;

	    private Integer productStatus;

	    private Integer categoryType;

	    private Date createTime;

	    private Date updateTime;
	    
	    @JsonIgnore
	    public ProductStatusEnum getProductStatusEnum(){
	    	return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	    }
	    public String getProductId() {
	        return productId;
	    }

	    public void setProductId(String productId) {
	        this.productId = productId;
	    }

	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName;
	    }

	    public BigDecimal getProductPrice() {
	        return productPrice;
	    }

	    public void setProductPrice(BigDecimal productPrice) {
	        this.productPrice = productPrice;
	    }

	    public Integer getProductStock() {
	        return productStock;
	    }

	    public void setProductStock(Integer productStock) {
	        this.productStock = productStock;
	    }

	    public String getProductDescription() {
	        return productDescription;
	    }

	    public void setProductDescription(String productDescription) {
	        this.productDescription = productDescription;
	    }

	    public String getProductIcon() {
	        return productIcon;
	    }

	    public void setProductIcon(String productIcon) {
	        this.productIcon = productIcon;
	    }

	    public Integer getProductStatus() {
	        return productStatus;
	    }

	    public void setProductStatus(Integer productStatus) {
	        this.productStatus = productStatus;
	    }

	    public Integer getCategoryType() {
	        return categoryType;
	    }

	    public void setCategoryType(Integer categoryType) {
	        this.categoryType = categoryType;
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
