<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>我的基本信息</title>
<link rel="icon" href="/admin/images/favicon.ico" type="image/ico">
<link rel="stylesheet" href="/admin/css/style.css">
<link rel="stylesheet" href="/admin/layui/css/layui.css"/>
<style>
    .news-item
    {
        border-bottom: 1px solid #EAEAEA;
        padding: 15px 5px;
    }

    .news-item a
    {
        display: block;
        margin-bottom: 5px;
        font-size: 1.2em;
        color: #000000;
    }

    .news-item span
    {
        font-size: 1em;
        color: #696969;
    }

    .news-item:hover
    {
        box-shadow: 3px 35px 77px -17px rgba(0, 0, 0, 0.8);
        transform: scale(1.06);
        transition: all 0.3s ease-in-out;
        z-index: 100;
        background-color: #fff;
    }

    .news-image
    {
        width: 150px;
        height: 80px;
        object-fit: cover;
    }

    .news-image:hover
    {
        transform: scale(1.06);
        transition: all 0.3s ease-in-out;
        box-shadow: 3px 35px 77px -17px rgba(0, 0, 0, 0.8);
    }
</style>
</head>
<body>

<div class="type-ahead layui-form" style="max-width: 690px; background-color: white; padding: 20px; border-radius: 8px;">
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this" lay-id="0">
                <i class="layui-icon layui-icon-fire"></i>常驻信息
            </li>
            <li lay-id="1">
                <i class="layui-icon layui-icon-user"></i>流动信息
            </li>
            <li lay-id="2">
                <i class="layui-icon layui-icon-survey"></i>核酸报告
            </li>
        </ul>
        <div class="layui-tab-content">
			<#--常驻信息-->
            <div class="layui-tab-item layui-show">
                <#if resident??>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">户号:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input"value="${resident.accountNumber}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">本户地址:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" value="${resident.address}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">姓名:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" value="${resident.name}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">性别:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" value="<#if resident.sex == male.code>男<#else>女</#if>" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">身份证号:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" value="${resident.cardNumber}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">所属社区:</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" value="${resident.community.name}" disabled />
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="layui-row layui-form">
                        暂无数据
                    </div>
                </#if>

                <div class="layui-form-item" style="text-align: right">
                    <a class="layui-btn layui-btn-primary" href="/admin/index/index">
                        返回
                    </a>
                </div>
            </div>
			<#--流动信息-->
            <div class="layui-tab-item">
                <div class="layui-row layui-form">
					<#if migrant??>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">地址:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.address}" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">户籍所在地:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.censusAddress}" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">姓名:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.name}" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">性别:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="<#if migrant.sex == male.code>男<#else>女</#if>" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">身份证号:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.cardNumber}" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">所属社区:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.community.name}" disabled />
                                </div>
                            </div>
                        </div>
                        <div class="layui-row layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">是否通过:</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" value="${migrant.status.value}" disabled />
                                </div>
                            </div>
                        </div>
					    <div class="layui-row layui-form" style="text-align: right;">

						   <#if notPass.code == migrant.status.code>
                               <button class="layui-btn" onclick="edit(${migrant.id})">
                                   编辑
                               </button>
                               <button class="layui-btn layui-btn-danger" text="${migrant.reasons!""}" onclick="showMsg(this)">
                                   查看原因
                               </button>
						   <#else>
                               <button class="layui-btn layui-disabled" disabled>
                                   编辑
                               </button>
						   </#if>
					    </div>
                    <#else>
                        <div class="layui-row layui-form">
                            暂无数据
                        </div>
					</#if>
                </div>
            </div>
			<#--核酸报告-->
            <div class="layui-tab-item">
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-primary layui-btn-sm" type="button" onclick="add()">
                        <i class="layui-icon layui-icon-add-circle"></i>添加
                    </button>
                </div>
                <div class="layui-row">
                    <ul>
						<#if testRecords?size gt 0>
							<#list testRecords as item>
								<li class="news-item">
									<div class="layui-row">
										<div class="layui-col-md8">
											<a href="detail?id=${item.id}">
												身份证号码:${item.cardNumber}
											</a>
											<p class="item-date-view">
												<span>采集时间:${item.gatherTime?string('yyyy-MM-dd HH:mm')}</span>

												<span>姓名:${item.name}</span>
                                                <span>状态:
                                                      <#if item.status.code == 0>
                                                        <font color="red">
                                                      <#elseif item.status.code == 1>
                                                        <font color="green">
                                                      <#else>
                                                        <font color="#ff69b4">
                                                      </#if>
                                                    ${item.status.value}</font>
                                                </span>
											</p>
										</div>
										<div class="layui-col-md4" style="text-align: right">
											<#if item.img??>
												<#if item.img?length gt 0>
													<img src="/photo/view?filename=${item.img}" class="news-image"/>
												<#else>
													<img src="/admin/images/default-head.jpg" class="news-image"/>
												</#if>
											<#else>
												<img src="/admin/images/default-head.jpg" class="news-image"/>
											</#if>
										</div>
									</div>
								</li>
							</#list>
						<#else>
						   <li class="news-item">
							   <a>
								   暂无数据
							   </a>
						   </li>
						</#if>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>


<script  src="/admin/js/jquery.min.js"></script>
<script  src="/admin/layui/layui.all.js"></script>
<script  src="/admin/js/msg.js"></script>
<script>

    layui.use('element', function () {
       var element = layui.element;

       element.render();
    });


    function showMsg(thiz)
    {
        var btn = $(thiz);
        var text = btn.attr("text");


        layer.alert("未通过原因:" + (text == "" ? "无" : text))
    }

    /**
     * 编辑
     * @param id
     */
    function edit(id)
    {
        location.href = "edit_migrant?id=" + id;
    }

    /**
     * 添加核酸信息
     */
    function add()
    {
        location.href = "add_test_record";
    }
</script>
</body>
</html>
