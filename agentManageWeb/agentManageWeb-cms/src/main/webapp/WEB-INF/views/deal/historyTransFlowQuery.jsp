<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
	
    $(function() {
    	 $("#transCode").val('${cTransFlow.transCode}');
    	 $("#transDirection").val('${cTransFlow.transDirection}');
    	 $("#transtype").val('${cTransFlow.transType}');
    	 $("#accountstatus").val('${cTransFlow.accountStatus}');
    	 $("#ratetype").val('${cTransFlow.rateType}');
    	 $("#operSource").val('${cTransFlow.operSource}');
    	 $("#correctTag").val('${cTransFlow.correctTag}');
    	 $("#showControl").val('${cTransFlow.showControl}');
    	 $("#paystatus").val('${cTransFlow.payStatus}');
    	 $("#payType").val('${cTransFlow.payType}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryHisTransFlowForm" method="post">
        <table class="grid">
         <!-- 1tr -->
        		<tr>
                <td colspan="10" >交易流水号</td> 
                <td><input name="flowId" type="text" value="${cTransFlow.flowId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                <td>	订单编号  </td>   
                <td><input name="orderId" type="text" value="${cTransFlow.orderId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                </tr>
                <tr>
                 <td colspan="10">借据编号 </td>
                <td><input name="loanId" type="text" value="${cTransFlow.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                <td>	合同编号</td>
                <td><input name="contractId" type="text" value="${cTransFlow.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                </tr>
        	  <tr>
        		  <td colspan="10">交易类型</td>    
                <td><select class="easyui-combobox"  id="transCode" name="transCode" style="width:150px;" data-options="required:true, disabled:true">
                <option></option>
                <option value="T001">(调账)合同撤销</option>
                <option value="T002">(调账)合同生效</option>
                <option value="T003">(调账)人工还款</option>
                <option value="T004">利息冲销</option>
                <option value="T005">服务费冲销</option>
                <option value="T006">罚息冲销</option>
                <option value="T007">逾期管理费冲销</option>
				<option value="A002">放款申请</option>
				<option value="B002">放款冲正</option>
				<option value="A003">还款</option>
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
				<option value="C009">提前手续费计提</option>
				<option value="C010">利息计提</option>
				<option value="C011">罚息计提</option>  
				<option value="Z001">正常利息转逾期</option> 
				<option value="Z002">正常本金转逾期</option>  
				</select></td>
				 <td>息费类型</td>
                <td><select class="easyui-combobox"  id="ratetype" name="ratetype" style="width:150px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="T">基准利率</option>
				<option value="D">复利利率</option>
				<option value="L">逾期利率</option>
				<option value="S">管理费</option>
				<option value="W">违约金</option>
				<option value="K">砍头息</option>
				<option value="F">逾期服务费率</option>
				</select></td>
				
          </tr>
         <tr>
         <td colspan="10">出款/还款交易方式</td>
               	<td><select class="easyui-combobox"  id="payType" name="payType" style="width:150px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="LJ">联机代付</option>
				<option value="PK">批量代付</option>
				<option value="XF">消费</option>
				<option value="DK">主动代扣</option>
				<option value="PJ">批扣</option>
				<option value="SK">刷卡</option>
				<option value="KZ">快捷支付</option>
				<option value="WX">微信</option>
				<option value="ZB">支付宝</option>
				<option value="XX">线下</option>
				<option value="XT">系统</option>
				<option value="TZ">调账</option>
				</select></td>
				<td>交易时间 </td>    
                <td><input name="strForTransDate" type="text" value="${cTransFlow.strForTransDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
         </tr>
            <tr>
            	<td colspan="10">借贷标记 </td>    
                <td><select class="easyui-combobox"  id="transDirection" name="transDirection" style="width:150px;" data-options="required:true, disabled:true">
				<option value="D">借记</option>
				<option value="C">贷记</option></select></td>
				 <td>交易金额 </td>    
                <td><input name="amount" type="text" value="${cTransFlow.amount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
            </tr>
             <tr>
                <td colspan="10">客户姓名</td> 
                <td><input name="custName" type="text" value="${cTransFlow.custName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                <td>客户手机号</td>
                <td><input name="mobileNo" type="text" value="${cTransFlow.mobileNo}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
            </tr>
            <tr>
             	<td colspan="10">操作人</td>
                <td><input name="operId" type="text" value="${cTransFlow.operId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                <td>交易卡号  </td>  
                <td><input name="account" type="text" value="${cTransFlow.account}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
            </tr>
            <tr>
                <td colspan="10">银行名称</td> 
                <td><input name="bankName" type="text" value="${cTransFlow.bankName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:150px;"></td>
                <td>交易状态 </td>
                <td><select class="easyui-combobox"  id="transtype" name="transtype" style="width:150px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="Q">申请</option>
				<option value="O">交易中</option>
				<option value="S">成功</option>
				<option value="E">失败</option>
				<option value="L">超时</option>
				<option value="T">已重提</option>
				<option value="F">不确定</option>
				<option value="D">冲正</option>
				<option value="H">挂起</option>
				<option value="G">有效</option>
				</select></td>
            </tr>
        </table>
    </form>
</div>
