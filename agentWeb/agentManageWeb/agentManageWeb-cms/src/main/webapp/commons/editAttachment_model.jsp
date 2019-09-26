<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="附件信息"  data-options="iconCls:'fi-results'" id="attachmentItemTableForm">
    <table class="grid" >
        <tr>
            <td>附件</td>
            <td class="attrInput" colspan="3">
                <c:if test="${!empty attachment}">
                    <c:forEach items="${attachment}" var="attachmentItem">
                        <span onclick='removeaddAttAgentcapitalTable_jxkxUploadFile(this)'>${attachmentItem.attName}<input type='hidden' name='attachmentItemTableFile' value='${attachmentItem.id}' /></span>
                    </c:forEach>
                </c:if>
            </td>
            <td colspan="4">
                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAttAgentcapitalTable_uploadView(this,ATT_DATA_TYPE_STATIC.YYZZ.key)" >营业执照（营业执照扫描件）</a>||
                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAttAgentcapitalTable_uploadView(this,ATT_DATA_TYPE_STATIC.SFZZM.key)" >法人身份证（法人身份证扫描件）</a>
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
    function get_addAttAgentcapitalTable_attrfiles(){
        var data = [];
        var files =  $("#attachmentItemTableForm").find(".attrInput").find("input[name='attachmentItemTableFile']");
        for(var j=0;j<files.length;j++){
            data.push($(files[j]).val());
        }
        return data;
    }
</script>

