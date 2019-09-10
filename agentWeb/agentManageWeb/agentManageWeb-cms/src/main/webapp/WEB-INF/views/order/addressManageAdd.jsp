<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#addressAddForm').form({
            url : '${path}/address/addressManageAdd',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.ok) {
                    if(result.flag=1){
                        refreshView();
                    }else {
                        parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                        parent.$.messager.alert('提示', result.msg, 'info');
                    }
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });

        //省市区初始化
        $("#addressAddForm_addrProvince").combobox({
            url:basePath+"/region/queryRegions?rCode=0",
            valueField: 'rCode',
            textField: 'rName',
            onChange: function(param){
                regionChange(this,'#addressAddForm_addrCity');
            },
            onLoadSuccess:function(){
                var t=this;
                var addrv = $(t).attr("addrv");
                var val = $(t).combobox('getData');
                if(undefined!=addrv && addrv.length>0){
                    $.each(val,function(i,n){
                        if(addrv==n.rCode){
                            $(t).combobox("setValue",addrv);
                            return;
                        }
                    });
                }else if(val.length>0){
                    $(this).combobox("setValue",val[0].rCode);
                }
            }
        });
        $("#addressAddForm_addrCity").combobox({
            onChange: function(param){
                regionChange(this,'#addressAddForm_addrDistrict');
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="addressAddForm" method="post">
            <input type="hidden" name="id" value="${address.id}" />
            <table class="grid">
                <tr>
                    <td>姓名：</td>
                    <td>
                        <input name="flag" type="hidden" value="${flag}">
                        <input name="addrRealname" maxlength="15" type="text" placeholder="请输入姓名" class="easyui-validatebox"  style="width:160px;" value="${address.addrRealname}" data-options="required:true">
                        <span style="color: red;">*</span>
                    </td>
                    <td>联系电话：</td>
                    <td colspan="3">
                        <input name="addrMobile" maxlength="15" type="text" placeholder="请输入联系电话" class="easyui-validatebox"  style="width:160px;" value="${address.addrMobile}" data-options="required:true,validType:['length[7,12]','Mobile']">
                        <span style="color: red;">*</span>
                    </td>
                </tr>
                <tr>
                    <td>省：</td>
                    <td>
                        <select class="easyui-combobox"  name="addrProvince"   style="width:160px;" id="addressAddForm_addrProvince"  addrv="${address.addrProvince}" data-options="required:true">
                        </select>
                        <span style="color: red;">*</span>
                    </td>
                    <td>市：</td>
                    <td>
                        <select class="easyui-combobox"  name="addrCity"   style="width:160px;" id="addressAddForm_addrCity"  addrv="${address.addrCity}" data-options="required:true">
                        </select>
                        <span style="color: red;">*</span>
                    </td>
                    <td>区：</td>
                    <td>
                        <select class="easyui-combobox"  name="addrDistrict"   style="width:160px;" id="addressAddForm_addrDistrict" addrv="${address.addrDistrict}" >
                        </select>
                        <span style="color: red;">*</span>
                    </td>
                </tr>
                <tr>
                    <td>详细地址：</td>
                    <td colspan="5">
                        <input name="addrDetail"  type="text" placeholder="请输入详细地址" class="easyui-validatebox"  style="width:98%;" value="${address.addrDetail}" data-options="required:true">
                        <span style="color: red;">*</span>
                    </td>

                </tr>
                <tr>
                    <td>邮编：</td>
                    <td><input name="zipCode"  type="text" placeholder="请输入" class="easyui-validatebox"  style="width:160px;"  value="${address.zipCode}" data-options="required:true"><span style="color: red;">*</span></td>
                    <td>备注：</td>
                    <td><input name="remark"  type="text" placeholder="请输入" class="easyui-validatebox"  style="width:160px;" value="${address.remark}" ><span style="color: red;">
                    <td>默认地址：</td>
                    <td>
                        <select class="easyui-combobox"  editable="false" name="isdefault" style="width:160px;" >
                            <option value="1"  <c:if test="${address.isdefault eq 1}">selected="selected"</c:if>  >是</option>
                            <option value="0"  <c:if test="${address.isdefault eq 0}">selected="selected"</c:if>>否</option>
                        </select>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</div>