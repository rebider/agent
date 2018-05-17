<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#productagreeEditPid').combotree({
            url : '${path }/productrate/tree?flag=false',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value :'${agree.parentProductId}'
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="agreeQueryForm" method="post">
           <table class="grid">
                <tr>
                     <td>协议版本</td>
	                <td><input name="agreementId" type="hidden"  value="${agree.agreementId}">
	                <input name="version" value="${agree.version}" style="width: 300px;" class="easyui-textbox" data-options="required:true, disabled:true"></td>
					</tr><tr>
	                <td>模板地址</td>
	                <td><input name="tempUrl" value="${agree.tempUrl}" style="width: 300px;" class="easyui-textbox" data-options="required:true, disabled:true"></td>
                </tr>
                 <tr>
	                <td>所属产品</td>
	                <td colspan="3"><select id="productagreeEditPid" name="parentProductId" style="width:300px;height: 29px;" data-options="required:true, disabled:true"></select>
	               </td>
            	</tr>
            </table>
        </form>
    </div>
</div>