<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#activityVisibleForm').form({
            url: '${path}/activity/activityVisible',
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success: function (result) {
                result = $.parseJSON(result);
                if (result.status==200) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });

        $("input[name='visible']").click(function(){
            if($(this).val()==2){
                $(".queryClass").show();
                $(".agentClass").show();
            }else{
                $(".queryClass").hide();
                $(".agentClass").hide();
            }
        });

    });

    function visibleVerifyAgent(agUniqNum) {
        var agUniqNum = $("#agUniqNum").val();
        if(agUniqNum=='' || agUniqNum==undefined){
            info("唯一编号不能为空");
            return false;
        }
        $.ajax({
            url :"${path}/activity/verifyAgent",
            type:'POST',
            data:{
                agUniqNum:agUniqNum
            },
            dataType:'json',
            success:function(data){
                if(data.success){
                    var jsonObj = JSON.parse(data.msg);
                    var agentTableText = '<tr>';
                    agentTableText+='<td width="33%">'+jsonObj.id+'</td>';
                    agentTableText+='<input type="hidden" name="agentId" value="'+jsonObj.id+'">';
                    agentTableText+='<td width="33%">'+jsonObj.agName+'</td>';
                    agentTableText+='<td width="33%">';
                    agentTableText+='<a href="javascript:void(0);" onclick="deleteTableAgent(this)">删除</a>';
                    agentTableText+='</td>';
                    agentTableText+='</tr>';
                    $("#agentTable").append(agentTableText);
                }else{
                    info(data.msg);
                }
            },
            error:function(data){
                info("获取代理商失败，请联系管理员！");
            }
        });
    }

    function deleteTableAgent(o){
        $(o).parent().parent().remove();
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'center',border:false" style="padding: 3px;">
        <form id="activityVisibleForm" method="post">
            <table class="grid">
                <input type="hidden" name="activityId" value="${activity.actCode}">
                <tr>
                    <td>
                        <input type="radio" name="visible" value="1"  <c:if test="${activity.visible=='1'}">CHECKED</c:if>/>全部可见
                        <input type="radio" name="visible" value="2" style="margin-left: 20px" <c:if test="${activity.visible=='2'}">CHECKED</c:if>/>部分可见
                    </td>
                    <td colspan="2">
                        <a style="color: red">注:(可根据"代理商唯一编码"、"业务平台编号"、"代理商名称"查询)</a>
                    </td>
                </tr>
                <tr <c:if test="${activity.visible=='1' || activity.visible=='' || activity.visible==null}">style="display: none"</c:if> class="queryClass">
                    <td>唯一编码:
                    </td>
                    <td colspan="2">
                        <input id="agUniqNum" maxlength="80" type="text"  style="width:160px;margin-left: 10px">
                        <a href="javascript:void(0);"  onclick="visibleVerifyAgent();">查询</a>
                    </td>
                </tr>
                <tr <c:if test="${activity.visible=='1' || activity.visible=='' || activity.visible==null}">style="display: none"</c:if> class="queryClass">
                    <td style="text-align:left;" colspan="3">已选择代理商：</td>
                </tr>
                <tr <c:if test="${activity.visible=='1' || activity.visible=='' || activity.visible==null}">style="display: none"</c:if> class="queryClass">
                    <td width="33%">代理商编号</td>
                    <td width="33%">代理商名称</td>
                    <td width="33%">操作</td>
                </tr>
            </table>
            <table class="grid agentClass" id="agentTable" <c:if test="${activity.visible=='1' || activity.visible=='' || activity.visible==null}">style="display: none"</c:if>>
                <c:forEach items="${configuredList}" var="configured">
                    <tr>
                        <input type="hidden" name="agentId" value="${configured.AGENT_ID}">
                        <td width="33%">${configured.AGENT_ID}</td>
                        <td width="33%">${configured.AG_NAME}</td>
                        <td width="33%">
                            <a href="javascript:void(0);" onclick="deleteTableAgent(this)">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
</div>