<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/02/20
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
    <form id="add_TerminalTransfer_model_form">
        <table class="grid">
            <tbody>
            <tr>
                <td width="150px">代理商基本信息</td>
                <td>
                    <div>
                        <hr>
                        <table class="grid">
                            <tr>
                                <td>代理商名称:</td><td>${agentRelate.agentName}</td>
                                <td>代理商唯一码:</td><td>${agentRelate.agentId}</td>
                                <td>业务类型:</td><td><input type="text" name="busPlatform" value="${agentRelate.busPlatform}"></td>
                            </tr>
                        </table>
                        <span style="color: red;font-size: 13px;">提示：关联的子公司顺序为扣款的依次顺序</span>
                        <table class="grid">
                            <c:forEach  items="${detailList}" var="detail">
                                <tr>
                                    <td>代理商名称:</td><td>${detail.agentName}</td>
                                    <td>代理商唯一码:</td><td>${detail.agentId}</td>
                                    <td>业务类型:</td><td><input type="text" name="busPlatform" value="${agentRelate.busPlatform}"></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    开始执行时间（分润归属月份）
                </td>
                <td>
                    <input type="text" value="${agentRelate.startMonth}">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<script>
    $(function() {
        console.log('0-0');
        var busPlatform=$("[name='busPlatform']:first").val();
        for(var i=0;i< db_options.ablePlatForm.length;i++){
            if (db_options.ablePlatForm[i].platformNum == busPlatform) {
                var temp = db_options.ablePlatForm[i].platformName;
                $("[name='busPlatform']").val(temp);
            }
        }
    });
</script>

