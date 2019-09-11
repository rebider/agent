<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var conditionValueDataGrid;
    $(function() {
        conditionValueDataGrid = $('#conditionValueDataGrid').datagrid({
            url : '${path }/conditionValue/conditionValueList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
           // sortName : 'jobTime',
           // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '50',
                title : '编号',
                field : 'id',
                sortable : true
            }, {
                width : '130',
                title : '名称',
                field : 'name',
                sortable : true
            } , {
                width : '130',
                title : '条件',
                field : 'conditionName',
                sortable : true
            } , {
                width : '100',
                title : '操作',
                field : 'operate',
                sortable : true
            } , {
                width : '100',
                title : '值',
                field : 'value',
                sortable : true
            }, {
                width : '150',
                title : '创建时间',
                field : 'createTime',
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                    <%--<shiro:hasPermission name="/springbatch/start">--%>
                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="updateconditionValue(\'{0}\');" >修改</a>', row.id);
//                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-del"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="deleteconditionValue(\'{0}\');" >删除</a>', row.id);
                    <%--</shiro:hasPermission>--%>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'修改'});
            },
            toolbar : '#conditionValueToolbar'
        });
    });

    function addconditionValue() {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 220,
            href : '${path }/conditionValue/toAdd',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = conditionValueDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#conditionValueAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function toUpdateconditionValue(jobId) {
        parent.$.modalDialog({
            title : '修改',
            width : 400,
            height : 220,
            href : '${path }/conditionValue/toUpdate?id='+jobId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = conditionValueDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#conditionValueUpdateForm');
                    f.submit();
                }
            } ]
        });
    }

    function updateconditionValue(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = conditionValueDataGrid.datagrid('getSelections');
            jobId = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            conditionValueDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '是否修改'+jobId+'的配置？', function(b) {
            if (b) {
                toUpdateconditionValue(jobId);
            }
        });
    }


      function deleteconditionValue(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = roleDataGrid.datagrid('getSelections');
            jobId = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            conditionValueDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/conditionValue/deleteconditionValue', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        conditionValueDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }

    //将表单数据转为json
    function form2Json(id) {

        var arr = $("#" + id).serializeArray();
        var jsonStr = "";

        jsonStr += '{';
        for (var i = 0; i < arr.length; i++) {
            jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
        }
        jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
        jsonStr += '}';

        var json = JSON.parse(jsonStr);
        console.log(json);
        return json;
    }

    $("#submit_search_conditionValue").click(function () {
        $('#conditionValueDataGrid').datagrid('load',{
            id:$('#id').val(),
//            name:$('#jobName option:selected').val(),
            name:$('#name').val(),
//            jobTime:$('#jobTime').datetimebox("getValue"),
            status:$('#status option:selected').val()
        });
    });
    
    function cleanconditionValue() {
        $('#conditionValueSearchform input').val('');
        conditionValueDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="conditionValueDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="conditionValueToolbar" style="display: none;">
    <%--<shiro:hasPermission name="/springbatch/add">--%>
        <a onclick="addconditionValue();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加条件值</a>
    <%--</shiro:hasPermission>--%>
    <form name="conditionValueSearchform" method="post" action="" id ="conditionValueSearchform">
        <span>ID:</span>
        <input id="id" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span>名称:</span>
        <input id="name" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
        <%--<span>创建时间:</span>--%>
        <%--<input class="easyui-datetimebox" id="createTime" data-options=""  style="width:140px">&nbsp&nbsp--%>
        <a id="submit_search_conditionValue" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true">查询</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanconditionValue();">清空</a>
    </form>
</div>
