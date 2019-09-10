<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    var otherSupplyApplcList;

    $(function(){
        otherSupplyApplcList = $('#otherSupplyApplicList').datagrid({
            url: '${path }/profitSupply/getApplyList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '',
                field: 'ID',
                hidden: true
            },{
                title: '上级代理商唯一码',
                field: 'parentAgentId',
                width: 130
            }, {
                title: '上级代理商名称',
                field: 'parentAgentName',
                width: 130
            },{
                title: '代理商唯一码',
                field: 'agentId',
                width: 130
            },{
                title: '代理商名称',
                field: 'agentName',
                width: 130
            },{
                title: '月份',
                field: 'applicationMonth',
                width: 80
            }, {
                title: '补款类型',
                field: 'deductionRemark',
                width: 130
            },{
                title: '补款金额',
                field: 'applicationAmt',
                width: 130
            },{
                title: '备注',
                field: 'remark',
                width: 130
            },{
                title: '创建时间',
                field: 'createDate',
                width: 130
            },{
                title: '审批状态',
                field: 'applicationStatus',
                width: 130,
                formatter: function (value, row) {
                    if(value == '0'){
                        return "申请中";
                    }else if(value == '1'){
                        return "申请通过";
                    }else if(value == '2'){
                        return "申请失败";
                    }else if(value == '3'){
                        return "退回";
                    }
                    return '';
                }
            },{
                title: '审批记录',
                field: 'deductionStatus',
                width: 130,
                formatter: function (value, row) {
                    var str = '';
                        str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryApproval(\'{0}\');" >查看</a>', row.id);
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
                $('.easyui-linkbutton-add').linkbutton();
            },
            toolbar: '#otherDeductionApplicToolbar'
        });
    });

    //查询
    function searchDedcution(){
        otherSupplyApplcList.datagrid('load', $.serializeObject($('#searchApplySupplyForm')));
    }
    //重置
    function cleanDedcution(){
        $('#searchApplySupplyForm input').val('');
        otherSupplyApplcList.datagrid('load', {});
    }

    /**
     * 查看审批明细
     */
    function queryApproval(id) {
        addTab({
            title: '其他补款审批展示'+id,
            border: false,
            closable: true,
            fit: true,
            href: '${path }/profit/application/gotoTaskApproval?sourceId=' + id
        });
    }


    //其他补款- 补款申请
    function supplyApplication() {
        parent.$.modalDialog({
            title : '其他补款申请',
            width : 500,
            height : 400,
            href : '${path }/profitSupply/gotoCityApplicationPage',
            buttons : [ {
                text : '申请',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = otherSupplyApplcList;	//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#applicationDeductionForm');
                    f.submit();
                }
            } ]
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="otherSupplyApplicList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height:58px; overflow: hidden;background-color: #fff">
        <form id="searchApplySupplyForm">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="agentName" id="agentName" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <th>代理商唯一码：</th>
                    <td><input name="agentId" id="agentId" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDedcution();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDedcution();">重置</a>
                    </td>
                </tr>
            </table>
        </form>
        <%--<shiro:hasPermission name="/otherDeduction/application" >--%>
        <a onclick="supplyApplication();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请补款</a>
       <%-- </shiro:hasPermission>--%>
    </div>

</div>




