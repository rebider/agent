<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    var cAgentBtbA;
    $(function() {
        cAgentBtbA = $('#cAgentBtbA').datagrid({
            url : '${path }/creditCardCAgentBtb/cAgentBtbList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '业务关系编号',
                field : 'id',
            } , {
                width : '90',
                title : '代理商编号',
                field : 'agentId',
            }, {
                width : '110',
                title : '代理商名称',
                field : 'agentName',
            } , {
                width : '100',
                title : '子集合作商编号',
                field : 'cAgentId',
            }, {
                width : '110',
                title : '子集合作商名称',
                field : 'cAgentName',
            } , {
                width : '80',
                title : '业务关系',
                field : 'businessCode',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 'CREDIT_APY':
                            return '代办信用卡';
                    }
                }
            } , {
                width : '80',
                title : '创建时间',
                field : 'createtime',
                formatter :function(value, row, index){
                    var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '70',
                title : '状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '正常';
                        case 0:
                            return '删除';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 210,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/creditCardCAgentBtb/editCAgentBtb">
                        str += $.formatString('<a href="javascript:void(0)" class="btb-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCAgentBtbFun(\'{0}\');" >编辑</a>', row.id);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/creditCardCAgentBtb/addCAgentBtb">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                         str += $.formatString('<a href="javascript:void(0)" class="btb-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryCAgentBtbFun(\'{0}\');" >查看</a>', row.id);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/creditCardCAgentBtb/deleteCAgentBtb">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="btb-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteCAgentBtbFun(\'{0}\');" >删除</a>', row.id);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.btb-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.btb-easyui-linkbutton-query').linkbutton({text:'查看'});
                $('.btb-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#cAgentBtbToolbar'
        });
    });

    // 添加添加业务关系信息
    function addCAgentBtbFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
//            maximizable:true,
            href : '${path }/creditCardCAgentBtb/addCAgentBtbPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = cAgentBtbA;
                    var gr = parent.$.modalDialog.handler.find('#cAgentBtbAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    // 编辑
    function editCAgentBtbFun(id) {
        parent.$.modalDialog({
            title : '编辑',
            width : 800,
            height : 420,
            href : '${path }/creditCardCAgentBtb/editCAgentBtbPage?id=' + id,
            buttons : [ {
                text : '编辑',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = cAgentBtbA;
                    var p = parent.$.modalDialog.handler.find('#cAgentBtbEditForm');
                    p.submit();
                }
            } ]
        });
    }

    // 查看
    function queryCAgentBtbFun(id) {
        parent.$.modalDialog({
            title : '查看',
            width : 800,
            height : 420,
            href : '${path }/creditCardCAgentBtb/queryCAgentBtbPage?id=' + id,
        });
    }

    // 删除(编辑状态)
    function deleteCAgentBtbFun(id) {
        console.log(id);
        if (id == undefined) {    // 点击右键菜单才会触发这个
            var rows = roleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {    // 点击操作里面的删除图标会触发这个
            cAgentBtbA.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除该业务状态？', function(g) {
            if (g) {
                $.post('${path }/creditCardCAgentBtb/deleteCAgentBtb', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        cAgentBtbA.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }

    // 查询
    function searchCAgentBtb() {
        cAgentBtbA.datagrid('load', $.serializeObject($('#searchCAgentBtbForm')));
    }

    // 清空
    function cleanCAgentBtb() {
        $('#searchCAgentBtbForm input').val('');
        cAgentBtbA.datagrid('load', {});
    }
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="cAgentBtbA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id ="searchCAgentBtbForm">
            <table>
                <tr>
                    <th>业务关系编号:</th>
                    <td><input name="id" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商编号:</th>
                    <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <th>子集合作商编号:</th>
                    <td><input name="cAgentId" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCAgentBtb();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCAgentBtb();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="cAgentBtbToolbar" style="display: none;">
    <shiro:hasPermission name="/creditCardCAgentBtb/addCAgentBtb">
        <a onclick="addCAgentBtbFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加业务关系信息</a>
    </shiro:hasPermission>
</div>

