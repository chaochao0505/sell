<!DOCTYPE html>
<html>
	<#-- 头部 -->
<#include "../common/header.ftl">
	<body>
		<div class="box">
			<div class="row">
				<div class="col-xs-12 head">
					<h1>卖家管理系统</h1>
				</div>
				<#-- 左侧 -->
				<#include "../common/left.ftl">
		
				<div class="col-xs-11 aaa con">
					<table class="table table-hover" border="1" cellspacing="0" width="100%" height="150">
					 <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list list as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${productInfo.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td><a href="/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                            <td>
                               <#if productInfo.getProductStatusEnum().message == "在架">
                                    <a href="/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                <#else>
                                    <a href="/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                </#if>

                            </td>
                        </tr>
                        </#list>
                        </tbody>
					</table>
					
					                <#-- 分页  -->
                <div class="col-md-12 column">
					<ul class="pagination pull-right">
					<#if currentPage lte 1>
						<li class="disabled"><a href="#">&laquo;</a></li>
					<#else>
						<li><a href="/seller/product/list?page=${currentPage-1}&size=10">&laquo;</a></li>
					</#if>
						<#list 1..TotalPages as index>
						  <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                           <li><a href="/seller/product/list?page=${index}&size=10">${index}</a></li>
                        </#if>
						</#list>
					<#if currentPage gte TotalPages>
						<li class="disabled"><a href="#">&raquo;</a></li>
					<#else>
						<li><a href="/seller/product/list?page=${currentPage+1}&size=10">&raquo;</a></li>
					</#if>
					</ul>
                </div>
                
		<script type="text/javascript">
		var windowH = $(window).height();
		var height = windowH - $('.head').height() + 'px';
		$('.aaa').css({'height': height});
	</script>
</div>
</div>
</div>

 
</body>

</html>