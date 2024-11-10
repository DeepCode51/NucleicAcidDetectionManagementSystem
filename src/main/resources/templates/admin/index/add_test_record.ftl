<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>核酸证明上传</title>
<link rel="icon" href="/admin/images/favicon.ico" type="image/ico">
<link rel="stylesheet" href="/admin/css/style.css">
<link rel="stylesheet" href="/admin/layui/css/layui.css"/>
<script type="text/javascript" src="/admin/layui/layui.all.js"></script>
<script type="text/javascript" src="/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="/admin/js/msg.js"></script>
<style>
	#images img
	{
		margin-left: 10px;
		width: 100px;
		height: 100px;
	}
</style>
</head>
<body>

<div class="type-ahead layui-form" style="max-width: 690px;">
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">核酸图片:</label>
		<div class="layui-input-block">
			<div class="form-controls">
				<div class="layui-upload-drag" id="upload_headPic">
					<i class="layui-icon layui-icon-upload-drag"></i>
					<p>点击上传，或将文件拖拽到此处</p>
					<div class="layui-hide" id="uploadDemoView">
						<hr>
						<img src="" alt="上传成功后渲染" style="max-width: 196px">
					</div>
				</div>
			</div>
		</div>
	</div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">采集时间</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" id="gatherTime" readonly="" placeholder="yyyy-MM-dd HH:mm:ss" value="">
        </div>
    </div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">采集地点</label>
        <div class="layui-input-block">
            <input class="layui-input" name="gatherAddress" id="gatherAddress" placeholder="请输入采集地点"/>
        </div>
    </div>
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">采集管号</label>
		<div class="layui-input-block">
			<input class="layui-input" name="gatherNumber" id="gatherNumber" placeholder="请输入采集管号"/>
		</div>
	</div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">采集人姓名</label>
        <div class="layui-input-block">
            <input class="layui-input" name="gatherName" id="gatherName" placeholder="请输入采集人姓名"/>
        </div>
    </div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">采集人电话</label>
        <div class="layui-input-block">
            <input class="layui-input" name="gatherMobile" id="gatherMobile" placeholder="请输入采集人电话"/>
        </div>
    </div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">所在社区</label>
        <div class="layui-input-block">
			<select id="communtiy" name="communtiy" lay-search="">
				<option value="">---请选择社区</option>
				<#list communities as item>
					 <option value="${item.id}">${item.city}-${item.area}-${item.street}-${item.name}</option>
				</#list>
			</select>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: right">
		<button class="layui-btn" onclick="M.save()">
			<i class="layui-icon layui-icon-add-1"></i>上传
		</button>
        <button class="layui-btn layui-btn-primary" onclick="location.href='/admin/index/info'">
            <i class="layui-icon layui-icon-loading-1"></i>取消
        </button>
	</div>
</div>

<script>
	var M = {};
	M.img = "";
    M.gatherTime = "";

	layui.use(['form', 'upload', 'laydate'], function()
	{
		var form = layui.form;
		var upload = layui.upload;
		var laydate = layui.laydate;
		form.render();
		
		//文件上传
		upload.render({
			elem: '#upload_headPic',
			url: '/upload/home_upload_photo', //改成您自己的上传接口,
			size: 1024 * 1,
			accept: 'images',
			done: function (res) {
				layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', "/photo/view?filename="+ res.data);
				M.img = res.data;
			}
		});

        //日期只读
        laydate.render({
            elem: '#gatherTime'
            ,trigger: 'click'
            // ,btns: ['confirm']
            ,max:0
			,type:'datetime'
            ,done: function(value, date){
                M.gatherTime = value;

                if(date.year == null)
                    M.gatherTime = "";
            }
        });

	});

	M.save = function()
	{

		var communtiy = $("#communtiy").val().trim();
		var gatherTime = M.gatherTime;
		var img = M.img;
		var gatherAddress = $("#gatherAddress").val().trim();
		var gatherNumber = $("#gatherNumber").val().trim();
		var gatherName = $("#gatherName").val().trim();
		var gatherMobile = $("#gatherMobile").val().trim();

        if(msg.isEmpty(communtiy))
        {
            errorMsg("请选择社区或者先去添加常驻和流动人员信息");
            return ;
        }

        if(msg.isEmpty(img))
		{
		    errorMsg("请上传核酸图片");
		    return ;
		}

		if(msg.isEmpty(gatherAddress))
		{
		    errorMsg("请上传采集地点");
		    return ;
		}

		if(msg.isEmpty(gatherNumber))
		{
		    errorMsg("请输入管号");
		    return ;
		}

		if(msg.isEmpty(gatherName))
		{
		    errorMsg("请输入采集人姓名");
		    return ;
		}

		if(msg.isEmpty(gatherMobile))
		{
		    errorMsg("请输入采集人电话");
		    return ;
		}

		if(msg.isEmpty(gatherTime))
		{
		    errorMsg("请选择采集时间");
		    return;
		}

		if(!msg.isPhone(gatherMobile))
		{
		    errorMsg("请输入正确的手机号码");
		    return ;
		}

        $.ajax({
            url:'add_test_record',
            type:'POST',
            data:{
                img:M.img,
                gatherTime: gatherTime,
                gatherAddress:gatherAddress,
                gatherNumber:gatherNumber,
                gatherName:gatherName,
                gatherMobile:gatherMobile,
                communtiyId:communtiy
			},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    successMsg("上传成功")
                    setTimeout(function()
					{
					    location.href = "info";
					}, 1500);
                }else{
                    errorMsg(data.msg);
                }
            },
            error:function(data){
                alert('网络错误!');
            }
        });
	}
</script>
</body>
</html>
