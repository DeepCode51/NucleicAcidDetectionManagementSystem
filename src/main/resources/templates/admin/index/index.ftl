<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>个人信息查询</title>
<link rel="icon" href="/admin/images/favicon.ico" type="image/ico">
<link rel="stylesheet" href="/admin/css/style.css">
<link rel="stylesheet" href="/admin/layui/css/layui.css"/>
</head>
<body>

<div class="type-ahead" style="max-width: 690px;">
	<input type="text" id="cardNumber" class="type-ahead__input" placeholder="请输入身份证号码来查询个人信息...">
	<svg class="icon" width="24" height="24" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd" onclick="search()">
		<path d="M15.853 16.56c-1.683 1.517-3.911 2.44-6.353 2.44-5.243 0-9.5-4.257-9.5-9.5s4.257-9.5 9.5-9.5 9.5 4.257 9.5 9.5c0 2.442-.923 4.67-2.44 6.353l7.44 7.44-.707.707-7.44-7.44zm-6.353-15.56c4.691 0 8.5 3.809 8.5 8.5s-3.809 8.5-8.5 8.5-8.5-3.809-8.5-8.5 3.809-8.5 8.5-8.5z"/></svg>
	<ul class="type-ahead__suggestions hidden" style="display:none;"></ul>
	<div class="layui-form-item">
	</div>	
	<div class="layui-form-item">
		<a href="/admin/index/add_migrant">添加流动人员信息</a>
        <a style="margin-left: 15px" href="/system/login">前往后台</a>
	</div>
</div>

<script  src="/admin/js/script.js"></script>
<script  src="/admin/js/jquery.min.js"></script>
<script  src="/admin/layui/layui.all.js"></script>
<script  src="/admin/js/msg.js"></script>
<script>
	function search()
	{
	    var cardNumber = $("#cardNumber").val().trim();

	    if(msg.isEmpty(cardNumber))
		{
		    errorMsg("你输入的身份证号码为空")
		    return ;
		}

		if(!msg.isCard(cardNumber))
		{
		    errorMsg("你输入的身份证号码格式有误");
		    return ;
		}


        $.ajax({
            url:'search',
            type:'post',
            data:{
                cardNumber:cardNumber
            },
            dataType:'json',
            success:function (data) {
                if(data.code == 0){
                    setTimeout(function()
					{
					    location.href = "info";
					}, 1500);
                }else{
                    errorMsg(data.msg);
                }
            },
            error:function (data) {
                alert("网络错误")
            }
        })
	}
</script>
</body>
</html>
