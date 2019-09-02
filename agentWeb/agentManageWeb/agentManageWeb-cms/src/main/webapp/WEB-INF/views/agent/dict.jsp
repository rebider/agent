<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    var dictA;
    $(function() {
        dictA = $('#dictA').datagrid({
            url : '${path }/dict/dictList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'dGroup',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '字典类型',
                field : 'dGroup'
            } , {
                title : '字典编号',
                field : 'dArtifact'
            }, {
                title : '字典数值',
                field : 'dItemvalue'
            } , {
                title : '字典名称',
                field : 'dItemname'
            } , {
                title : '字典备注',
                field : 'dItemnremark'
            } , {
                title : '字典种类',
                field : 'dSort'
            } , {
                title : '字典状态',
                field : 'dStatus',
                hidden: true    //隐藏列
            } , {
                field : 'action',
                title : '操作',
                width : 75,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/dict/deleteDict">
                    str += '&nbsp;&nbsp';
                    str += $.formatString('<a href="javascript:void(0)" class="dict-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteDictFun(\'{0}\',\'{1}\',\'{2}\');" >删除</a>', row.dGroup,row.dArtifact,row.dItemvalue);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.dict-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#dictToolbar'
        });
    });

    // 新增
    function addDictFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 700,
            height : 260,
            href : '${path }/dict/addDictPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dictA;
                    var gr = parent.$.modalDialog.handler.find('#dictAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    // 删除(编辑状态)
    function deleteDictFun(dGroup,dArtifact,dItemvalue) {
        console.log(dGroup,dArtifact,dItemvalue);
        parent.$.messager.confirm('询问', '您是否要编辑该字典状态？', function(g) {
            if (g) {
                $.post('${path }/dict/deleteDict', {
                    dGroup : dGroup,
                    dArtifact : dArtifact,
                    dItemvalue : dItemvalue
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        dictA.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }

    // 查询
    function searchDict() {
        dictA.datagrid('load', $.serializeObject($('#searchDictForm')));
    }

    // 清空
    function cleanDict() {
        $('#searchDictForm input').val('');
        dictA.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="dictA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id ="searchDictForm">
            <table>
                <tr>
                    <th>字典类型:</th>
                    <td><input name="dGroup" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;</td>
                    <th>字典编号:</th>
                    <td><input name="dArtifact" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;</td>
                    <th>字典数值:</th>
                    <td><input name="dItemvalue" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;</td>
                    <th>字典名称:</th>
                    <td><input name="dItemname" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;</td>
                </tr>
                <tr>
                    <th>字典备注:</th>
                    <td><input name="dItemnremark" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;</td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDict();">查询</a></td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDict();">清空</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="dictToolbar" style="display: none;">
    <shiro:hasPermission name="/dict/addDict">
        <a onclick="addDictFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>
