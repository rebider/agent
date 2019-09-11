<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    var addFileDom ;
    var fileName;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    $(function() {
        $('#importForm').form({
            url : '${path }/profit/other/importDeduction',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success == '1') {
                    parent.$.messager.alert('成功', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    });
    function removeAgentColinfoTable_model(t){
        $(t).parent().parent().parent().remove();
    }

    var addAgentColinfoTable_attrDom ;

    //上传窗口
    function addAgentColinfoTable_uploadView(t){
        addAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentColinfoTable_jxkxUploadFile);
    }

    //附件解析
    function addAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addAgentColinfoTable_attrDom).append("<span onclick='removeAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='text' hidden name='attachInfo' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeAgentColinfoTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
</script>
<div class="easyui-panel" data-options="iconCls:'fi-results'">
    <form id="importForm" method="post" enctype="multipart/form-data">
        <table class="grid">
            <tr>
                <td>上传附件：</td>
                <td class="attrInput">
                    <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                </td>
            </tr>

        </table>
    </form>
</div>

