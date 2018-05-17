<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

    	$('#productEditPid').combotree({
            url : '${path }/productrate/tree?flag=false',
            parentField : 'subProductId',
            lines : true,
            panelHeight : 'auto',
            value :'${rate.subProductId}'
        });
        
    
        $("#rateType").val('${rate.rateType}');
        $("#rateMode").val('${rate.rateMode}');
        $("#rateCaltype").val('${rate.rateCaltype}');
        $("#compound").val('${rate.compound}');
        $("#status").val('${rate.status}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="rateQueryForm" method="post">
            <table class="grid">
               <tr>
                <td>息费类型</td>
                <td>
                <input name="rateId" type="hidden"  value="${rate.rateId}">
                <select class="easyui-combobox" id="rateType" name="rateType" style="width:160px;" data-options="required:true, disabled:true">
					<option value="T">基准利率</option>
					<option value="D">复利利率</option>
					<option value="L">逾期利率</option>
					<option value="S">服务费</option>
					<option value="W">违约金</option>
					<option value="K">砍头息</option>
					<option value="F">逾期服务费率</option>
					<option value="X">提前还款手续费</option>
					<option value="Z">逾期管理费</option>
				</select>
				</td>
           	 </tr>
                <tr>
                    <td>收息方式</td>
	                <td>
	                <select class="easyui-combobox" id="rateMode" name="rateMode" style="width:160px;" data-options="required:true, disabled:true">
						<option value="R">提前还款</option>
						<option value="D">每期计息</option>
						<option value="Q">首期计息</option>
						<option value="L">逾期首期</option>
						<option value="Y">逾期每期</option>
						<option value="K">挪减放款金额</option>
						<option value="U">累加贷款本金</option>
					</select>
					</td>
	                <td>计算周期</td>
	                <td>
	                <select class="easyui-combobox" id="rateCaltype" name="rateCaltype" style="width:160px;" data-options="required:true, disabled:true">
						<option value="D">日</option>
						<option value="Q">周</option>
						<option value="L">月</option>
						<option value="J">季</option>
						<option value="N">年</option>
						<option value="S">期</option>
						<option value="M">一次性</option>
					</select>
					</td>
                </tr>
                <tr>
                    <td>费率</td>
                    <td><input type="text" name="rate" value="${rate.rate}" data-options="required:true, disabled:true" style="width:155px;" precision="6" class="easyui-numberbox"/></td>
                    <td>固定收取金额</td>
                    <td><input type="text" name="amt" value="${rate.amt}" data-options="required:true, disabled:true" style="width:155px;" precision="2" class="easyui-numberbox"/></td>
                </tr>
                <tr>
                    <td>是否计复利</td>
                    <td>
                       <select id="compound" name="compound" class="easyui-combobox" style="width:160px;" data-options="required:true, disabled:true">
                                <option value="S">是</option>
                                <option value="D">否</option>
                        </select>
                    </td>
                    <td>是否有效</td>
                    <td>
                        <select id="status" name="status" class="easyui-combobox" style="width:160px;" data-options="required:true, disabled:true">
                                <option value="H">是</option>
                                <option value="W">否</option>
                        </select>
                    </td>
                </tr>
                 <tr>
	                <td>所属子产品</td>
	                <td colspan="3"><select id="productEditPid" name="subProductId" data-options="required:true, disabled:true" style="width:580px;height: 29px;"></select>
	               </td>
            	</tr>
            </table>
        </form>
    </div>
</div>