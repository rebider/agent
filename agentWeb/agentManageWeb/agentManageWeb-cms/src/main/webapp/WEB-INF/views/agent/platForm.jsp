<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var platFormA;
    $(function() {
        platFormA = $('#platFormA').datagrid({
            url : '${path }/platForm/platFormList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : 'ID',
                field : 'id'
            } , {
                title : '平台号',
                field : 'platformNum'
            }, {
                title : '平台名称',
                field : 'platformName'
            } , {
                title : '创建时间',
                field : 'cTime',
                formatter :function(value, row, index){
                    var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            } , {
                title : '更新时间',
                field : 'cUtime',
                formatter :function(value, row, index){
                    var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            } , {
                title : '操作员',
                field : 'cUser',
                hidden: true
            } , {
                title : '平台状态',
                field : 'platformStatus',
                hidden: true
            } , {
                title : '状态',
                field : 'status',
                hidden: true
            } , {
                title : '版本信息',
                field : 'version',
                hidden: true
            } , {
                title : '平台类型',
                field : 'platformType'
            } , {
                title : '平台名称前缀',
                field : 'posanameprefix'
            } , {
                title : 'POS代理商业务类型',
                field : 'posbusitype'
            } , {
                title : '业务平台编号',
                field : 'busplatform'
            } , {
                title : '业务平台网址',
                field : 'platformUrl'
            } , {
                field : 'action',
                title : '操作',
                width : 140,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/platForm/editPlatForm">
                        str += $.formatString('<a href="javascript:void(0)" class="plat-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editPlatFormFun(\'{0}\');" >编辑</a>', row.id);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/platForm/deletePlatForm">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="plat-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletePlatFormFun(\'{0}\');" >删除</a>', row.id);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.plat-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.plat-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#platFormToolbar'
        });
    });

    // 新增
    function addPlatFormFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 620,
            height : 220,
            href : '${path }/platForm/addPlatFormPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = platFormA;
                    var gr = parent.$.modalDialog.handler.find('#platFormAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    // 编辑
    function editPlatFormFun(id) {
        parent.$.modalDialog({
            title : '编辑',
            width : 620,
            height : 220,
            href : '${path }/platForm/editPlatFormPage?id=' + id,
            buttons : [ {
                text : '编辑',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = platFormA;
                    var p = parent.$.modalDialog.handler.find('#platFormEditForm');
                    p.submit();
                }
            } ]
        });
    }

    // 删除(编辑状态)
    function deletePlatFormFun(id) {
        console.log(id);
        if (id == undefined) {
            var rows = roleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            platFormA.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除该业务平台？', function(g) {
            if (g) {
                $.post('${path }/platForm/deletePlatForm', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        platFormA.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="platFormA" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="platFormToolbar" style="display: none;">
    <shiro:hasPermission name="/platForm/addPlatForm">
        <a onclick="addPlatFormFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>
