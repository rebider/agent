<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	//alert(product.productLevel);
    	
        $('#productEditPid').combotree({
            url : '${path }/product/tree?flag=false',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value :'${product.parentProductId}'
        });
        $("#productLevel").val('${product.productLevel}');
        $("#productType").val('${product.productType}');
        $("#loanType").val('${product.loanType}');
        $("#repayType").val('${product.repayType}');
        $("#payType").val('${product.payType}');
        $("#periodType").val('${product.periodType}');
        $("#resetType").val('${product.resetType}');
        $("#status").val('${product.status}');
    });
</script>
<div style="padding: 3px;">
    <form id="productSearchForm" method="post">
        <table class="grid">
             <tr>
                <td>产品名称</td>
                <td><input name="parentProductId" type="hidden"  value="${product.parentProductId}"><input name="productName" type="text" value="${product.productName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:155px;"></td>
                <td>产品级别</td>
                <td>
                <select class="easyui-combobox" id="productLevel" name="productLevel" style="width:160px;" data-options="required:true, disabled:true" >
				<option value="Y">一级</option>
				</select>
				</td>
            </tr>
            <tr>
                <td>产品类型</td>
                <td>
                <select class="easyui-combobox" id="productType" name="productType" style="width:160px;" data-options="required:true, disabled:true" >
                <option></option>
				<option value="S">随借随还</option>
				<option value="F">分期</option>
				</select>
				</td>
				<td>借款类型</td>
                <td>
                <select class="easyui-combobox"  id="loanType" name="loanType" style="width:160px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">循环借</option>
				<option value="F">结清再借</option>
				</select>
				</td>
            </tr>
            <tr>
                <td>还款类型</td>
                <td>
                <select class="easyui-combobox" id="repayType" name="repayType" style="width:160px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="D">等额本金</option>
				<option value="L">等额本息</option>
				<option value="H">等本等息</option>
				<option value="R">按日计息</option>
				</select>
				</td>
				<td>放款类型</td>
                <td>
                <select class="easyui-combobox" id="payType" name="payType" style="width:160px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="S">一次性放款</option>
				<option value="D">分期放款</option>
				</select>
				</td>
            </tr>
            <tr>
                <td>期限类型</td>
                <td>
                <select class="easyui-combobox" id="periodType" name="periodType" style="width:160px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="R">日</option>
				<option value="Z">周</option>
				<option value="Y">月</option>
				<option value="J">季</option>
				<option value="N">年</option>
				</select>
				</td>
				<td>还款日计算</td>
                <td><input name="repayDayDiff" type="text" class="easyui-numberbox" data-options="required:true, disabled:true" style="width:160px;" value="${product.repayDayDiff}"></td>
            </tr>
             <tr>
            <td>终止账龄</td>
            <td><input name="terminationAging" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.terminationAging}" data-options="required:true, disabled:true"> </td>
            <td>宽限日</td>
            <td><input name="graceDate" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.graceDate}" data-options="required:true, disabled:true"></td>
            </tr>
            
             <tr>
            <td>最低还款额</td>
            <td><input name="munPayment" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.munPayment}" data-options="required:true, disabled:true"></td>
            <td>容差</td>
            <td><input name="tolerance" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.tolerance}" data-options="required:true, disabled:true"></td>
            </tr>
            
            <tr>
            <td>最高可购金额</td>
            <td><input name="maxAmt" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.maxAmt}" data-options="required:true, disabled:true"></td>
            <td>最低可购金额</td>
            <td><input name="minAmt" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.minAmt}" data-options="required:true, disabled:true"></td>
            </tr>
            
            <tr>
            <td>额度阈值</td>
            <td><input name="thresholdAmt" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.thresholdAmt}" data-options="required:true, disabled:true"></td>
            <td>风险等级</td>
            <td><input name="riskLevel" type="text"  class="easyui-numberbox" style="width:160px;" value="${product.riskLevel}" data-options="required:true, disabled:true"></td>
            </tr>
            
            <tr>
                <td>重算类型</td>
                <td>
                <select class="easyui-combobox" id="resetType" name="resetType" style="width:160px;" data-options="required:true, disabled:true">
                	<option></option>
					<option value="A">摊薄所有</option>
					<option value="F">先冲当期，摊薄剩余</option>
					<option value="N">不做重算</option>
				</select>
				</td>
				<td>是否启用</td>
                <td>
                <select class="easyui-combobox" id="status" name="status" style="width:160px;" data-options="required:true, disabled:true">
                	<option></option>
					<option value="Q">启用</option>
					<option value="N">不启用</option>
				</select>
				</td>
            </tr>
            
            <tr>
                <td>产品描述</td>
                <td colspan="3"><input name="productDesc" value="${product.productDesc}" data-options="required:true, disabled:true" class="easyui-textbox" style="width:560px;height:50px"></td>
           </tr>
        </table>
    </form>
</div>
