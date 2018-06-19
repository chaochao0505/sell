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
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list list as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getOrderStatusEnum().message}</td>
                            <td>${orderDTO.getPayStatusEnum().message}</td>
                            <td>${orderDTO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td><a href="/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                            <td>
                             <#if orderDTO.getOrderStatusEnum().message =="新订单"> 
                           		  <a href="/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
						<li><a href="/seller/order/list?page=${currentPage-1}&size=10">&laquo;</a></li>
					</#if>
						<#list 1..TotalPages as index>
						  <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                           <li><a href="/seller/order/list?page=${index}&size=10">${index}</a></li>
                        </#if>
						</#list>
					<#if currentPage gte TotalPages>
						<li class="disabled"><a href="#">&raquo;</a></li>
					<#else>
						<li><a href="/seller/order/list?page=${currentPage+1}&size=10">&raquo;</a></li>
					</#if>
					</ul>
                </div>
                
				                <#--弹窗-->
				<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                <h4 class="modal-title" id="myModalLabel">
				                    提醒
				                </h4>
				            </div>
				            <div class="modal-body">
				                你有新的订单
				            </div>
				            <div class="modal-footer">
				                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
				            </div>
				        </div>
				    </div>
				</div>
                
                
		<script type="text/javascript">
		var windowH = $(window).height();
		var height = windowH - $('.head').height() + 'px';
		$('.aaa').css({'height': height});
	</script>
</div>
</div>
</div>
		<#--播放音乐-->
		<audio id="notice" loop="loop">
		    <source src="/mp3/song.mp3" type="audio/mpeg" />
		</audio>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
 <script>
 	var websocket=null;
 	if('WebSocket' in window){
 		websocket =new WebSocket('ws://viavia.natapp1.cc/webSocket');
 	}else{
 		alert('该浏览器不支持websocket！');
 	}
 	websocket.onopen=function(event){
 		console.log('建立连接');
 	}
 	websocket.onclose=function(event){
 		console.log('关闭连接');
 	}
 	websocket.onmessage=function(event){
 		console.log('收到消息:'+event.data);
 		//弹窗播放音乐
 		  $('#myModal').modal('show');

        document.getElementById('notice').play();
 	}
 	websocket.onerror=function(){
 		alert('websocket通信发生错误！');
 	}
    window.onbeforeunload = function () {
        websocket.close();
    }
 </script>
 
 
</body>

</html>