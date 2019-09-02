<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var mergeBusinfoList;
    $(function () {
        mergeBusinfoList = $('#mergeBusinfoList').datagrid({
            url: '${path }/agentMerge/getMergeBusinfoList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/agentMergeX/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '明细编号',
                field: 'ID'
            },{
                title: '合并申请ID',
                field: 'AGENT_MARGE_ID'
            },{
                title: '主代理商唯一编码',
                field: 'MAIN_AGENT_ID'
            },{
                title: '主代理商名称',
                field: 'MAIN_AG_NAME'
            },{
                title: '副代理商唯一编码',
                field: 'SUB_AGENT_ID'
            },{
                title: '副代理商名称',
                field: 'SUB_AG_NAME'
            },{
                title: '业务ID',
                field: 'BUS_ID'
            },{
                title: '业务平台号',
                field: 'BUS_NUM'
            },{
                title: '业务平台',
                field: 'BUS_PLATFORM'
            },{
                title: '业务类型',
                field: 'BUS_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '二代直签直发';
                        case "2":
                            return '机构';
                        case "3":
                            return '机构一代';
                        case "5":
                            return '一代X';
                        case "6":
                            return '标准一代';
                        case "8":
                            return '直签不直发';
                    }
                }
            },{
                title: '所属上级代理',
                field: 'BUS_PARENT'
            },{
                title: '合并状态',
                field: 'MERGE_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未生效';
                        case 1:
                            return '已生效';
                        case 2:
                            return '被合并';
                    }
                }
            },{
                title: '创建时间',
                field: 'C_TIME'
            },{
                title: '更新时间',
                field: 'C_UTIME'
            }]],
            toolbar: '#mergeBusinfoToolbar'
        });
    });

    function searchMergeBusinfo() {
        mergeBusinfoList.datagrid('load', $.serializeObject($('#searchMergeBusinfoForm')));
    }

    function cleanMergeBusinfo() {
        $('#searchMergeBusinfoForm input').val('');
        mergeBusinfoList.datagrid('load', $.serializeObject($('#searchMergeBusinfoForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'合并业务明细列表'" style="width:100%;overflow: hidden; ">
        <table id="mergeBusinfoList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchMergeBusinfoForm">
            <table>
                <tr>
                    <th>合并申请ID:</th>
                    <td>
                        <input name="agentMargeId" style="line-height:17px;border:1px solid #ccc">
                    </td>
                    <c:if test="${empty agentId}">
                        <th>主代理商唯一编码:</th>
                        <td><input name="mainAgentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>副代理商唯一编码:</th>
                    <td><input name="subAgentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>业务ID:</th>
                    <td><input name="busId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchMergeBusinfo();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanMergeBusinfo();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>