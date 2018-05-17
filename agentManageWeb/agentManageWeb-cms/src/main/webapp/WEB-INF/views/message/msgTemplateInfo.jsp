<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var msgTemplateInfoDataGrid;

    $(function() {

        msgTemplateInfoDataGrid = $('#msgTemplateInfoDataGrid').datagrid({
            url : '${path }/template/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'templateId',
            sortName : 'templateId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '短信模板编号',
                field : 'templateId',
                sortable : true
            },
            {
                width : '700',
                title : '短信模板内容',
                field : 'templateContent',
                sortable : true
            } , {
                field : 'action',
                title : '操作',
                width : 260,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/user/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-agree" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editMsgTemplate(\'{0}\');" >同意</a>', row.templateId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/user/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-refuse" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\');" >拒绝</a>', row.templateId);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
             onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-agree').linkbutton({text:'修改'});
                $('.user-easyui-linkbutton-refuse').linkbutton({text:'删除'});
            }, 
            toolbar : '#userToolbar'
        });
    });
    
    function addMessageTemplate() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/template/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = msgTemplateInfoDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#msgTemplateAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(templateId) {
    	/*  alert("chuanru----templateId--"+templateId); */
        if (templateId == undefined) {//点击右键菜单才会触发这个
            var rows = msgTemplateInfoDataGrid.datagrid('getSelections');
            templateId = rows[0].templateId;
        } else {//点击操作里面的删除图标会触发这个
            msgTemplateInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/template/delete', {
                    templateId : templateId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        msgTemplateInfoDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editMsgTemplate(templateId) {
    	/*  alert("chuanru----templateId--"+templateId); */
        if (templateId == undefined) {
        	
            var rows = msgTemplateInfoDataGrid.datagrid('getSelections');
            templateId = rows[0].templateId;
            alert("rows[0].templateId"+rows[0].templateId);
        } else {
            msgTemplateInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path }/template/editPage?templateId='+templateId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = msgTemplateInfoDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#msgTemplateEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchMsgInfo() {
        msgTemplateInfoDataGrid.datagrid('load', $.serializeObject($('#searchMsgTemplateForm')));
    }
    function cleanMsgInfo() {
        $('#searchMsgTemplateForm input').val('');
        msgTemplateInfoDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchMsgTemplateForm">
            <table>
                <tr>
                    <th>短信模板编号:</th>
                    <td><input name="templateId" placeholder="请输入短信模板编号"/></td>
                   <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchMsgInfo();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanMsgInfo();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'短信模板信息查询'" >
        <table id="msgTemplateInfoDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="userToolbar" style="display: none;">
    <shiro:hasPermission name="/user/add">
        <a onclick="addMessageTemplate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>
