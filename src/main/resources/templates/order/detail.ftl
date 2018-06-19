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

									<div id="wrapper" class="toggled">
										<#--主要内容content-->
											<div id="page-content-wrapper">
												<div class="container">
													<div class="row clearfix">
														<div class="col-md-6 column">
															<table class="table table-bordered">
																<thead>
																	<tr>
																		<th>订单id</th>
																		<th>订单总金额</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td>${orderDto.orderId}</td>
																		<td>${orderDto.orderAmount}</td>
																	</tr>
																</tbody>
															</table>
														</div>

														<#--订单详情表数据-->
															<div class="col-md-12 column">
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>商品id</th>
																			<th>商品名称</th>
																			<th>价格</th>
																			<th>数量</th>
																			<th>总额</th>
																		</tr>
																	</thead>
																	<tbody>
																		<#list orderDto.orderDetailList as orderDetail>
																			<tr>
																				<td>${orderDetail.productId}</td>
																				<td>${orderDetail.productName}</td>
																				<td>${orderDetail.productPrice}</td>
																				<td>${orderDetail.productQuantity}</td>
																				<td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
																			</tr>
																		</#list>
																	</tbody>
																</table>
															</div>

															<#--操作-->
																<div class="col-md-12 column">
																	<#if orderDto.getOrderStatusEnum().message=="新订单">
																		<a href="/seller/order/finish?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
																		<a href="/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
																	</#if>
																</div>
													</div>
												</div>
											</div>
									</div>

									<script type="text/javascript">
										var windowH = $(window).height();
										var height = windowH - $('.head').height() + 'px';
										$('.aaa').css({
											'height': height
										});
									</script>
								</div>
					</div>
				</div>

			</body>

</html>