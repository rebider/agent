<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetRenewDetailList;
    $(function () {
        internetRenewDetailList = $('#internetRenewDetailList').datagrid({
            url: '${path }/internetRenew/internetRenewDetailList',
            striped: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '明细编号',
                field: 'id'
            },{
                title: '申请编号',
                field: 'renewId'
            },{
                title: 'iccid号',
                field: 'iccidNum'
            },{
                title: '订单号',
                field: 'orderId'
            },{
                title: '机具SN',
                field: 'snNum'
            },{
                title: '代理商ID',
                field: 'agentId'
            },{
                title: '代理商名称',
                field: 'agentName'
            },{
                title: '商户名称',
                field: 'merName'
            },{
                title: '商户编码',
                field: 'merId'
            },{
                title: '物联卡号',
                field: 'internetCardNum'
            },{
                title: '开户日期',
                field: 'openAccountTime'
            },{
                title: '到期日期',
                field: 'expireTime'
            },{
                title: '续费方式',
                field: 'renewWayName'
            },{
                title: '轧差金额',
                field: 'offsetAmt'
            },{
                title: '续费金额',
                field: 'renewAmt'
            },{
                title: '应扣金额',
                field: 'oughtAmt'
            },{
                title: '实扣金额',
                field: 'realityAmt'
            },{
                title: '续费状态',
                field: 'renewStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '未续费';
                        case "2":
                            return '已续费';
                        case "3":
                            return '未扣足';
                        case "4":
                            return '续费中';
                        case "5":
                            return '失效';
                    }
                }
            },{
                title: '创建时间',
                field: 'cTime'
            },{
                title: '创建人',
                field: 'cUser'
            },{
                field: 'action',
                title: '操作',
                width: 110,
                formatter: function (value, row, index) {
                    var str = '';
                    if(row.renewWay=='XXBKGC' || row.renewWay=='FRDKGC')
                    str += $.formatString('<a href="javascript:void(0)" class="reneOffsetwd-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-page\'" onclick="internetCardRenewOffsetQuery(\'{0}\',\'{1}\');" >查看轧差明细</a>', row.id);
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.reneOffsetwd-easyui-linkbutton-look').linkbutton({text: '查看轧差明细'});
            }
        });

    });

    function searchInternetCardRenewDetail() {
        internetRenewDetailList.datagrid('load', $.serializeObject($('#searchCardRenewFormDetail')));
    }

    function cleanInternetCardRenewDetail() {
        $('#searchCardRenewFormDetail input').val('');
        internetRenewDetailList.datagrid('load', {});
    }

    function internetCardRenewDetailExport() {
        $("#searchCardRenewFormDetail").submit();
    }


    function internetCardRenewOffsetQuery(renewDetailId) {
        $('#index_tabs').tabs('close',"续费轧差明细");
        addTab({
            title: '续费轧差明细',
            border: false,
            closable: true,
            fit: true,
            href: '/internetRenew/toInternetRenewOffsetList?renewDetailId=' + renewDetailId
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetRenewDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 80px; overflow: hidden;background-color: #fff">
        <form id="searchCardRenewFormDetail" action="/internetRenew/internetCardRenewDetailExport" method="post">
            <table>
                <tr>
                    <th>iccid:</th>
                    <td><input name="iccidNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>申请编号:</th>
                    <td><input name="renewId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>明细编号:</th>
                    <td><input name="id" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>SN编号:</th>
                    <td><input name="snNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>续费方式:</th>
                    <td>
                        <select class="easyui-combobox" name="renewWay" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${internetRenewWayList}" var="internetRenewWay" >
                                <option value="${internetRenewWay.key}">${internetRenewWay.value}</option>
                            </c:forEach>
                        </select>
                    </td>

                </tr>
                <tr>
                    <c:if test="${isAgent==false}">
                        <th>代理商ID:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>续费状态:</th>
                    <td>
                        <select class="easyui-combobox" name="renewStatus" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${internetRenewStatusList}" var="internetRenewStatus" >
                                <option value="${internetRenewStatus.key}">${internetRenewStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetCardRenewDetail();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetCardRenewDetail();">清空</a>
                        <shiro:hasPermission name="/internetRenew/internetCardRenewDetailExport">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="internetCardRenewDetailExport()">导出</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>