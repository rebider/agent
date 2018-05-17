<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var AccountingBookA;
    $(function() {
    	AccountingBookA = $('#AccountingBookA').datagrid({
            url : '${path }/accountingBook/accountingBookList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'financeId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '160',
                title : '主键',
                field : 'financeId',
            }, {
                width : '130',
                title : '场景',
                field : 'scene',
            } , {
                width : '150',
                title : '业务内容',
                field : 'busiContext',
            } , {
                width : '100',
                title : '机构号',
                field : 'forgNumber',
            } ,{
                width : '80',
                title : '账套号',
                field : 'facctBookId',
            } , {
                width : '160',
                title : '摘要',
                field : 'abstracts',
            } , {
                width : '190',
                title : '科目名称',
                field : 'faccountName',
            }, {
                width : '40',
                title : '金额',
                field : 'pn',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '正数';
                    case 'N':
                        return '负数';
                    }
                } 
            }, {
                width : '90',
                title : '是否生成凭证',
                field : 'geneRate',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Y':
                        return '是';
                    case 'N':
                        return '否';
                    }
                } 
            },{
                width : '90',
                title : '费用类型',
                field : 'feeType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '上期未还清';
                    case 'Y':
                        return '本金';
                    case 'V':
                        return '利息';
                    case 'B':
                        return '砍头息';
                    case 'E':
                        return '服务费';
                    case 'P':
                        return '逾期罚息';
                    case 'W':
                        return '违约金';
                    case 'G':
                        return '溢缴款';
                    case 'S':
                        return '单边账';
                    case 'X':
                        return '提前还款手续费';
                    case 'Z':
                        return '逾期管理费';
                    }
                } 
            }, {
                width : '90',
                title : '贷款状态',
                field : 'allocStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '正常分配';
                    case 'Y':
                        return '逾期分配';
                    case 'L':
                        return '临时分配';
                    }
                } 
            }, {
                width : '130',
                title : '交易码',
                field : 'transCode',
                formatter : function(value, row, index) {
                switch (value) {
                case 'T001':
                    return '(调账)合同撤销';
                case 'T002':
                    return '(调账)合同生效';
                case 'T003':
                    return '(调账)人工还款';
                case 'T004':
                    return '利息冲销';
                case 'T005':
                    return '服务费冲销';
                case 'T006':
                    return '罚息冲销';
                case 'T007':
                    return '逾期管理费冲销';
                case 'T008':
                    return '未知费用冲销';
                case 'A002':
                    return '放款申请';
                case 'B002':
                    return '放款冲正';
                case 'A003':
                    return '还款';
                case 'A004':
                    return '溢缴款还款';
                case 'A005':
                    return '溢缴款转出';
                case 'A011':
                    return '提前还款';
                case 'U001':
                    return '利息调增';
                case 'U002':
                    return '罚息调增';
                case 'U003':
                    return '服务费调增';
                case 'U004':
                    return '提前手续费调增';
                case 'U005':
                    return '逾期管理费调增';
                case 'DW01':
                    return '未还利息减免';
                case 'DY01':
                    return '已还利息减免';
                case 'DW02':
                    return '未还罚息减免';
                case 'DY02':
                    return '已还罚息减免';
                case 'DW03':
                    return '未还服务费减免';
                case 'DY03':
                    return '已还服务费减免';
                case 'DW04':
                    return '未还提前手续费减免';
                case 'DY04':
                    return '已还提前手续费减免';
                case 'DW05':
                    return '未还逾期管理费减免';
                case 'DY05':
                    return '已还逾期管理费减免';
                case 'C007':
                    return '服务费计提';    
                case 'C008':
                    return '提前手续费计提';
                case 'C009':
                    return '逾期管理费计提';
                case 'C010':
                    return '利息计提';
                case 'C011':
                    return '罚息计提';
                case 'C099':
                    return '未知成分计提';
                case 'Z001':
                    return '正常利息转逾期';
                case 'Z002':
                    return '正常本金转逾期';
                }
            } 
            },{
            	 field : 'action',
                 title : '操作',
                 width : 240,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/accountingBook/editAccountingBook">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editAccountingBookFun(\'{0}\');" >编辑</a>', row.financeId);
                         </shiro:hasPermission>

                         <shiro:hasPermission name="/accountingBook/deleteAccountingBook">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteAccountingBookFun(\'{0}\');" >删除</a>', row.financeId);
                         </shiro:hasPermission>
                        
                         <shiro:hasPermission name="/accountingBook/editAccountingBook">
                        	 str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryAccountingBookFun(\'{0}\');" >查看</a>', row.financeId);
                         </shiro:hasPermission> 
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.product-easyui-linkbutton-query').linkbutton({text:'查看'}); 
             },
            toolbar : '#accountingBookToolbar'
        });
    });

    function addAccountingBookFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/accountingBook/addAccountingBookPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = AccountingBookA;
                    var gr = parent.$.modalDialog.handler.find('#accountingBookAddForm');
                    gr.submit();
                }
            } ]
        });
    }
    
    function accountingBookAFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/finance/accountingPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = AccountingBookA;
                    var gr = parent.$.modalDialog.handler.find('#accountingBookAForm');
                    gr.submit();
                }
            } ]
        });
    }
    
  function deleteAccountingBookFun(financeId) {
        console.log(financeId);
       if (financeId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           financeId = rows[0].financeId;
       } else {//点击操作里面的删除图标会触发这个
    	   AccountingBookA.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该信息吗？', function(g) {
           if (g) {
               $.post('${path }/accountingBook/deleteAccountingBook', {
            	   financeId : financeId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       AccountingBookA.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
      function editAccountingBookFun(financeId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 800,
               height : 420,
               href : '${path }/accountingBook/editAccountingBookPage?financeId=' + financeId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = AccountingBookA;
                       var p = parent.$.modalDialog.handler.find('#accountingBookEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryAccountingBookFun(financeId) {
           parent.$.modalDialog({
               title : '查看',
               width : 800,
               height : 420,
               href : '${path }/accountingBook/queryAccountingBookPage?financeId=' + financeId,
           });
   }
   
   /*	function searchAgent() {
		AgentA.datagrid('load', $.serializeObject($('#searchAgentForm')));
	}
	function cleanAgent() {
		$('#searchAgentForm input').val('');
		$("[name='agentStatus']").val('');
		AgentA.datagrid('load', {});
	} */
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="AccountingBookA" data-options="fit:true,border:false"></table>
    </div>
   <!--  <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAgentForm">
			<table>
				<tr>
					<th>代理商编号:</th>
					<td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<th>代理商状态:</th>
					<td>
						<select name="agentStatus" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="A">申请</option>
						  <option value="O">开通</option>
						  <option value="W">暂停</option>
						  <option value="T">终止</option>
						  <option value="B">限制业务</option>
						  <option value="S">限制交易</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>申请人:</th>
					<td><input name="appliant" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<th>注册手机:</th>
					<td><input name="salPhone" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div> -->
</div>
<div id="accountingBookToolbar" style="display: none;">
    <shiro:hasPermission name="/accountingBook/addAccountingBook">
        <a onclick="addAccountingBookFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission> 
    <shiro:hasPermission name="/accountingBook/addAccountingBook">
        <a onclick="accountingBookAFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">金蝶数据上报</a>
    </shiro:hasPermission> 
</div>

