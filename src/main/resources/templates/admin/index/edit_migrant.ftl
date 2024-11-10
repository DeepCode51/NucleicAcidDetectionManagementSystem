<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>流动信息编辑</title>
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
		<label class="layui-form-label">图片:</label>
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
        <label class="layui-form-label"></label>
        <div class="layui-input-block" id="images">
			<#list migrant.images as img>
                <img src='${img}'/>
			</#list>
        </div>
    </div>
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">性别</label>
		<div class="layui-input-block">
			<select name="sex" id="sex">
				<#list sexList as item>
                    <option value="${item.code}" <#if migrant.sex == item.code>selected</#if>>${item.value?substring(0,1)}</option>
				</#list>
			</select>
		</div>
	</div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input class="layui-input" name="name" id="name" placeholder="请输入姓名" value="${migrant.name}"/>
        </div>
    </div>
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">详细地址</label>
		<div class="layui-input-block">
			<input class="layui-input" name="address" id="address" placeholder="示例: XXXX小区XXX栋XX号或者XXXX村XXX号" value="${migrant.address}"/>
		</div>
	</div>
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">身份证号码</label>
		<div class="layui-input-block">
			<input class="layui-input layui-disabled" name="cardNumber" id="cardNumber"
				   placeholder="请输入身份证号码" value="${migrant.cardNumber}" disabled/>
		</div>
	</div>
	<div class="layui-form-item" style="background-color: white;">
		<label class="layui-form-label">户籍所在地</label>
		<div class="layui-input-block">
			<input class="layui-input" name="censusAddress" id="censusAddress" placeholder="请输入户籍所在地" value="${migrant.censusAddress}"/>
		</div>
	</div>
    <div class="layui-form-item" style="background-color: white;">
        <label class="layui-form-label">所在社区</label>
        <div class="layui-input-block">
			<select id="communtiy" name="communtiy" lay-search="" disabled class="layui-disabled">
				<option value="">---请选择社区</option>
				<#list communtiys as item>
					 <option value="${item.id}" <#if item.id == migrant.community.id>selected</#if>>${item.city}-${item.area}-${item.street}-${item.name}</option>
				</#list>
			</select>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: right">
		<button class="layui-btn" onclick="M.save()">
			<i class="layui-icon layui-icon-edit"></i>编辑提交
		</button>
        <button class="layui-btn layui-btn-primary" onclick="location.href='/admin/index/info'">
            <i class="layui-icon layui-icon-loading-1"></i>取消
        </button>
	</div>
</div>

<script>
	var M = {};
	M.id = ${migrant.id};
	M.images = JSON.parse('${migrant.cardImg!"[]"}');
	layui.use(['form', 'upload'], function()
	{
		var form = layui.form;
		var upload = layui.upload;
		form.render();
		
		//文件上传
		upload.render({
			elem: '#upload_headPic',
			url: '/upload/home_upload_photo', //改成您自己的上传接口,
			size: 1024 * 1,
			accept: 'images',
			multiple:true,
			done: function (res) {
				M.images.push(res.data);
				layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', "/photo/view?filename="+ res.data);
			},
			before:function()
			{
				M.images = [];
			},
			allDone:function()
			{
				//显示图片
				var view = $("#images");
				view.html('');
	
				for(var item of M.images)
				{
					var image = "<img src='/photo/view?filename="+item+"' />";
					view.append(image);
				}
			}
		});
		
	});

	M.save = function()
	{
	    if(M.images.length != 2)
		{
		    errorMsg("请上传身份证正反面");
		    return ;
		}

		var name = $("#name").val().trim();
		var sex = $("#sex").val().trim();
		var address = $("#address").val().trim();
		var censusAddress = $("#censusAddress").val().trim();

		if(msg.isEmpty(name))
		{
		    errorMsg("请输入姓名");
		    return ;
		}

        if(msg.isEmpty(address))
        {
            errorMsg("请输入详细地址");
            return ;
        }

        if(msg.isEmpty(censusAddress))
        {
            errorMsg("请输入户籍所在地");
            return ;
        }

        $.ajax({
            url:'edit_migrant',
            type:'POST',
            data:{cardImg:JSON.stringify(M.images),name:name, sex:sex, address:address,
				censusAddress:censusAddress, id:M.id},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    successMsg("编辑成功等待管理员审核")
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
