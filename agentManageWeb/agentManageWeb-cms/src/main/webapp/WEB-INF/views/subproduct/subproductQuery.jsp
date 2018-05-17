<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#subproductEditPid').combotree({
            url : '${path }/product/tree?flag=false',
            parentField : 'parentProductId',
            lines : true,
            panelHeight : 'auto',
            value :'${sub.parentProductId}'
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="subQueryForm" method="post">
           <table class="grid">
                <tr>
	                <td style="width:150px">子产品名称</td>
	                <td><input name="subProductId" type="hidden"  value="${sub.subProductId}"><input name="productName" type="text" value="${sub.productName}"  class="easyui-textbox" style="width: 150px; height: 29px;" required="required" data-options="required:true, disabled:true"></td>
                </tr>
				<tr>
	                <td>期数</td>
	                <td><input name="period" value="${sub.period}"  class="easyui-numberspinner" style="width: 150px; height: 29px;" required="required" data-options="required:true, disabled:true"></td>
                </tr>
				<tr>
	                <td>每期数值</td>
	                <td><input name="periodUnit" value="${sub.periodUnit}"  class="easyui-numberspinner" style="width: 150px; height: 29px;" required="required" data-options="required:true, disabled:true"></td>
                </tr>
                <tr>
	                <td>名义日利率</td>
	                <td><input name="titularRate" value="${sub.titularRate}"  class="easyui-numberbox" style="width: 150px;" required="required" data-options="required:true, disabled:true"></td>
                </tr>
                 <tr>
	                <td>所属产品</td>
	                <td colspan="1"><select id="subproductEditPid" name="parentProductId" style="width:150px;height: 29px;" data-options="required:true, disabled:true"></select>
	                </td>
            	</tr>
            </table>
        </form>
    </div>
</div>