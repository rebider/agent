<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var custInfoDataGrid;

    $(function() {
        custInfoDataGrid = $('#custInfoDataGrid').datagrid({
            url : '${path }/cust/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'custId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ 
            {
                width : '150',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            },  {
                width : '80',
                title : '客户名',
                field : 'custName',
                sortable : true
            },  {
                width : '80',
                title : '授信额度',
                field : 'maxAmt',
                sortable : true
            },  {
                width : '80',
                title : '可用额度',
                field : 'amt',
                sortable : true
            }, {
                width : '80',
                title : '证件类型',
                field : 'custPidType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'I':
                        return '身份证';
                    case 'T':
                        return '临时身份证';
                    case 'S':
                        return '军官证/士兵证';
                    case 'P':
                        return '护照';
                    case 'L':
                        return '营业执照';
                    case 'O':
                        return '其他有效证件';
                    case 'R':
                        return '户口簿';
                    case 'H':
                        return '港澳居民来往内地通行证';
                    case 'W':
                        return '台湾同胞来往内地通行证';
                    case 'F':
                        return '外国人居留证';
                    case 'Y':
                        return '测试';
                    case 'C':
                        return '警官证';
                    case 'Z':
                        return '烟草专卖号';              
                    }
                } 
            } , {
                width : '150',
                title : '证件号码',
                field : 'custPid'
            } ,
            {
                width : '100',
                title : '手机号',
                field : 'custMobile',
                sortable : true
            } ,
           /*  {
                width : '80',
                title : '行业类型',
                field : 'industryType',
                sortable : true
            } ,
            {
                width : '80',
                title : '月收入',
                field : 'monthlyIncome',
                sortable : true
            } , */
            {
                width : '80',
                title : '婚姻状况',
                field : 'maritalStatus',
                sortable : true
            } ,
            {
                width : '80',
                title : '教育程度',
                field : 'eduStatus',
                sortable : true
            } ,
            /* {
                width : '50',
                title : '客户类型',
                field : 'custType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '自然人';
                     case'Y':
                        return '企业';
                    case 'V':
                        return 'VIP';
                    case 'B':
                        return '白名单';
                     case'E':
                        return '员工';
                    case 'F':
                        return '普通员工';
                  
                    }
                }
            } ,  */ {
                width : '80',
                title : '账号状态',
                field : 'status',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Y':
                        return '正常';
                     case 'O':
                        return '冻结';
                    case 'Q':
                        return '启用';
                    case 'N':
                        return '不启用';
                    case 'H':
                        return '有效';
                    case 'W':
                        return '无效';
                  
                    }
                } 
            },/* {
                width : '100',
                title : '时间戳',
                field : 'timestame',
                sortable : true
            }  , */ {
                width : '130',
                title : '绑定时间',
                field : 'createTime',
                sortable : true/* ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '正常';
                    case 1:
                        return '停用';
                    }
                } */
            } , {
                field : 'action',
                title : '操作',
                width : 260,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/cust/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="cust-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCustFun(\'{0}\');" >编辑</a>', row.custId);
                    </shiro:hasPermission>
                    //开始
                    <shiro:hasPermission name="/cust/editCust">
                        str += $.formatString('<a href="javascript:void(0)" class="qy-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryCustFun(\'{0}\');" >查看</a>', row.custId);
                    </shiro:hasPermission>
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
            	 $('.cust-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            	 $('.qy-easyui-linkbutton-edit').linkbutton({text:'查看'});
            },
            toolbar : '#userToolbar'
        });
    });
    
    
    //用户信息查看
    function queryCustFun(custId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1000,
            height : 300,
            href : '${path }/cust/editCustPage?custId=' + custId
        });
	}
    function addUserFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = custInfoDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = custInfoDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            custInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/user/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        custInfoDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editCustFun(id) {
        if (id == undefined) {
            var rows = custInfoDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            custInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 400,
            height : 220,
            href : '${path }/cust/editPage?custId=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = custInfoDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchCustInfo() {
        custInfoDataGrid.datagrid('load', $.serializeObject($('#searchCustForm')));
    }
    function cleanCustInfo() {
        $('#searchCustForm input').val('');
        custInfoDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchCustForm">
            <table>
                <tr>
                	<th>唯一客户号:</th>
                    <td><input name="custId" placeholder="请输入唯一客户号"/></td>
                    <th>客户名:</th>
                    <td><input name="custName" placeholder="请输入客户名"/></td>
                     <th>证件号码:</th>
                    <td><input name="custPid" placeholder="请输入合证件号码"/></td>
                     <th>手机号:</th>
                     <td><input name="custMobile" placeholder="请输入手机号"/></td>
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
    <div data-options="region:'center',border:true,title:'用户信息查询'" >
    <!--    <table>
         <tr><table id="custInfoDataGrid" data-options="fit:true,border:false"></table></tr>
         <tr>获取信用计划</tr>
         <tr><table id="custInfoDataGrid" data-options="fit:true,border:false"></table></tr>
       </table> -->
        <table id="custInfoDataGrid" data-options="fit:true,border:false"></table>
     <!--    <table id="custInfoDataGrid2" data-options="fit:true,border:false"></table> -->
    </div>
    
	<!--  <div>获取信用计划</div>
    <div data-options="region:'center',border:true,title:'授信到期审核'" >
        <table id="custInfoDataGrid" data-options="fit:true,border:false"></table>
    </div> -->
</div>
