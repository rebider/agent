<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	/* productTree = $('#productTree').tree({
            url : '${path }/productrate/tree',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto'
        }); */
    	
    	$('#productrateAddPid').combotree({
            url : '${path }/productrate/tree',
            parentField : 'subProductId',
            lines : true,
            panelHeight : 'auto',
            value : <%=request.getParameter("id")%>
        });
    	
        $('#rateAddForm').form({
            url : '${path }/productrate/add',
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
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="rateAddForm" method="post">
            <table class="grid">
               <tr>
                <td>息费类型</td>
                <td>
                <select class="easyui-combobox" name="rateType" style="width:160px;" data-options="required:true">
	                <option></option>
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
	                <select class="easyui-combobox" name="rateMode" style="width:160px;" data-options="required:true">
		                <option></option>
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
	                <select class="easyui-combobox" name="rateCaltype" style="width:160px;" data-options="required:true">
		                <option></option>
						<option value="D">日</option>
						<option value="N">年</option>
						<option value="S">期</option>
						<option value="M">一次性</option>
					</select>
					</td>
                </tr>
                <tr>
                    <td>费率</td>
                    <td><input type="text" name="rate" style="width:155px;" precision="10" class="easyui-numberbox"/></td>
                    <td>固定收取金额</td>
                    <td><input type="text" name="amt" style="width:155px;" precision="2" class="easyui-numberbox"/></td>
                </tr>
                <tr>
                    <td>是否计复利</td>
                    <td>
                       <select name="compound" class="easyui-combobox" style="width:160px;" data-options="required:true">
                       			<option></option>
                                <option value="S">否</option>
                                <option value="D">是</option>
                        </select>
                    </td>
                    <td>是否有效</td>
                    <td>
                        <select name="status" class="easyui-combobox" style="width:160px;" data-options="required:true">
                        		<option></option>
                                <option value="H">是</option>
                                <option value="W">否</option>
                        </select>
                    </td>
                </tr>
                 <tr>
	                <td>所属子产品</td>
	                <td colspan="3"><select id="productrateAddPid" name="subProductId" style="width:580px;height: 29px;"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#productrateAddPid').combotree('clear');" >清空</a></td>
            	</tr>
            </table>
        </form>
    </div>
</div>