<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var payCompDataGrid;
    $(function () {
        payCompDataGrid = $('#payCompListId').datagrid({
            url : '${path }/paycomp/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : 'id',
                field : 'id',
                hidden : true
            },{
                title : '打款公司名称',
                field : 'comName'
            },{
                title : '创建时间',
                field : 'cTime'
            },{
                title : '更新时间',
                field : 'cUtime'
            },{
                title : '状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "2":
                            return '无效';
                        case "1":
                            return '生效';
                    }
                }
            },{
                title : '备注',
                field : 'remark'
            }, {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editPayCompFun(\'{0}\');" >编辑</a>', row.id);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            },
            toolbar : '#payCompToolbar'
        });
    });

    function searchPayCom() {
        payCompDataGrid.datagrid('load', $.serializeObject($('#payCompForm')));
    }

    function cleanPayCom() {
        $('#payCompForm input').val('');
        payCompDataGrid.datagrid('load', {});
    }

    function addPayCompFun() {

        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 200,
            href : '${path }/paycomp/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = payCompDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#payCompAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function editPayCompFun(id) {
        if (id == undefined) {
            var rows = payCompDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            payCompDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 400,
            height : 250,
            href : '${path }/paycomp/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = payCompDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#payCompEditForm');
                    f.submit();
                }
            } ]
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="payCompListId" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="payCompForm">
            <table>
                <tr>
                    <th>打款公司名称:</th>
                    <td><input name="comName" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>状态:</th>
                    <td>
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value=""></option>
                            <option value="1">生效</option>
                            <option value="2">无效</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPayCom();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanPayCom();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="payCompToolbar" style="display: none;">
    <shiro:hasPermission name="/role/add">
        <a onclick="addPayCompFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>
