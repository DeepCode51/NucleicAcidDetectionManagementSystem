<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|用户管理-添加用户</title>
<#include "../common/header.ftl"/>
  <link rel="stylesheet" href="/admin/js/bootstrap-select/dist/css/bootstrap-select.min.css">
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
              <div class="card-header"><h4>添加用户</h4></div>
              <div class="card-body">
                <form action="add" id="user-add-form" method="post" class="row">
                  <div class="form-group col-md-12">
                    <label>头像上传</label>
                    <div class="form-controls">
                      <ul class="list-inline clearfix lyear-uploads-pic">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                            <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                          </figure>
                        </li>
                        <input type="hidden" name="headPic" id="headPic">
                        <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','headPic')">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">用户名</span>
                    <input type="text" class="form-control required" id="username" name="username" value="" placeholder="请输入用户名" tips="请填写用户名" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">登录密码</span>
                    <input type="password" class="form-control required" id="password" name="password" value="" placeholder="请输入登录密码" tips="请填写登录密码" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">所属角色</span>
                    <select name="role.id" class="form-control" id="role">
                    	<#list roles as role>
                    	<option value="${role.id}">${role.name}</option>
                    	</#list>
                    </select>
                  </div>
                  <div class="input-group m-b-10" id="add-community" style="display:none">
                    <span class="input-group-addon">负责地点</span>
                    <select name="community" class="form-control selectpicker" id="community"
                             data-live-search="true" data-max-options="1">
                      <#list communities as community>
                        <option value="${community.id}">${community.city}-${community.area}-${community.street}-${community.name}</option>
                      </#list>
                    </select>
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

<script type="text/javascript" src="/admin/js/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap-select/js/i18n/defaults-zh_CN.js"></script>

<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}
		var data = $("#user-add-form").serialize();
		$.ajax({
			url:'add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('用户添加成功!',function(){
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

$("#role").change(function () {
  var roleId = $(this).val()
  console.log(roleId)
  if(roleId == user_role.SUPER_ADMIN){
    $("#add-community").hide();
    $("#community").val(null);
  }else{
    $("#add-community").show();
  }
})

</script>
</body>
</html>