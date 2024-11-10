


<table class="table table-bordered">
    <tr>
        <td colspan="2">核酸检测信息</td>
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
        <th>地址:</th>
        <td>${testRecord.address}</td>
    </tr>
    <tr>
        <th>社区:</th>
        <td>${testRecord.community.name}</td>
    </tr>
    <tr>
        <th>采集时间:</th>
        <td>${testRecord.gatherTime}</td>
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
</table>


