<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetRenewOffsetDetailList;
    $(function () {
        internetRenewOffsetDetailList = $('#internetRenewOffsetDetailList').datagrid({
            url: '${path }/internetRenew/internetRenewOffsetDetailList',
            striped: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
//            queryParams: {
//                renewDetailId:$("#searchCardRenewFormOffset input[name='renewDetailId']").val()
//            },
            columns: [[{
                title: '明细流水号',
                field: 'id'
            },{
                title: '轧差流水号',
                field: 'flowId'
            },{
                title: '申请编号',
                field: 'renewId'
            },{
                title: '明细编号',
                field: 'renewDetailId'
            },{
                title: 'iccid号',
                field: 'iccidNum'
            },{
                title: '代理商ID',
                field: 'agentId'
            },{
                title: '代理商名称',
                field: 'agentName'
            },{
                title: '商户编码',
                field: 'merId'
            },{
                title: '商户名称',
                field: 'merName'
            },{
                title: '预轧差金额',
                field: 'offsetAmt'
            },{
                title: '已轧差金额',
                field: 'alreadyOffsetAmt'
            },{
                title: '今日轧差金额',
                field: 'todayOffsetAmt'
            },{
                title: '清算状态',
                field: 'cleanStatus'
            },{
                title: '处理日期',
                field: 'processDate'
            },{
                title: '处理时间',
                field: 'processTime'
            },{
                title: '创建时间',
                field: 'cTime'
            },{
                title: '创建人',
                field: 'cUser'
            }]]
        });

    });

    function searchInternetCardRenewOffsetDetail() {
        internetRenewOffsetDetailList.datagrid('load', $.serializeObject($('#searchCardRenewFormOffsetDetail')));
    }

    function cleanInternetCardRenewOffsetDetail() {
        $('#searchCardRenewFormOffsetDetail input').val('');
        internetRenewOffsetDetailList.datagrid('load', {});
    }

    function renewOffsetDetailExport() {
        $("#searchCardRenewFormOffsetDetail").submit();
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetRenewOffsetDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 90px; overflow: hidden;background-color: #fff">
        <form id="searchCardRenewFormOffsetDetail"  action="/internetRenew/internetRenewOffsetDetailExport" method="post">
            <table>
                <tr>
                    <th>明细流水号:</th>
                    <td><input name="id" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>申请编号:</th>
                    <td><input name="renewId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>明细编号:</th>
                    <td><input name="renewDetailId" style="line-height:17px;border:1px solid #ccc" value="${internetRenewOffset.renewDetailId}"></td>
                    <th>轧差流水号:</th>
                    <td><input name="flowId" style="line-height:17px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <th>处理时间开始:</th>
                    <td>
                        <input name="processDateBegin" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                    <th>处理时间结束:</th>
                    <td>
                        <input name="processDateEnd" type="text" class="easyui-datebox" editable="false" placeholder="请输入">
                    </td>
                    <c:if test="${isAgent==false}">
                        <th>代理商ID:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                </tr>
                <tr>
                    <th>iccid:</th>
                    <td><input name="iccidNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetCardRenewOffsetDetail();">查询</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetCardRenewOffsetDetail();">清空</a>
                        <shiro:hasPermission name="/internetRenew/internetRenewOffsetDetailExport">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="renewOffsetDetailExport()">导出</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>