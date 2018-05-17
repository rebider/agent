<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var msgRecordDataGrid;

    $(function() {

        msgRecordDataGrid = $('#msgRecordDataGrid').datagrid({
            url : '${path }/msgRecord/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'recordId',
            sortName : 'recordId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[ {
                width : '120',
                title : '短信发送记录ID',
                field : 'recordId',
                sortable : true
            },
            {
                width : '100',
                title : '手机号',
                field : 'mobile',
                sortable : true
            } ,
            {
                width : '100',
                title : '失败码',
                field : 'defeatedCode',
                sortable : true
            } ,
            {
                width : '100',
                title : '交易码',
                field : 'trancode',
                sortable : true
            },
            {
                width : '100',
                title : '短信模板ID',
                field : 'templateId',
                sortable : true
            }  ,
            {
                width : '150',
                title : '日期',
                field : 'recordDate',
                sortable : true

            } ,
            {
                width : '100',
                title : ' ',
                field : 'params',
                sortable : true
            } /* , {
                field : 'action',
                title : '操作',
                width : 260,
                formatter : function(value, row, index) {
                    var str = '';
                      <shiro:hasPermission name="/user/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-audit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >到期审核</a>', row.id);
                       </shiro:hasPermission>
                        <shiro:hasPermission name="/user/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-agree" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >同意</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/user/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-refuse" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\');" >拒绝</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            } */] ],
           /*  onLoadSuccess:function(data){
            	 $('.user-easyui-linkbutton-audit').linkbutton({text:'到期审核'});
                $('.user-easyui-linkbutton-agree').linkbutton({text:'同意'});
                $('.user-easyui-linkbutton-refuse').linkbutton({text:'拒绝'});
            }, */
            toolbar : '#userToolbar'
        });
    });
    
    
    function addUserFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = msgRecordDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = msgRecordDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            msgRecordDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/user/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        msgRecordDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editUserFun(id) {
        if (id == undefined) {
            var rows = msgRecordDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            msgRecordDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path }/user/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = msgRecordDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchCustInfo() {
        msgRecordDataGrid.datagrid('load', $.serializeObject($('#searchCustForm')));
    }
    function cleanCustInfo() {
        $('#searchCustForm input').val('');
        msgRecordDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchCustForm">
            <table>
                <tr>
                    <th>短信发送记录ID:</th>
                    <td><input name="recordId" placeholder="短信发送记录ID"/></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                     <th>手机号:</th>
                     <td><input name="mobile" placeholder="请输入手机号"/></td>
                   <!--  <th>时间:</th> -->
                    <td>
                       <!--  <input name="createdateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
                        <input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /> -->
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCustInfo();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCustInfo();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'短息记录查询'" >
        <table id="msgRecordDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
