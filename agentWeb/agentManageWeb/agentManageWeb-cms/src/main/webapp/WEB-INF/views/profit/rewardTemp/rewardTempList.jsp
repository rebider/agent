<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var posRewardTempListGrid;
    $(function() {
        posRewardTempListGrid = $('#posRewardTempList').datagrid({
            url : '${path }/posRewardTemp/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '交易量对比月',
                field : 'tranContrastMonth',
                align:'center',
                width:200
            }, {
                field : 'tranTotalqj',
                title : '对比交易金额（万）',
                align:'center',
                width : 200,
                formatter : function(value, row, index) {
                    var str = row.tranTotalStart+'~'+row.tranTotalEnd;
                    return str;
                }
            },{
                title : '贷记交易量对比月',
                field : 'creditTranContrastMonth',
                align:'center',
                width:200
            },{
                title : '奖励比例',
                field : 'proportion',
                align:'center',
                width:200
            },{
                title : '活动生效分润月',
                field : 'activityValid',
                align:'center',
                width:200
            },{
                title : '设置时间',
                field : 'createTime',
                align:'center',
                width:200
            },{
                title : '修改时间',
                field : 'updateTime',
                align:'center',
                width:200
            },{
                title : '操作用户',
                field : 'operUser',
                align:'center',
                width:150
            }, {
                field : 'action',
                title: '操作',
                width: 200,
                formatter: function (value, row, index) {
                    <shiro:hasPermission name="/rexard/tiaozheng">
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit-reward" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="endit(\'{0}\');" >调整</a>', row.id);
                    return str;
                    </shiro:hasPermission>
                }
            }
            ]],
            onLoadSuccess: function (data) {
                <shiro:hasPermission name="/rexard/tiaozheng">
                $('.activity-easyui-linkbutton-edit-reward').linkbutton({text: '调整'});
                </shiro:hasPermission>

            },
            toolbar : '#rewardTempbar'
        });
    });

    function endit(id) {
        parent.$.modalDialog({
            title : '调整POS奖励',
            width : 650,
            height : 270,
            href : '${path }/posRewardTemp/editPage?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = posRewardTempListGrid;
                    var f = parent.$.modalDialog.handler.find('#posRewardTempEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function addRewardTempFun() {
        parent.$.modalDialog({
            title : '新增POS奖励模板',
            width : 650,
            height : 270,
            href : '${path }/posRewardTemp/addPage',
            buttons : [ {
                text : '保存',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = posRewardTempListGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#posRewardTempAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function searchRewardTempData() {
        posRewardTempListGrid.datagrid('load', $.serializeObject($('#posRewardTempForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="posRewardTempList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="posRewardTempForm" method="post">
            <table>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRewardTempData();">刷新</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="rewardTempbar" style="display: none;">
    <a onclick="addRewardTempFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">新增POS奖励通用模板</a>
</div>

