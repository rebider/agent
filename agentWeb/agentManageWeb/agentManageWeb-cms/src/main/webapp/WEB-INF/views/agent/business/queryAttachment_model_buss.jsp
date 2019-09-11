<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="附件信息"  data-options="iconCls:'fi-results'" id="attachmentItemTableForm">
    <table class="grid" >
        <tr>
            <td>附件名称</td>
            <td  class="attrInput" colspan="3">
                <c:if test="${!empty attachment}">
                    <c:forEach items="${attachment}" var="attachmentItem">
            <td class="attrInput"><span>${attachmentItem.attName}<input type='hidden' name='attachmentItemTableFile' value='${attachmentItem.id}' /></span></td>
            <td><a href="<%=imgPath%>${attachmentItem.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
    </table>
</div>
<script type="application/javascript">
    var addAttAgentcapitalTable_attrDom;
    //上传窗口
    function addAttAgentcapitalTable_uploadView(t,attDataType){
        addAttAgentcapitalTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAttAgentcapitalTable_jxkxUploadFile,attDataType);
    }
    //附件解析
    function addAttAgentcapitalTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addAttAgentcapitalTable_attrDom).append("<span onclick='removeaddAttAgentcapitalTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='attachmentItemTableFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }

    function removeaddAttAgentcapitalTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
    //解析打个table
    function get_addAttAgentcapitalTable(){
        var data = [];
        var files =  $("#attachmentItemTableForm").find(".attrInput").find("input[name='attachmentItemTableFile']");
        for(var j=0;j<files.length;j++){
            data.push($(files[j]).val());
        }
        return data;
    }
</script>

