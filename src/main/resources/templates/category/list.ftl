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
				<div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目id</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list categoryList as category>
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryType}</td>
                            <td>${category.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${category.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td><a href="/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
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

 
</body>

</html>