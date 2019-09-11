<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>--%>
<!--批量导入发票-->
<script type="text/javascript">

    //批量导入 提交
    $(function() {
        $('#volumeFileForm').form({
            url : '${path }/profit/invoiceDetail/uploadMultipartFile',
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success == '1') {
                    parent.$.messager.alert('成功', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    });

   /* function serachFileList() {
        var path = document.getElementById("mulFileInput").files;
        var str = "";
        var len = 0;
        for (var i = 0, len = path.length; i < len; i++) {
            console.log(path[i]);
            str += "<div>"+path[i].name+"</div>";
        };
        $("#filenameDisplay").html(str);
    }*/


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="volumeFileForm" method="post"  enctype="multipart/form-data">
            <div id="galleryImage-container">
                <table>
                    <tr>
                        <input id="mulFileInput" name="file" type="file" style="width: 220px;" multiple="true" required="required" <%--onchange="serachFileList()"--%>>
                    </tr>
                </table>
            </div>
        </form>
       <%-- <div id="filenameDisplay"></div>--%>
    </div>
</div>