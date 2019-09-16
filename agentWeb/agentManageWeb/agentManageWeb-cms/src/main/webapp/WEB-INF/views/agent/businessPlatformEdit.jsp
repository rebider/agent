<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function businessPlatEdit() {
        var editAgentBusiTable =(typeof get_editAgentBusiTable_FormData=== "function")?get_editAgentBusiTable_FormData():[];
        parent.$.messager.confirm('询问', '确认要修改？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "${path}/business/editBusPlat",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        busInfoVoList:editAgentBusiTable
                    }),
                    success: function(result){
                        if (result==1) {
                            alertMsg("修改成功");
                            RefreshCloudHomePageTab();
                            $('#index_tabs').tabs('close',"修改业务");
                        } else {
                            if(result.msg!='' && result.msg!=undefined){
                                alertMsg(result.msg);
                            }else{
                                alertMsg("修改失败");
                            }
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }

</script>
<%@ include file="/commons/editAgentBusi_model.jsp" %>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="businessPlatEdit()">保存</a>
</div>

