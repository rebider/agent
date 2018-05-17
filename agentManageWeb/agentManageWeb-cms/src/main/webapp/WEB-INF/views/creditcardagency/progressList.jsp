<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var progressDataList;
    var bankRecordDataList;
    $(function() {
        progressDataList = $('#progressDataList').datagrid({
            url : '${path }/creditcardagency/progressDataList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : 'ID',
                field : 'id'
            },{
                title : '商户号',
                field : 'custId'
            },{
                title : '合作商ID',
                field : 'agentId'
            }, {
                title : '银行名称',
                field : 'bankName'
            }, {
                title : '身份证号',
                field : 'pid'
            } , {
                title : '手机号',
                field : 'mobile'
            } , {
                title : '姓名',
                field : 'realname'
            }, {
                title : '状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '失效';
                        case 1:
                            return '申请成功';
                        case 2:
                            return '审批中';
                        case 3:
                            return '申请失败';
                    }
                }
            }, {
                title : '申请时间',
                field : 'applyDate'
            }, {
                title : '成功时间',
                field : 'apySucDate'
            }, {
                title : '分润状态',
                field : 'shareStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "Y":
                            return '已分润';
                        case "N":
                            return '未分润';
                    }
                }
            },{
                title : '版本',
                field : 'version'
            },{
                field : 'action',
                title : '操作',
                width : 100,
                formatter : function(value, row, index) {
                    var status = row.status;
					if(status!=3){
						var str = '';
						<shiro:hasPermission name="/creditcardagency/manualDispose">
						str += $.formatString('<a href="javascript:void(0)" class="sdcl-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="manualDispose(\'{0}\');" >手动处理</a>', row.id);
						</shiro:hasPermission>
						return str;
                    }
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.sdcl-easyui-linkbutton-edit').linkbutton({text:'手动处理'});
            }
        });

        bankRecordDataList = $('#bankRecordDataList').datagrid({
            url : '${path }/creditcardagency/apyBankRecordList',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            columns : [[{
                title : 'ID',
                field : 'id'
            },{
                title : '银行名称',
                field : 'bankName'
            },{
                title : '批次',
                field : 'batchid'
            },{
                title : '数据类型',
                field : 'datatype',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "0":
                            return "首刷";
                        case "1":
                            return '申请成功';
						case "3":
                            return '申请失败';
                    }
                }
            }
            ,{
                title : '创建时间',
                field : 'createdate'
            }]]
        });

        $('#queryBankResult').click(function(){
            var rows = progressDataList.datagrid('getSelections');
            i = rows.length-1;
            apyid = rows[i].id;
            bankRecordDataList.datagrid('load', {
                apyid: apyid
            });
            bankRecordDataList.datagrid('unselectAll').datagrid('uncheckAll');
        });

    });

   function searchAgent() {
       progressDataList.datagrid('load', $.serializeObject($('#searchAgentForm')));
	}
	function cleanAgent() {
		$('#searchAgentForm input').val('');
		$("[name='status']").val('');
        progressDataList.datagrid('load', {});
	}

	function manualDispose(id){
        parent.$.modalDialog({
            title : '手动处理',
            width : 500,
            height : 300,
            href : '${path }/creditcardagency/manualDisposePage?id=' + id,
            buttons : [{
                text : '手动处理',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = progressDataList;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#manualDisposeForm');
                    f.submit();
                }
            } ]
        });
    }

	function analysisBankNotify(){
        parent.$.modalDialog({
            title : '导入',
            width : 500,
            height : 300,
            href : '${path }/creditcardagency/toUploadBankNotifyPage',
            buttons : [{
                text : '导入',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = progressDataList;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#uploadBankNotifyForm');
                    f.submit();
                }
            } ]
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryBankResult" data-options="region:'west',border:true,title:'进度列表'"  style="width:55%;overflow: hidden; ">
		<table id="progressDataList" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'center',border:true,title:'银行处理列表'" >
		<table id="bankRecordDataList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAgentForm">
			<table>
				<tr>
					<th>商户号:</th>
					<td><input name="custId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>状态:</th>
					<td>
						<select name="status" style="width:140px;height:21px" >
						  <option value="">-全部-</option>
						  <option value="1">申请成功</option>
						  <option value="2">审批中</option>
						  <option value="3">申请失败</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>身份号:</th>
					<td><input name="pid" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>手机号:</th>
					<td><input name="mobile" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
						<shiro:hasPermission name="/creditcardagency/analysisBankNotifyExcel">
							<a onclick="analysisBankNotify();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="cleanAgent();">解析银行文件</a>
						</shiro:hasPermission>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

