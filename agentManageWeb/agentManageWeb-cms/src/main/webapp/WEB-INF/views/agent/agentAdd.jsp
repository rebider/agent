<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#agentAddForm').form({
            url : '${path }/agent/addAgent',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
                
            },
            success : function(result) {
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
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="agentAddForm" method="post">
            <table class="grid">
                <!-- <tr>
	                <td>代理商编号</td>
	                <td><input id="agentId" name="agentId" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                </tr> -->
                <tr>
                    <td>代理商状态</td>
                     <td>
		                <select class="easyui-combobox" name="agentStatus" style="width:160px;" data-options="required:true">
			                <option></option>
							<option value="A">申请</option>
							<option value="O">开通</option>
							<option value="W">暂停</option>
							<option value="T">终止</option>
							<option value="B">限制业务</option>
							<option value="S">限制交易</option>
						</select>
	                </td>
                    <td>代理商名称</td>
                    <td><input name="agentName" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>代理商推广码</td>
                    <td><input name="agentCode" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>公司简称</td>
                    <td><input name="agentCname" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>申请人</td>
                    <td><input name="appliant" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>申请时间</td>
                    <td><input name="time" type="text" class="easyui-datebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>注册手机</td>
                    <td><input name="salPhone" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>城市</td>
                    <td><input name="area" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>  
					 <td>地址</td>
                    <td><input name="address" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>邮编</td>
                    <td><input name="post" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>座机</td>
                    <td><input name="agentTel" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>传真</td>
                    <td><input name="fax" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>工商注册名称</td>
                    <td><input name="indregName" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>组织机构代码</td>
                    <td><input name="orgNum" type="text" placeholder="请输入"  style="width:160px;"></td>
                 </tr>
                <tr>
                    <td>营业执照号</td>
                    <td><input name="buslicNum" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>税务登记号</td>
                    <td><input name="taxregNum" type="text" placeholder="请输入"  style="width:160px;"></td>
                 </tr>
                <tr>
                    <td>注册资金</td>
                    <td><input name="regcapAmt" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>经营范围</td>
                    <td><input name="scobus" type="text" placeholder="请输入" style="width:160px;"></td>
                 </tr>
                <tr>
                    <td>开户名</td>
                    <td><input name="actName" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>开户行</td>
                    <td><input name="actBank" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>   
                    <td>开户账号</td>
                    <td><input name="actAccount" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                    <td>业务联系人</td>
                    <td><input name="conName" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>业务联系电话</td>
                    <td><input name="conTel" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>业务联系email</td>
                    <td><input name="email" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
                <tr>  
                    <td>代理商类型</td>
                    <td>
		                <select class="easyui-combobox" name="agentType" style="width:160px;">
			                <option></option>
							<option value="S">自营</option>
							<option value="J">加盟</option>
							<option value="C">资方</option>
						</select>
				    </td>
                    <td>其他资质</td>
                    <td><input name="othidtyp" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr> 
                <tr>  
                    <td>公司类型</td>
                    <td><input name="comType" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>所属操作员id</td>
                    <td><input name="userId" type="text" placeholder="请输入" style="width:160px;"></td>
               </tr>
                <tr>
                    <td>所属销售</td>
                    <td><input name="salesId" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>