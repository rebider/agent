<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var custUserDataGrid;
    $(function() {
        custUserDataGrid = $('#custUserDataGrid').datagrid({
            url : '${path }/cCustUser/dataGrid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'userId',
            sortName : 'userId',
            sortOrder : 'asc',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '130',
                title : '用户编号',
                field : 'userId',
                sortable : true
            }, {
                width : '170',
                title : '客户编号',
                field : 'custId',
                sortable : true
            } ,{
                width : '80',
                title : '代理商id',
                field : 'agencyId',
                sortable : true
            } , /* {
                width : '80',
                title : '用户名',
                field : 'userName',
                sortable : true
            }, {
                width : '80',
                title : '密码',
                field : 'password'
            }, {
                width : '80',
                title : '最后登陆时间',
                field : 'lastLoginTime'
            }, {
                width : '80',
                title : '输错密码次数',
                field : 'loginTimes'
            }, {
                width : '80',
                title : '用户来源',
                field : 'userSource',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '手刷';
                    case 'E':
                        return '瑞刷';
                    case 'Y':
                        return '瑞烟';
                    case 'D':
                        return '其他';
                    }
                }
            },  */{
                width : '80',
                title : '用户状态',
                field : 'userStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                        case 'Y':
                            return '正常';
                        case 'N':
                            return '冻结';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 100,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/cCustUser/edit">
                    str += $.formatString('<a href="javascript:void(0)" class="custuser-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCustUserFun(\'{0}\');" >编辑</a>', row.userId);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.custuser-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            },
            toolbar : '#custUserToolbar'
        });
    });

    function editCustUserFun(id) {
        if (id == undefined) {
            var rows = custUserDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            custUserDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 750,
            height : 250,
            href : '${path }/cCustUser/editPage?userId=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = custUserDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#cCustUserEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function uploadCustUserFun(){

        parent.$.modalDialog({
            title : '导入',
            width : 500,
            height : 300,
            href : '${path }/cCustUser/toUploadPage',
            buttons : [ {
                text : '导入',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = custUserDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#cCustUserUploadForm');
                    f.submit();
                }
            } ]
        });
    }

    function searchUser() {
        custUserDataGrid.datagrid('load', $.serializeObject($('#searchUserForm')));
    }
    function cleanUser() {
        $('#searchUserForm input').val('');
        custUserDataGrid.datagrid('load', {});
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false"  style="overflow: hidden;">
		<table id="custUserDataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id ="searchUserForm">
			<table>
				<tr>
					<th>用户编号:</th>
					<td><input name="userId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchUser();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanUser();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="custUserToolbar" style="display: none;">
	<a onclick="uploadCustUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-upload icon-green'">导入商户</a>
</div>
</div>