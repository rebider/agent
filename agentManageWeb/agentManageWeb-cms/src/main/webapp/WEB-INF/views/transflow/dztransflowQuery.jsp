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
                <td><input name="flowId" type="text" value="${cTransFlow.flowId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	订单编号  </td>   
                <td><input name="orderId" type="text" value="${cTransFlow.orderId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	借据编号 </td>
                <td><input name="loanId" type="text" value="${cTransFlow.loanId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>	合同编号</td>
                <td><input name="contractId" type="text" value="${cTransFlow.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
          <!-- 2tr -->       
              <tr> 
                <td colspan="10">交易卡号  </td>  
                <td><input name="account" type="text" value="${cTransFlow.account}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户号</td>       
                <td><input name="custId" type="text" value="${cTransFlow.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td> 
                <td>客户姓名</td> 
                <td><input name="custName" type="text" value="${cTransFlow.custName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				</select></td>
                <td>移动电话</td>
                <td><input name="mobileNo" type="text" value="${cTransFlow.mobileNo}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
            <!-- 3tr -->     
                <tr>
                <td colspan="10">借款金额</td>    
                <td><input name="payAmt" type="text" value="${cTransFlow.payAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>放款金额 </td>    
                <td><input name="amount" type="text" value="${cTransFlow.amount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				<td>银行名称</td> 
                <td><input name="bankName" type="text" value="${cTransFlow.bankName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>银行卡号</td>
                <td><input name="bankId" type="text" value="${cTransFlow.bankId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;">
                </td>
                </tr>
             <!-- 4tr -->    
                <tr>
                <td colspan="10">交易类型</td>    
                <td><select class="easyui-combobox"  id="transCode" name="transCode" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="A001">借款合同申请</option>
				<option value="B001">合同撤销</option>
				<option value="A002">放款申请</option>
				<option value="B002">放款冲正</option>
				<option value="A003">还款</option>
				<option value="A011">提前还款</option>
				<option value="B003">还款冲正</option>
				<option value="A004">本金调整</option>
				<option value="A005">利息调整</option>
				<option value="A006">砍头息调整</option>
				<option value="A007">管理费调整</option>
				<option value="A008">逾期利息调整</option>
				<option value="A009">违约金调整</option>
				<option value="B004">息费调整冲正</option>
				<option value="A010">溢交款转出</option>
				<option value="B010">溢交款转出冲正</option>
				<option value="C010">利息计提</option>
				</select></td>
                <td>借贷标记 </td>    
                <td><select class="easyui-combobox"  id="transDirection" name="transDirection" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">借记</option>
				<option value="C">贷记</option></select></td>
				<td>交易状态 </td>
                <td><select class="easyui-combobox"  id="transtype" name="transtype" style="width:120px;" data-options="required:true, disabled:true">
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
				<td><select class="easyui-combobox"  id="accountstatus" name="accountstatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">对账成功</option>
				<option value="E">对账失败</option>
				<option value="D">状态不一致</option>
				<option value="Q">回盘文件缺失</option>
				<option value="W">等待对账</option>
				</select></td>
				</select></td>
                </tr>
                
             <!-- 5tr -->     
                <tr>
                <td colspan="10">返回时间 </td>   
                <td><input name="retDate" type="text" value="${cTransFlow.retDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <!-- 新加数据库字段 -->
                <td>交易时间 </td>    
                <td><input name="transDate" type="text" value="${cTransFlow.transDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>计划编号 </td>
                <td><input name="planId" type="text" value="${cTransFlow.planId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>息费类型</td>
                <td><select class="easyui-combobox"  id="ratetype" name="ratetype" style="width:120px;" data-options="required:true, disabled:true">
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
                <td><input name="operDate" type="text" value="${cTransFlow.operDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>返回消息 </td>
                <td><input name="retText" type="text" value="${cTransFlow.retText}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>冲正时间</td>
                <td><input name="correctDate" type="text" value="${cTransFlow.correctDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
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
                <td><input name="correctTrans" type="text" value="${cTransFlow.correctTrans}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>返回码 </td>
                <td><input name="retCode" type="text" value="${cTransFlow.retCode}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
				</tr>   
				
				
				
				
			 <!-- 8tr --> 	  
                <tr>
                <td  colspan="10">出款/还款的通道标记</td>
                <td>   <input name="payChannel" type="text" value="${cTransFlow.payChannel}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款通道交易时间</td>
                <td><input name="payDate" type="text" value="${cTransFlow.payDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款平台流水号</td>
                <td><input name="payPlatformId" type="text" value="${cTransFlow.payPlatformId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款通道流水号</td>
                <td><input name="payChannelId" type="text" value="${cTransFlow.payChannelId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
                <tr>
                <td colspan="10">出款/还款流水状态</td>
                <td><select class="easyui-combobox"  id="paystatus" name="paystatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="G">未放款</option>
				<option value="H">部分放款</option>
				<option value="U">已放款</option>
				</select></td>
                <td>出款/还款返回内容</td>
                <td><input name="payText" type="text" value="${cTransFlow.payText}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出款/还款交易方式</td>
               	<td><select class="easyui-combobox"  id="payType" name="payType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">一次性放款</option>
				<option value="D">分期放款</option>
				</select></td>
				<td>出款/还款的平台标记</td>
                <td><input name="payPlatform" type="text" value="${cTransFlow.payPlatform}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                
				<tr>
                <td  colspan="10">验证码</td>
               	<td> <input name="valCode" type="text" value="${cTransFlow.valCode}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>操作人</td>
                <td><input name="operId" type="text" value="${cTransFlow.operId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
        </table>
    </form>
</div>
