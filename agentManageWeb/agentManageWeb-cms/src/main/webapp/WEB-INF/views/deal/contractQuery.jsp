<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	 $("#custPidType").val('${contract.custPidType}');
    	 $("#productType").val('${contract.productType}');
    	 $("#repayType").val('${contract.repayType}');
    	 $("#payType").val('${contract.payType}');
    	 $("#billDay").val('${contract.billDay}');
    	 $("#contractStatus").val('${contract.contractStatus}');
    	 $("#batchStatus").val('${contract.batchStatus}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryHisTransFlowForm" method="post">
        <table class="grid">
        		<tr>
                <td>合同号</td>
                <td><input name="contractId" type="text" value="${contract.contractId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>订单编号</td>
                <td><input name="orderId" type="text" value="${contract.orderId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>唯一客户号</td>
                <td><input name="custId" type="text" value="${contract.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户姓名</td>
                <td><input name="custName" type="text" value="${contract.custName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>客户证件类型</td>
                <td><select class="easyui-combobox"  id="custPidType" name="custPidType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="I">身份证</option>
				<option value="T">临时身份证</option>
				<option value="S">军官证/士兵证</option>
				<option value="P">护照</option>
				<option value="L">营业执照</option>
				<option value="O">其他有效证件</option>
				<option value="R">户口簿</option>
				<option value="H">港澳居民来往内地通行证</option>
				<option value="W">台湾同胞来往内地通行证</option>
				<option value="F">外国人居留证</option>
				<option value="Y">测试</option>
				<option value="C">警官证</option>
				<option value="Z">烟草专卖号</option>
				</select></td>
                <td>客户证件号</td>
                <td><input name="custPid" type="text" value="${contract.custPid}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>产品编号</td>
                <td><input name="subProductId" type="text" value="${contract.subProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>父产品编号</td>
                <td><input name="parentProductId" type="text" value="${contract.parentProductId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>产品类型</td>
                <td><select class="easyui-combobox"  id="productType" name="productType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">随借随还产品</option>
				<option value="F">分期产品</option>
				</select></td>
                <td>合同创建时间</td>
                <td><input name="createDate" type="text" value="${contract.createDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>合同签署时间</td>
                <td><input name="contractDate" type="text" value="${contract.contractDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>还款类型</td>
                <td><select class="easyui-combobox"  id="repayType" name="repayType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
                <option value="D">等额本金</option>
				<option value="L">等额本息</option>
				<option value="H">等本等息</option>
				<option value="S">测试</option>
				<option value="R">按日计息</option>
				</select></td>   
				</tr>
				
				<tr>   
                <td>放款类型</td>
                <td><select class="easyui-combobox"  id="payType" name="payType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">一次性放款</option>
				<option value="D">分期放款</option>
				</select></td>
                <td>贷款本金</td>
                <td><input name="amt" type="text" value="${contract.amt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>贷款期数</td>
                <td><input name="period" type="text" value="${contract.period}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>当前期数</td>
                <td><input name="currentPeriod" type="text" value="${contract.currentPeriod}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>每周的周期单位</td>
                <td><input name="periodUnit" type="text" value="${contract.periodUnit}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>约定账单日</td>
                 <td><input name="billDay" type="text" value="${contract.billDay}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
<!--                 <td><select class="easyui-combobox"  id="billDay" name="billDay" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">(20170601)</option>
				<option value="W">(1-7)</option>
				<option value="M">(1-28)</option>
				<option value="Q">(1-3)-(1-28)</option>
				<option value="Y">(1-12)-(1-28)</option>
				</select></td> -->
                <td>约定还款日</td>
                 <td><input name="repayDate" type="text" value="${contract.repayDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
<!--                 <td><select class="easyui-combobox"  id="repayDate" name="repayDate" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">(20170601)</option>
				<option value="W">(1-7)</option>
				<option value="M">(1-28)</option>
				<option value="Q">(1-3)-(1-28)</option>
				<option value="Y">(1-12)-(1-28)</option>
				</select></td>   -->             
                <td>下一账单日</td>
                <td><input name="nextBillDay" type="text" value="${contract.nextBillDay}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>开始计息日</td>
                <td><input name="interestDate" type="text" value="${contract.interestDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>代付卡号</td>
                <td><input name="payAccount" type="text" value="${contract.payAccount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>代扣卡号</td>
                <td><input name="repayAccount" type="text" value="${contract.repayAccount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>合同状态</td>
                <td><select class="easyui-combobox"  id="contractStatus" name="contractStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">申请</option>
				<option value="D">生效</option>
				<option value="E">无效</option>
				<option value="L">已结清</option>
				<option value="C">超时</option>
				<option value="O">正常</option>
				<option value="Y">关注</option>
				<option value="F">次级</option>
				<option value="K">可疑</option>
				<option value="M">损失</option>
				</select></td>
				</tr>
				
				<tr>
                <td>借款时间</td>
                <td><input name="loanDate" type="text" value="${contract.loanDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>放款时间</td>
                <td><input name="payDate" type="text" value="${contract.payDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>结清时间</td>
                <td><input name="settledDate" type="text" value="${contract.settledDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>合同生效时间</td>
                <td><input name="startDate" type="text" value="${contract.startDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>合同终止时间</td>
                <td><input name="endDate" type="text" value="${contract.endDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>提前终止时间</td>
                <td><input name="advanceDate" type="text" value="${contract.advanceDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>提前终止原因</td>
                <td><input name="advanceType" type="text" value="${contract.advanceType}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>审核人</td>
                <td><input name="operId" type="text" value="${contract.operId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
                
                <tr>
                <td>审核时间</td>
                <td><input name="operDate" type="text" value="${contract.operDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>最后更新时间</td>
                <td><input name="updateDate" type="text" value="${contract.updateDate}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户输入金额</td>
                <td><input name="inputAmt" type="text" value="${contract.inputAmt}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>批量状态锁定码</td>
                <td><select class="easyui-combobox"  id="batchStatus" name="batchStatus" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="C">正常计息锁定</option>
				<option value="D">正常计息锁定释放</option>
				<option value="E">逾期计息锁定</option>
				<option value="F">逾期计息锁定释放</option>
				</select></td>
                </tr> 
        </table>
    </form>
</div>
