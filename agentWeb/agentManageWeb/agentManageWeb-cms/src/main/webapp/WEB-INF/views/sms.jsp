<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
</script>
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <table class="grid">
            <c:forEach items="${smsList}" var="sms"  >
                <tr>
                    <td>手机号：</td>
                    <td>
                       ${sms.key}
                    </td>
                    <td>验证码：</td>
                    <td colspan="3">
                       ${sms.value}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>