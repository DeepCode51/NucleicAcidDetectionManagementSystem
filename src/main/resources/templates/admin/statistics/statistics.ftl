<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}-核酸记录统计</title>
    <#include "../common/header.ftl"/>
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
    <link href="/admin/css/style.min.css" rel="stylesheet">
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
        <main class="lyear-layout-content" >

            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="row" style="text-align: right">
                            <div class="col-md-4">

                            </div>

                            <div class="col-md-3">
                                <#if ylrc_user.role.id == superAdmin>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">地址</span>
                                    <select name="community" class="form-control selectpicker" id="community"
                                             data-live-search="true" data-max-options="1">
                                        <option value="0">全部地址</option>
                                        <#list communities as community>
                                            <option value="${community.id}">${community.city}-${community.area}-${community.street}-${community.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                </#if>
                            </div>

                            <div class="col-md-3">
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">日期</span>
                                    <input type="text" class="form-control required js-datepicker" id="gatherTime" name="gatherTime" value="" placeholder="yyyy-mm-dd" tips="请选择时间" data-date-format="yyyy-mm-dd"/>
                                </div>
                            </div>

                            <div class="col-md-2" style="text-align: center">
                                <button class="btn btn-primary" onclick="submit()">
                                    搜索
                                </button>
                                <#if ylrc_user.role.id != superAdmin>
                                     <button class="btn btn-danger" onclick="poiExport()">
                                         导出
                                     </button>
                                </#if>
                            </div>
                        </div>
                        <div class="col-lg-12 row">
                            <div class="col-md-6 col-lg-6">
                                <div id="pie" style="height: 800px"></div>
                            </div>
                            <div class="col-md-6 col-lg-6">
                                <div id="bar" style="height: 800px;"></div>
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

<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script type="text/javascript" src="/admin/js/echarts.min.js"></script>

<#--<script type="text/javascript" src="https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js"></script>-->
<script type="text/javascript" src="/admin/js/msg.js"></script>
<script type="text/javascript">

    //统计
    var orderChart = echarts.init(document.getElementById('pie'));
    var barChart = echarts.init(document.getElementById('bar'));

    $('#gatherTime').datepicker({
        endDate : new Date()
    });

    function submit(){
        show()
    }

    $(document).ready(
        show()
    );

    function show() {

        var datas = [];
        var gatherTime = $("#gatherTime").val().trim();
        var testRecord = {};

        var datax = []
        var datay = []
        var sum = 0;

        var userType = ${ylrc_user.role.id}
        if(userType == user_role.SUPER_ADMIN){
            var communityId = $("#community").val();
            testRecord = {
                gatherTime:gatherTime,
                communityId:communityId
            }
        }else{
            testRecord = {
                gatherTime:gatherTime,
            }
        }

        $.ajax({
            url: 'statistics',
            type:'post',
            cache: false,
            data:testRecord,
            dataType: "json",
            success: function (result) {
                for (var key in result) {
                    datas.push({
                        "value": result[key], "name": key
                    })
                    datax.push(key)
                    datay.push(result[key])
                    sum =sum + Number(result[key]);
                }
                datax.push("总人数")
                datay.push(sum)

                orderChart.setOption({
                    series: [{
                        data: datas
                    }]
                })

                barChart.setOption({
                    xAxis: {
                        type: 'category',
                        data: datax
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: datay,
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                //这里是重点
                                color: function (params) {
                                    //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                    var colorList = ['#61a0a8', '#d48265', '#91c7ae'];
                                    return colorList[params.dataIndex]
                                },
                                label: {
                                    show: true, //开启显示
                                    position: 'top', //在上方显示
                                    textStyle: { //数值样式
                                        color: 'black',
                                        fontSize: 16
                                    }
                                }
                            }
                        }
                    }]
                })
                // 设置加载等待隐藏
                /*myChart.hideLoading();*/
            }
        });

        orderChart.setOption({
            title: {
                left: 'center',
                text: '核酸记录统计',
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [{
                name:'核酸检测记录统计',
                type: 'pie',
                radius: '50%',
                label: {
                    show: false,
                    position: 'center'
                },
                labelLine: {
                    show: false
                },
            }]
        });

    }

    function poiExport()
    {
        var gatherTime = $("#gatherTime").val().trim();
        if(msg.isEmpty(gatherTime))
        {
            showErrorMsg("请选择日期");
            return ;
        }

        location.href = "/admin/testRecord/export?gatherTime=" + gatherTime;
    }

</script>
</body>
</html>