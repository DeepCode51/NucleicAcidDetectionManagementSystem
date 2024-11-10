<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|核酸记录管理-添加核酸记录</title>
<#include "../common/header.ftl"/>
    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
    <link href="/admin/css/style.min.css" rel="stylesheet">
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
              <div class="card-header"><h4>添加核酸记录</h4></div>
              <div class="card-body">
                <form id="user-add-form" class="row">
                    <div class="form-group col-md-12">
                        <label>核酸检测图片上传</label>
                        <div class="form-controls">
                            <ul class="list-inline clearfix lyear-uploads-pic">
                                <li class="col-xs-4 col-sm-3 col-md-2">
                                    <figure>
                                        <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                                    </figure>
                                </li>
                                <input type="hidden" name="img" id="img">
                                <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','img')">
                                <li class="col-xs-4 col-sm-3 col-md-2">
                                    <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">姓名</span>
                        <input type="text" class="form-control required" id="name" name="name"
                               value="" placeholder="请输入姓名" tips="请输入姓名" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">地址</span>
                        <input type="text" class="form-control required" id="address" name="address"
                               value="" placeholder="请输入地址" tips="请输入地址" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">身份证号码</span>
                        <input type="text" class="form-control required" id="cardNumber" name="cardNumber"
                               value="" placeholder="请输入身份证号码" tips="请输入身份证号码" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">采集时间</span>
                        <input class="form-control js-datetimepicker" type="text" id="gatherTime" name="gatherTime"
                               placeholder="请选择具体时间" value="" data-side-by-side="true" data-locale="zh-cn" data-format="YYYY-MM-DD HH:mm" />

                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">采集地点</span>
                        <input type="text" class="form-control required" id="gatherAddress" name="gatherAddress"
                               value="" placeholder="请输入采集地点" tips="请输入采集地点" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">采集管号</span>
                        <input type="text" class="form-control required" id="gatherNumber" name="gatherNumber"
                               value="" placeholder="请输入采集管号" tips="请输入采集管号" />
                    </div><div class="input-group m-b-10">
                        <span class="input-group-addon">采集人姓名</span>
                        <input type="text" class="form-control required" id="gatherName" name="gatherName"
                               value="" placeholder="请输入采集人姓名" tips="请输入采集人姓名" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">采集人电话</span>
                        <input type="text" class="form-control required" id="gatherMobile" name="gatherMobile"
                               value="" placeholder="请输入采集人电话" tips="请输入采集人电话" />
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

<!--时间选择插件-->
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script type="text/javascript" src="/admin/js/main.min.js"></script>
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
		console.log(data);
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