<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|人员管理-添加流动人员</title>
<#include "../common/header.ftl"/>

</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
      </div>
      <div class="lyear-layout-sidebar-scroll"> 
        <#include "../common/left-menu.ftl"/>
      </div>
      
    </aside>
    <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>
    
     <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-header"><h4>添加流动人员</h4></div>
              <div class="card-body">
                <form id="user-add-form" class="row">
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">姓名</span>
                        <input type="text" class="form-control required" id="name" name="name"
                               value="" placeholder="请输入姓名" tips="请输入姓名" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">详细地址</span>
                        <input type="text" class="form-control required" id="address" name="address"
                               value="" placeholder="示例: XXXX小区XXX栋XX号或者XXXX村XXX号" tips="请输入详细地址" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">身份证号码</span>
                        <input type="text" class="form-control required" id="cardNumber" name="cardNumber"
                               value="" placeholder="请输入身份证号码" tips="请输入身份证号码" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">户籍所在地</span>
                        <input type="text" class="form-control required" id="censusAddress" name="censusAddress"
                               value="" placeholder="请输入户籍所在地" tips="请输入户籍所在地" />
                    </div>
                    <div class="input-group" style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                        性别：
                        <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                            <#list sexList as item>
                                <label class="lyear-radio radio-inline radio-primary">
                                    <input type="radio" name="sex" value="${item.code}" <#if item.code == male.code>checked</#if>>
                                    <span>${item.value}</span>
                                </label>
                            </#list>
                        </label>
                    </div>
                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
              </div>
            </div>
          </div>
          
        </div>
        
      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script src="/admin/Distpicker/js/distpicker.data.js"></script>
<script src="/admin/Distpicker/js/distpicker.js"></script>
<script src="/admin/Distpicker/js/main.js"></script>
<script src="/admin/js/msg.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}

		//身份证号码验证
        var cardNumber = $("#cardNumber").val().trim();
		if(!msg.isCard(cardNumber))
        {
            showErrorMsg("请输入正确的身份证号码");
            return ;
        }

		var data = $("#user-add-form").serialize();
		$.ajax({
			url:'add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('添加成功!',function(){
						window.location.href = 'list';
					})
				}else{
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('网络错误!');
			}
		});
	});
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});

</script>
</body>
</html>