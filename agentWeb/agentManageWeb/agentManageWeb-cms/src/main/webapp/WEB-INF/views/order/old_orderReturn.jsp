<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div>
    <table class="grid" style="width: 50%" >
        <tr>
            <td style="width: 150px;">上传sn整理文件:</td>
            <td>

                <form id="old_order_uploadForm" enctype="multipart/form-data" method="post">
                    <table style="vertical-align: middle;">
                        <tr>
                            <td>
                                <input type="file" id="file" name="file" style="width: 200px"/>
                                <input type="hidden" name="orderType" value="${orderType}"/>
                                <input type="hidden" name="rKey" value="${rKey}"/>
                                <a class="easyui-linkbutton" data-options="iconCls:'fi-upload'" onclick="javascript:$('#old_order_uploadForm').submit();">上传</a>
                                <%--&nbsp;&nbsp;&nbsp;&nbsp;<a href="/static/template/HistoryOrderReturn.xlsx">下载模板</a>--%>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </table>
</div>
<div id="old_order_return" class="easyui-panel" title="退货信息" style="background:#fafafa;" data-options="iconCls:'fi-results',tools:'#old_order_return_item'">
    <table class="grid" style="width: 100%" id="tbody_old_order_return_item">

    </table>
    <div class="easyui-panel" title="申请"  data-options="iconCls:'fi-wrench'" style="width:100%">
        <table class="grid" style="width: 100%;">
            <tbody>
                <tr>
                    <td>
                    <textarea rows="4" style="width: 100%" name="applyRemark" placeholder="申请备注" id="remark"></textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="old_order_return_item">
        <a href="javascript:void(0)" class="fi-plus" onclick="old_order_return_item_add()" title="添加记录"></a>
    </div>


    <table id="old_order_return_item_templet" style="display: none;">
        <tr>

            <td>起始SN:</td>
            <td><input name="snStart"/></td>
            <td>结束SN:</td>
            <td><input name="snEnd"/></td>
            <td>退货数量:</td>
            <td><input name="returnCount"/></td>
            <td>单价:</td>
            <td><input name="price"/></td>
            <td>小计:</td>
            <td><input name="amt"/></td>
            <td><input value="删除" type="button" onclick="javascript:$(this).parent().parent().remove();"/></td>
        </tr>
    </table>

</div>
<div style="padding-top: 20px;text-align: center">
    <a href="#" class="easyui-linkbutton" data-options="" style="width:80px;" onclick="tbody_old_order_return_item_submit()">提交申请</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" data-options="" style="width:80px" onclick="tbody_old_order_return_item_cancel()">取消申请</a>
</div>

<script type="text/javascript">
    $(function () {
        $('#old_order_uploadForm').form({
            url: '${path}/oldorderreturn/analysisFile',
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;

            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if(result.ok){
                   var html = [];
                   var tt = 0;
                   for(var i in result.data) {
                       html.push('<tr>');
                       html.push('<td>起始SN:</td>');
                       html.push('<td><input name="snStart" value="'+result.data[i].snStart+'"/></td>');
                       html.push('<td>结束SN:</td>');
                       html.push('<td><input name="snEnd" value="'+result.data[i].snEnd+'"/></td>');
                       html.push('<td>退货数量:</td>');
                       html.push('<td><input name="returnCount" value="'+result.data[i].count+'"/></td>');
                       html.push('<td>单价:</td>');
                       html.push('<td><input name="price" value="'+result.data[i].price+'"/></td>');
                       html.push('<td>小计:</td>');
                       html.push('<td><input name="amt" value="'+result.data[i].amt+'"/></td>');
                       html.push('<td><input value="删除" type="button" onclick="javascript:$(this).parent().parent().remove();"/></td>');
                       html.push('</tr>');
                       tt=result.data[i].amt+tt;
                   }
                   $('#tbody_old_order_return_item').empty();
                   $('#tbody_old_order_return_item').append(html.join(""));
                }else{
                    info(result.msg);
                }
            }
        });


        if('${rKey}'!=''){
            $('#old_order_uploadForm').submit();
        }

    });

    function tbody_old_order_return_item_submit(){
        var data = parseOldOrderReturnTable();
        $.ajaxL({
            type: "POST",
            url: basePath+"/oldorderreturn/createApply",
            dataType:'json',
            contentType:'application/json;charset=UTF-8',
            data: JSON.stringify({
                remark:$("#old_order_return textarea").text(),
                oldOrderReturnSubmitProVoList:data
            }),
            beforeSend : function() {
                progressLoad();
            },
            success: function(msg){
                info(msg.msg) ;
                if(msg.status==200){
                    $('#index_tabs').tabs('close', "申请-老订单");
                    searchRefund();
                }
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }
    function tbody_old_order_return_item_cancel(){
            $('#index_tabs').tabs('close', "历史退货");
    }
    function parseOldOrderReturnTable(){
        var data = [];
        $("#tbody_old_order_return_item tr").each(function(index,domEl){
            var returnCount =  $(domEl).find("input[name='returnCount']").val();
            var snStart =  $(domEl).find("input[name='snStart']").val();
            var snEnd =  $(domEl).find("input[name='snEnd']").val();
            var price =  $(domEl).find("input[name='price']").val();
            var amt =  $(domEl).find("input[name='amt']").val();
            data.push({
                returnCount:returnCount,
                snStart:snStart,
                snEnd:snEnd
            });
        });
        return data;
    }
    function old_order_return_item_add() {
        $('#tbody_old_order_return_item').append(
            $('#old_order_return_item_templet').html()
        );
    }




</script>