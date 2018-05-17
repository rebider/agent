<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	 $("#custId").val('${cust.custId}');
    	 $("#custName").val('${cust.custName}');
    	 $("#custPidType").val('${cust.custPidType}');
    	 $("#custMobile").val('${cust.custMobile}');
    	 $("#industryType").val('${cust.industryType}');
    	 $("#monthlyIncome").val('${cust.monthlyIncome}');
    	 $("#maritalStatus").val('${cust.maritalStatus}');
    	 $("#eduStatus").val('${cust.eduStatus}');
    	 $("#createTime").val('${cust.createTime}');
    	 
    	 //数据库新加
    	 $("#custPid").val('${cust.custPid}');
    	 $("#sex").val('${cust.sex}');
    	 $("#custType").val('${cust.custType}');
    	 $("#companyId").val('${cust.companyId}');
    	 $("#updateTime").val('${cust.updateTime}');
    	 $("#age").val('${cust.age}');
    	 $("#contactId").val('${cust.contactId}');
    	 $("#personType").val('${cust.personType}');
    	 $("#roomType").val('${cust.roomType}');
    	 $("#payAccount").val('${cust.payAccount}');
    	 $("#birthday").val('${cust.birthday}');
    	 $("#custAttr").val('${cust.custAttr}');
    	 
    	 $("#custAuth").val('${cust.custAuth}');
    	 $("#certPhotoId").val('${cust.certPhotoId}');
    	 $("#faceId").val('${cust.faceId}');
    });
</script>
<div style="padding: 3px;">
    <form id="queryHisTransFlowForm" method="post">
        <table class="grid">
        	<!-- 1tr -->
        		<tr>
                <td>唯一客户号</td>
                <td><input name="custId" type="text" value="${cust.custId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户名</td>
                <td><input name="custName" type="text" value="${cust.custName}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>证件类型</td>
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
                <td>手机号</td>
                
                <td><input name="custMobile" type="text" value="${cust.custMobile}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr>
        <!-- 2tr -->  
             <tr> 
                <td>行业类型</td>
                <td><input name="industryType" type="text" value="${cust.industryType}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>月收入</td>
                <td><input name="monthlyIncome" type="text" value="${cust.monthlyIncome}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>婚姻状况</td>
                <td><input name="maritalStatus" type="text" value="${cust.maritalStatus}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>教育程度</td>
                 <td><input name="eduStatus" type="text" value="${cust.eduStatus}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                </tr> 
            <!-- 3tr -->    
            <tr>
               
                <td>绑定时间</td>
                <td><input name="createTime" type="text" value="${cust.createTime}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                
                <!-- 数据库新加页面 -->
                <td>证件号码</td>
                <td><input name="custPid" type="text" value="${cust.custPid}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>性别</td>
                <td><input name="sex" type="text" value="${cust.sex}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>客户类型</td>
                <td><select class="easyui-combobox"  id="custType" name="custType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="O">企业</option>
				<option value="Y">自然人</option>
				<option value="V">VIP</option>
				<option value="B">白名单</option>
				<option value="E">员工</option>
				<option value="W">白领</option>
				<option value="L">老板</option>
				<option value="L">普通用户</option>
				</select></td>
				 </tr>
				
			<!-- 4tr -->
			<tr>
                <td>公司编号</td>
                <td><input name="companyId" type="text" value="${cust.companyId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>修改时间</td>
                <td><input name="updateTime" type="text" value="${cust.updateTime}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>年龄</td>
                <td><input name="age" type="text" value="${cust.age}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>紧急联系人</td>
                <td><input name="contactId" type="text" value="${cust.contactId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                 </tr>
             <!-- 5tr -->
             <tr>
                <td>户口类型</td>
                <td><select class="easyui-combobox"  id="personType" name="personType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="L">本地</option>
				<option value="F">外地</option>
				</select></td>
                <td>住宅类型</td>
                <td><select class="easyui-combobox"  id="roomType" name="roomType" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="O">自有</option>
				<option value="R">租赁</option>
				</select></td>
				
                <td>贷款账户</td>
                <td><input name="payAccount" type="text" value="${cust.payAccount}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>出生日期</td>
                <td><input name="birthday" type="text" value="${cust.birthday}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                 </tr>
            <!-- 6tr -->  
            <tr>
                <td>客户属性</td>
                <td><input name="custAttr" type="text" value="${cust.custAttr}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>实名认证状态</td>
                <td><select class="easyui-combobox"  id="custAuth" name="custAuth" style="width:120px;" data-options="required:true, disabled:true">
                <option></option>
				<option value="Y">成功</option>
				<option value="N">失败</option>
				</select></td>
                <td>证件照编号</td>
                <td><input name="certPhotoId" type="text" value="${cust.certPhotoId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
                <td>人脸照编号</td>
                <td><input name="faceId" type="text" value="${cust.faceId}" class="easyui-textbox" data-options="required:true, disabled:true" style="width:120px;"></td>
             </tr> 
        </table>
    </form>
</div>
