<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetRenewOffsetList;
    $(function () {
        internetRenewOffsetList = $('#internetRenewOffsetList').datagrid({
            url: '${path }/internetRenew/internetRenewOffsetList',
            striped: true,
            pagination: true,
            rownumbers: true,
            fit: true,
            idField: 'flowId',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
            queryParams: {
                renewDetailId:$("#searchCardRenewFormOffset input[name='renewDetailId']").val()
            },
            columns: [[{
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

    function searchInternetCardRenewOffset() {
        internetRenewOffsetList.datagrid('load', $.serializeObject($('#searchCardRenewFormOffset')));
    }

    function cleanInternetCardRenewOffset() {
        $('#searchCardRenewFormOffset input').val('');
        internetRenewOffsetList.datagrid('load', {});
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetRenewOffsetList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id="searchCardRenewFormOffset">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td><input name="renewId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>明细编号:</th>
                    <td><input name="renewDetailId" style="line-height:17px;border:1px solid #ccc" value="${internetRenewOffset.renewDetailId}"></td>
                    <th>轧差流水号:</th>
                    <td><input name="flowId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>iccid:</th>
                    <td><input name="iccidNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetCardRenewOffset();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetCardRenewOffset();">清空</a>
                    </td>
                </tr>
                <tr>
                    <c:if test="${isAgent==false}">
                        <th>代理商ID:</th>
                        <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                </tr>
            </table>
        </form>
    </div>
</div>