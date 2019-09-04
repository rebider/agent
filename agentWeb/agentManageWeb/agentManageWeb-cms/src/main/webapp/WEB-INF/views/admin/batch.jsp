<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var springDataGrid;
    $(function() {
        springDataGrid = $('#springDataGrid').datagrid({
            url : '${path }/springbatch/find',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'jobId',
           // sortName : 'jobTime',
           // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '180',
                title : 'job编号',
                field : 'jobId',
                sortable : true
            }, {
                width : '130',
                title : '跑批名称',
                field : 'jobName',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'createRepayPlan':
                        return '计息计提批';
                    /* case 'repayPlanJob':
                        return '逾期跑批'; */
                    case 'repayNoticeJob':
                        return '还款通知批';
                    case 'batchDeductJob':
                        return '扣款批';
                    case 'findTransFlowJob':
                        return '批扣成功查询批';
                    }
                }
            } , {
            	width : '100',
                title : '跑批时间',
                field : 'jobTime',
                sortable : true,
                formatter :function(value, row, index){
                    return value;
//                	var date = new Date(value);
//                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            },  {
                width : '80',
                title : '跑批状态',
                field : 'jobStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'COMPLETED':
                        return '跑批成功';
                    case 'FAILED':
                        return '跑批失败';
                    case 'WAIT':
                        return '等待跑批';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                      var str = '';
                        <shiro:hasPermission name="/springbatch/start">
                         if (row.jobStatus == 'FAILED') {
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="startRoleFun(\'{0}\');" >重启</a>', row.jobId);
                            }
                        </shiro:hasPermission>
                       /*  <shiro:hasPermission name="/springbatch/delete">
                         if (row.jobStatus == 'COMPLETED'){
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteJobFun(\'{0}\');" >删除</a>', row.jobId);
                            }
                        </shiro:hasPermission> */
                        <shiro:hasPermission name="/springbatch/startbefore">
                            if (row.jobStatus == 'WAIT'){
                             str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-start"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="startJobBefore(\'{0}\');" >提前跑批</a>', row.jobId);
                            }
                        </shiro:hasPermission>
                    return str; 
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'重启'});
                $('.batch-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.batch-easyui-linkbutton-start').linkbutton({text:'提前跑批'});
            },
            toolbar : '#jobToolbar'
        });
    });

    function addJobFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 250,
            href : '${path }/springbatch/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = springDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#jobAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
 
    function startRoleFun(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = springDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            springDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要重启'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/springbatch/start', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        springDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
    
    function startJobBefore(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = springDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            springDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要提前跑'+jobId+'的批？', function(b) {
            if (b) {
                $.post('${path }/springbatch/startBefore', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        springDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
    
    
      function deleteJobFun(jobId) {
         console.log(jobId);
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = roleDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            springDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/springbatch/delete', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        springDataGrid.datagrid('reload');
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
        
       /*  $("#submit_search").click(function () {
        	$('#springDataGrid').datagrid('load',{
		        jobId:$('#jobId').val(),
		        jobName:$('#jobName option:selected').val(),
		        jobTime:$('#jobTime').datetimebox("getValue"),
		        jobStatus:$('#jobStatus option:selected').val()
	        });
        }); */
        
        
        function searchJobStatus() {
        	springDataGrid.datagrid('load', $.serializeObject($('#searchform')));
    	}
        
        function cleanJob() {
    		$('#searchform input').val('');
    		$("[name='jobStatus']").val('');
    		springDataGrid.datagrid('load', {});
    	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table class="easyui-datagrid" id="springDataGrid" style="width:700px;height:250px" data-options="fit:true,border:false,singleSelect:true,collapsible:true"></table>
    </div>
</div>
<div id="jobToolbar" style="display: none;">
   <%--  <shiro:hasPermission name="/springbatch/add">
        <a onclick="addJobFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加跑批</a>
    </shiro:hasPermission> --%>
   <form  id ="searchform">
		<span>job编号:</span>
		<input id="jobId" name="jobId" style="line-height:17px;border:1px solid #ccc">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span>跑批时间:</span>
		<input class="easyui-datebox" id="jobTimes" name="jobTimes" data-options=""  style="width:140px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span>跑批状态:</span>
		<select id="jobStatus" name="jobStatus" style="width:140px;height:21px">
		      <option value="">-请选择-</option>
              <option value="WAIT">等待</option>
              <option value="COMPLETED">成功</option>
              <option value="FAILED">失败</option>
        </select>
		<!-- <a id="submit_search" class="easyui-linkbutton" plain="true" >搜索</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchJobStatus();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanJob();">清空</a>
	</form>
</div>
