<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var agentRule;
    $(function() {
        agentRule = $('#agentRule').datagrid({
            url : '${path }/evaluate/queryList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '编号',
                field : 'NUM'
            }, {
                title : '姓名',
                field : 'NAME'
            }, {
                title : '服务水平',
                field : 'SERVER_LEVEL'
            }, {
                title : '服务态度',
                field : 'SERVER_ATTITUDE'
            } , {
                title : '服务流程',
                field : 'SERVER_FLOW'
            }, {
                title : '配送时效',
                field : 'SHIPPING_TIME'
            }, {
                title : '经营指导',
                field : 'OPERATING_GUIDING'
            }, {
                title : '按时拜访',
                field : 'VISIT'
            }, {
                title : '问题解决',
                field : 'PROBLEM_SOLVING'
            }, {
                title : '意见建议',
                field : 'OPINIONS'
            }, {
                title : '评价日期',
                field : 'EVALUATE_TIOM'
            }, {
                title : '商户编号',
                field : 'MERCHANT_NUM'
            }, {
                title : '商户名称',
                field : 'MERCHANT_NAME'
            }, {
                title : '角色',
                field : 'PERSON',
           formatter : function(value, row, index) {
               if (value=="CUSTOMER_MANAGE"){
                   return "客户经理";
               }else if(value=="INSPECTOR"){
                   return "稽查员";
               }else if(value=="COURIER"){
                   return "配送员";
               }
           }
            }
            ] ],
            toolbar : '#agentRuleToolbar'
        });
    });

    function searchAgent() {
        agentRule.datagrid('load', $.serializeObject($('#searchAgentForm')));
    }
    function cleanAgent() {
        $('#searchAgentForm input').val('');
        $("[name='agentStatus']").val('');
        agentRule.datagrid('load', {});
    }

    function addRuleFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/creditcardagency/addRulePage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = agentRule;
                    var gr = parent.$.modalDialog.handler.find('#ruleAddForm');
                    gr.submit();
                }
            } ]
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="agentRule" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id ="searchAgentForm" action="${path }/evaluate/exportFile" method="post">
            <table>
                <tr>
                    <th>服务水平:</th>
                    <td><input name="serverLevel" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>评价日期:</th>
                    <td><input name="startTime" class="easyui-datetimebox"></td>
                    <td>至</td>
                    <td><input name="endTime" class="easyui-datetimebox"></td>
                    <th>编号:</th>
                    <td><input name="num" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>姓名:</th>
                    <td><input name="name" style="line-height:17px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <th>商户编号:</th>
                    <td><input name="merchantNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>商户名称:</th>
                    <td><input name="merchantName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>角色:</th>
                    <td>
                        <select name="person" style="width:140px;height:21px" >
                            <option value="">-请选择-</option>
                            <option value="CUSTOMER_MANAGE">客户经理</option>
                            <option value="INSPECTOR">稽查员</option>
                            <option value="COURIER">配送员</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
                    </td>
                    <td>
                        <button type="submit">导出</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


