<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentRelateList;

    $(function() {
        var agentId=$('#agentId').val();
        var agentName=$('#agentName').val();
        var status=$('#status').val();
        agentRelateList=$('#agentRelateList').datagrid({
            url : '${path }/agentRelate/query',
            striped : true,
            pagination : true,
            singleSelect : true,
            rownumbers:true,
            queryParams:{"agentId":agentId,"agentName":agentName,"status":status},
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '申请编号',
                field : 'ID',
                width : 180,
                align : 'center',
            },{
                title : '代理商名称',
                field : 'AGENT_NAME',
                width : 150,
                align : 'center',
            },{
                title : '代理商唯一编码',
                field : 'AGENT_ID',
                width : 180,
                align : 'center',
            },{
                title : '关联公司',
                field : 'RELATE_COMPANY1',
                width : 150,
                align : 'center',
            },{
                title : '关联公司',
                field : 'RELATE_COMPANY2',
                width : 150,
                align : 'center',
            },{
                title : '关联公司',
                field : 'RELATE_COMPANY3',
                width : 150,
                align : 'center',
            },{
                title : '关联公司',
                field : 'RELATE_COMPANY4',
                width : 150,
                align : 'center',
            },{
                title : '业务类型',
                field : 'BUS_PLATFORM',
                width : 120,
                sortable : true,
                formatter : function(value, row, index) {
                    for(var i=0;i< db_options.ablePlatForm.length;i++){
                        if (db_options.ablePlatForm[i].platformNum == value) {
                            var temp = db_options.ablePlatForm[i].platformName;
                            /*if (temp.indexOf('手刷') >= 0) {
                                return '手刷';
                            } else {*/
                                return temp;
                            /*}*/
                        }
                    }
                }
            },{
                title : '状态',
                field : 'STATUS',
                width : 70,
                align : 'center',
                formatter:function(value,row,index){
                    switch(value)
                    {
                        case 0:
                            return '审批中';
                        case 1:
                            return '审批完成';
                        case 2:
                            return '拒绝';
                        case 3:
                            return '失效';
                        default:
                            return '状态异常';
                    }
                }
            },{
                title : '状态建立时间',
                field : 'CREATE_TIME',
                width : 120,
                align : 'center',
            },{
                title : '开始执行月份',
                field : 'START_MONTH',
                width : 80,
                align : 'center',
            },{
                title : '备注',
                field : 'REMARK',
                width : 200,
                align : 'center',
            },{
                title : '操作',
                field : 'OPERATE',
                width : 80,
                align : 'center',
                formatter:function(value,row,index){
                    return '<a onclick="examineDetail(\''+row.ID+'\')">查看审批进度</a>'
                }
            }]]
        });
    });
    /*
    查询
    */
    function search_agent_relate_list(){
        agentRelateList.datagrid('load', $.serializeObject($('#searchAgentRelateForm')));
    }
    /*
    清空
     */
    function clear_agent_relate_form(){
        $('#searchAgentRelateForm input').val('');
        $('#status').val('');
        agentRelateList.datagrid('load', {});
    }
    /*
    导出
    */
    function export_agent_relate_list(){
        $('#searchAgentRelateForm').form({
            url : '${path}/agentRelate/exportList',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#searchAgentRelateForm').submit();
    }

    /*
    添加关联关系
     */
    function addAgentRelate(){
        addTab({
            title: '添加关联代理商',
            border: false,
            closable: true,
            fit: true,
            href:'${path }/agentRelate/toAddPage'
        });
    }
    function examineDetail(Id){
        addTab({
            title : "查看审批进度",
            border : false,
            closable : true,
            fit : true,
            iconCls : 'fi-database',
            href:'${path }/agentRelate/examineDetail?busId='+Id
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="TABLE" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="agentRelateList" data-options="fit:true,border:false"></table>
    </div>
    <div id="formList" data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form  method="post" id ="searchAgentRelateForm" >
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input id="agentName" name="agentName"style="line-height:17px;border:1px solid #ccc"></td>
                    <th>代理商唯一编码:</th>
                    <td><input id="agentId" name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>状态</th>
                    <td>
                        <select id="status" name="status">
                        <option value="">请选择</option>
                        <option value="0">审批中</option>
                        <option value="1">审批完成</option>
                        <option value="2">拒绝</option>
                        <option value="3">失效</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="search_agent_relate_list();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="clear_agent_relate_form();">清空</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="export_agent_relate_list();">导出</a>
                    </td>
                    <td>
                        <input type="hidden" name="directly" id="directly" value=""/>
                    </td>
                </tr>

                <shiro:hasPermission name="/agentRelate/addRelateBtn">
                    <tr>
                        <a onclick="addAgentRelate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加代理商关系</a>
                    </tr>
                </shiro:hasPermission>
            </table>
        </form>
    </div>
</div>
