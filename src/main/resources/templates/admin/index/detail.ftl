<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>核酸详情</title>
<link rel="icon" href="/admin/images/favicon.ico" type="image/ico">
<link rel="stylesheet" href="/admin/css/style.css">
<link rel="stylesheet" href="/admin/layui/css/layui.css"/>
<style>
    .layui-table img
    {
        max-height: 400px;
        max-width: 95%;
        object-fit: cover;
    }
</style>
</head>
<body>

<div class="type-ahead layui-form" style="max-width: 690px; background-color: white; padding: 20px; border-radius: 8px;">
    <table class="layui-table">
        <tr>
            <td colspan="2">个人核酸信息</td>
        </tr>
        <tr>
            <th>姓名:</th>
            <td>${testRecord.name}</td>
        </tr>
        <tr>
            <th>性别:</th>
            <td>
                <#if testRecord.sex == male.code>
                    男
                <#else>
                    女
                </#if>
            </td>
        </tr>
        <tr>
            <th>详细地址:</th>
            <td>${testRecord.address}</td>
        </tr>
        <tr>
            <th>社区:</th>
            <td>${testRecord.community.name}</td>
        </tr>
        <tr>
            <th>采集时间:</th>
            <td>${testRecord.gatherTime?string("yyyy-MM-dd HH:mm")}</td>
        </tr>
        <tr>
            <th>采集地点:</th>
            <td>${testRecord.gatherAddress}</td>
        </tr>
        <tr>
            <th>采集管号:</th>
            <td>${testRecord.gatherNumber}</td>
        </tr>
        <tr>
            <th>采集人姓名:</th>
            <td>${testRecord.gatherName}</td>
        </tr>
        <tr>
            <th>采集人电话:</th>
            <td>${testRecord.gatherMobile}</td>
        </tr>
        <#if testRecord.status.code == notPass.code>
          <tr>
              <th>未通过理由:</th>
              <td>${testRecord.reasons!""}</td>
          </tr>
        </#if>
        <tr>
            <th>核酸图片:</th>
            <td>
                <#if testRecord.img??>
                    <#if testRecord.img?length gt 0>
                        <img src="/photo/view?filename=${testRecord.img}" class="news-image"/>
                    <#else>
                        <img src="/admin/images/default-head.jpg" class="news-image"/>
                    </#if>
                <#else>
					<img src="/admin/images/default-head.jpg" class="news-image"/>
                </#if>
            </td>
        </tr>
        <tr>
            <th>操作:</th>
            <td style="text-align: right">
                <button class="layui-btn" onclick="printme()">打印</button>
                <button class="layui-btn layui-btn-primary" onclick="location.href='/admin/index/info'">返回</button>
            </td>
        </tr>
    </table>
</div>

<script  src="/admin/js/jquery.min.js"></script>
<script  src="/admin/layui/layui.all.js"></script>
<script  src="/admin/js/msg.js"></script>
<script>
    function printme()
    {
        var bdhtml=window.document.body.innerHTML;
        window.document.body.innerHTML=bdhtml; //把需要打印的指定内容赋给body.innerHTML
        window.print(); //调用浏览器的打印功能打印指定区域
        window.document.body.innerHTML=bdhtml; // 最后还原页面
    }
</script>
</body>
</html>
