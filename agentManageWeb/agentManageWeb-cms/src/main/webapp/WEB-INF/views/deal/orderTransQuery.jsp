<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
   	 $("#transCode").val('${cOrderTrans.transCode}');
	 $("#transDirection").val('${cOrderTrans.transDirection}');
	 $("#transType").val('${cOrderTrans.transType}');
	 $("#accountStatus").val('${cOrderTrans.accountStatus}');
	 $("#rateType").val('${cOrderTrans.rateType}');
	 $("#operSource").val('${cOrderTrans.operSource}');
	 $("#correctTag").val('${cOrderTrans.correctTag}');
	 $("#showControl").val('${cOrderTrans.showControl}');
	 $("#payStatus").val('${cOrderTrans.payStatus}');
	 $("#payType").val('${cOrderTrans.payType}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryHisTransFlowForm" method="post">
        <table class="grid">
         <!-- 1tr -->
        		<tr>
                <td colspan="10" >交易流水号</td> 
                <td><input name="flowId" type="text" value="${cOrderTrans.flowId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	订单编号  </td>   
                <td><input name="orderId" type="text" value="${cOrderTrans.orderId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
    
                <td>	借据编号 </td>
                <td><input name="loanId" type="text" value="${cOrderTrans.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	合同编号</td>
                <td><input name="contractId" type="text" value="${cOrderTrans.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
          <!-- 2tr -->       
              <tr> 
                <td colspan="10">交易卡号  </td>  
                <td><input name="account" type="text" value="${cOrderTrans.account}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户号</td>       
                <td><input name="custId" type="text" value="${cOrderTrans.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td> 
                <td>客户姓名</td> 
                <td><input name="custName" type="text" value="${cOrderTrans.custName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				</select></td>
                <td>移动电话</td>
                <td><input name="mobileNo" type="text" value="${cOrderTrans.mobileNo}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
            <!-- 3tr -->     
                <tr>
                <td colspan="10">借款金额</td>    
                <td><input name="payAmt" type="text" value="${cOrderTrans.payAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>交易金额 </td>
                <td><input name="amount" type="text" value="${cOrderTrans.amount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				<td>银行名称</td> 
                <td><input name="bankName" type="text" value="${cOrderTrans.bankName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>银行卡号</td>
                <td><input name="bankId" type="text" value="${cOrderTrans.bankId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;">
                </td>
                </tr>
             <!-- 4tr -->    
                <tr>
                <td colspan="10">交易类型</td>    
                <td><select class="easyui-combobox"  id="transCode" name="transCode" style="width:120px;" data-options="required:true, disabled:true">
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
                <td>借贷标记 </td>    
                <td><select class="easyui-combobox"  id="transDirection" name="transDirection" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">借记</option>
				<option value="C">贷记</option></select></td>
				<td>交易状态 </td>
                <td><select class="easyui-combobox"  id="transType" name="transType" style="width:120px;" data-options="required:true, disabled:true">
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
                <td>对账状态</td>
                <td><select class="easyui-combobox"  id="accountStatus" name="accountStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">对账成功</option>
				<option value="E">对账失败</option>
				<option value="D">状态不一致</option>
				<option value="Q">回盘文件缺失</option>
				<option value="W">等待对账</option>
                </select></td>
                </tr>
                
             <!-- 5tr -->     
                <tr>
                <td colspan="10">返回时间 </td>   
                <td><input name="retDate" type="text" value="${cOrderTrans.retDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <!-- 新加数据库字段 -->
                <td>交易时间 </td>    
                <td><input name="transDate" type="text" value="${cOrderTrans.transDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>计划编号 </td>
                <td><input name="planId" type="text" value="${cOrderTrans.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>息费类型</td>
                <td><select class="easyui-combobox"  id="rateType" name="rateType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="T">基准利率</option>
				<option value="D">复利利率</option>
				<option value="L">逾期利率</option>
				<option value="S">管理费</option>
				<option value="W">违约金</option>
				<option value="K">砍头息</option>
				<option value="F">逾期服务费率</option>
				<option value="X">提前还款手续费</option>
				<option value="Z">逾期管理费</option>
				</select></td>
                </tr>
             <!-- 6tr -->   
                <tr>
                <td colspan="10">操作来源</td>   
                <td><select class="easyui-combobox"  id="operSource" name="operSource" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="K">客户</option>
				<option value="L">内管</option>
				<option value="X">系统</option>
				</select></td>
                <td>操作时间  </td>   
                <td><input name="operDate" type="text" value="${cOrderTrans.operDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>返回消息 </td>
                <td><input name="retText" type="text" value="${cOrderTrans.retText}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>冲正时间</td>
                <td><input name="correctDate" type="text" value="${cOrderTrans.correctDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
           
            <!-- 7tr -->  
                 <tr>
                <td colspan="10">是否允许冲正</td>
                <td><select class="easyui-combobox"  id="correctTag" name="correctTag" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="Y">允许</option>
				<option value="N">不允许</option>
				</select></td>
                <td>客户端是否可见</td>
                <td><select class="easyui-combobox"  id="showControl" name="showControl" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="N">显示</option>
				<option value="F">不显示</option>
				</select></td>
				<td>冲正流水号</td>
                <td><input name="correctTrans" type="text" value="${cOrderTrans.correctTrans}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>返回码 </td>
                <td><input name="retCode" type="text" value="${cOrderTrans.retCode}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				</tr>   
				
				
				
				
			 <!-- 8tr --> 	  
                <tr>
                <td  colspan="10">出款/还款的通道标记</td>
                <td>   <input name="payChannel" type="text" value="${cOrderTrans.payChannel}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款通道交易时间</td>
                <td><input name="payDate" type="text" value="${cOrderTrans.payDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款平台流水号</td>
                <td><input name="payPlatformId" type="text" value="${cOrderTrans.payPlatformId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款通道流水号</td>
                <td><input name="payChannelId" type="text" value="${cOrderTrans.payChannelId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
                <tr>
                <td colspan="10">出款/还款流水状态</td>
                <td><input name="payStatus" type="text" value="${cOrderTrans.payStatus}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款返回内容</td>
                <td><input name="payText" type="text" value="${cOrderTrans.payText}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款交易方式</td>
               	<td><select class="easyui-combobox"  id="payType" name="payType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">一次性放款</option>
				<option value="D">分期放款</option>
				</select></td>
				<td>出款/还款的平台标记</td>
                <td><input name="payPlatform" type="text" value="${cOrderTrans.payPlatform}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
				<tr>
                <td  colspan="10">验证码</td>
               	<td> <input name="valCode" type="text" value="${cOrderTrans.valCode}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>操作人</td>
                <td><input name="operId" type="text" value="${cOrderTrans.operId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
        </table>
    </form>
</div>
