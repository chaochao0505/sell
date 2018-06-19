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

				<#--主要内容content-->
         <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/seller/category/save">
                        <div class="form-group">
                            <label>名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
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