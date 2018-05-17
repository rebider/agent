<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#accountingBookEditForm').form({
            url : '${path }/accountingBook/editAccountingBook',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#flag").val('${CAccountingBook.flag}');
        $("#pn").val('${CAccountingBook.pn}');
        $("#geneRate").val('${CAccountingBook.geneRate}');
        $("#feeType").val('${CAccountingBook.feeType}');
        $("#allocStatus").val('${CAccountingBook.allocStatus}');
        $("#transCode").val('${CAccountingBook.transCode}');
        $("#status").val('${CAccountingBook.status}');
        $("#fentryType").val('${CAccountingBook.fentryType}');
    });
</script>
<div style="padding: 3px;">
    <form id="accountingBookEditForm" method="post">
    <input type="hidden" name="financeId" value="${CAccountingBook.financeId}">
        <table class="grid">
               <tr>
                    <td>场景</td>
                    <td><input name="scene" value="${CAccountingBook.scene}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>业务内容</td>
                    <td><input name="busiContext" value="${CAccountingBook.busiContext}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>机构号</td>
                    <td><input name="forgNumber" value="${CAccountingBook.forgNumber}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>账套号</td>
                    <td><input name="facctBookId" value="${CAccountingBook.facctBookId}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>账套号描述</td>
                    <td><input name="facctBookInfo" value="${CAccountingBook.facctBookInfo}" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>摘要</td>
                    <td><input name="abstracts" value="${CAccountingBook.abstracts}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>借/贷</td>
                    <td>
		                <select class="easyui-combobox" name="flag" id="flag" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="B">借</option>
							<option value="C">贷</option>
						</select>
	                </td>
                    <td>科目编码</td>
                    <td><input name="faccountId" value="${CAccountingBook.faccountId}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>  
					<td>科目名称</td>
                    <td><input name="faccountName" value="${CAccountingBook.faccountName}" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>金额</td>
                    <td>
		                <select class="easyui-combobox" name="pn" id="pn" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="A">正数</option>
							<option value="N">负数</option>
						</select>
	                </td>
                </tr>
                <tr>
                    <td>辅助核算1</td>
                    <td><input name="fadjustitemId1" value="${CAccountingBook.fadjustitemId1}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>辅助核算2</td>
                    <td><input name="fadjustitemId2" value="${CAccountingBook.fadjustitemId2}" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>辅助核算3</td>
                    <td><input name="fadjustitemId3" value="${CAccountingBook.fadjustitemId3}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>辅助核算4</td>
                    <td><input name="fadjustitemId4" value="${CAccountingBook.fadjustitemId4}" type="text" placeholder="请输入"  style="width:160px;"></td>
                 </tr>
                <tr>
                    <td>是否生成凭证</td>
                    <td>
		                <select class="easyui-combobox" name="geneRate" id="geneRate" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
	                </td>
                    <td>费用类型</td>
                    <td>
		                <select class="easyui-combobox" name="feeType" id="feeType" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="O">上期未还清</option>
							<option value="Y">本金</option>
							<option value="V">利息</option>
							<option value="B">砍头息</option>
							<option value="E">服务费</option>
							<option value="P">逾期罚息</option>
							<option value="W">违约金</option>
							<option value="G">溢缴款</option>
							<option value="S">单边账</option>
							<option value="X">提前还款手续费</option>
							<option value="Z">逾期管理费</option>
						</select>
	                </td>
                 </tr>
                <tr>
                   <td>贷款状态</td>
                   <td>
		                <select class="easyui-combobox" name="allocStatus" id="allocStatus" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="S">正常分配</option>
							<option value="Y">逾期分配</option>
							<option value="L">临时分配</option>
						</select>
	                </td>
                    <td>交易码</td>
                   <td>
		                <select class="easyui-combobox" name="transCode" id="transCode" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="T001">(调账)合同撤销</option>
			                <option value="T002">(调账)合同生效</option>
			                <option value="T003">(调账)人工还款</option>
			                <option value="T004">利息冲销</option>
			                <option value="T005">服务费冲销</option>
			                <option value="T006">罚息冲销</option>
			                <option value="T007">逾期管理费冲销</option>
			                <option value="T008">未知费用冲销</option>
							<option value="A002">放款申请</option>
							<option value="B002">放款冲正</option>
							<option value="A003">还款</option>
							<option value="A004">溢缴款还款</option>
							<option value="A005">溢缴款转出</option>
							<option value="A011">提前还款</option>
							<option value="U001">利息调增</option>
							<option value="U002">罚息调增</option>
							<option value="U003">服务费调增</option>
							<option value="U004">提前手续费调增</option>
							<option value="U005">逾期管理费调增</option>
							<option value="DW01">未还利息减免</option>
							<option value="DY01">已还利息减免</option>
							<option value="DW02">未还罚息减免</option>
							<option value="DY02">已还罚息减免</option>
							<option value="DW03">未还服务费减免</option>
							<option value="DY03">已还服务费减免</option>
							<option value="DW04">未还提前手续费减免</option>
							<option value="DY04">已还提前手续费减免</option>
							<option value="DW05">未还逾期管理费减免</option>
							<option value="DY05">已还逾期管理费减免</option>
							<option value="C007">服务费计提</option>
							<option value="C008">提前手续费计提</option>
							<option value="C009">逾期管理费计提</option>
							<option value="C010">利息计提</option>
							<option value="C011">罚息计提</option> 
							<option value="C099">未知成分计提</option>  
							<option value="Z001">正常利息转逾期</option> 
							<option value="Z002">正常本金转逾期</option>  
						</select>
	                </td>
                 </tr>
                 <tr>
                   <td>启用状态</td>
                   <td>
		                <select class="easyui-combobox" name="status" id="status" style="width:160px;" >
			                <option></option>
							<option value="N">不启用</option>
							<option value="Y">启用</option>
						</select>
	                </td>
	                <td>分录类型</td>
                   <td>
		                <select class="easyui-combobox" name="fentryType" id="fentryType" style="width:160px;" >
			                <option></option>
							<option value="R">还款</option>
							<option value="P">放款</option>
							<option value="J">计提</option>
							<option value="T">调账</option>
							<option value="Z">转逾期</option>
						</select>
	                </td>
	              </tr>
	              <tr>
                   <td>分录状态</td>
                   <td><input name="fentryStatus" value="${CAccountingBook.fentryStatus}" type="text" placeholder="请输入"  style="width:160px;"></td>
                   <!-- <td>
		                <select class="easyui-combobox" name="fentryStatus" style="width:160px;" >
			                <option></option>
							<option value="N">不启用</option>
							<option value="Y">启用</option>
						</select>
	                </td> -->
	               </tr>
        </table>
    </form>
</div>
