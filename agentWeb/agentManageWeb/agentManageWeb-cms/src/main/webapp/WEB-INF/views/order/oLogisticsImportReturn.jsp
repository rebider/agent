<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function () {
        $('#logisticsImportFileForm').form({
            url: '${path}/logistics/importOLogistics',
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
                eval("var data = " + result + ";");
                //成功
                if(data.resCode=='1'){
                    if( data.obj != undefined && data.obj.length > 0 ){
                        var msg = [];
                        if( data.obj.length > 0 ){
                            for (var i=0; i<data.obj.length; i++){
                                for (var j=0; j<data.obj[i].length; j++){
                                    msg.push(data.obj[i][j]);
                                }
                            }
                        }
                        $('#oLogistics_return_notifyWin').html("");
                        $('#oLogistics_return_notifyWin').html(msg.join("</br></br>"));
                        $('#oLogistics_return_notifyWin').window('open');
                    }else {
                        info(data.resInfo);
                    }

                    parent.$.modalDialog.handler.dialog('close');
                    logisticsRefund_b.datagrid('reload');
                }else{
                    if( data.obj != undefined && data.obj.length > 0 ){
                      var msg = [];
                      if( data.obj.length > 0 ){
                         for (var i=0; i<data.obj.length; i++){
                             for (var j=0; j<data.obj[i].length; j++){
                                 msg.push(data.obj[i][j]);
                             }
                         }
                      }
                     $('#oLogistics_return_notifyWin').html("");
                     $('#oLogistics_return_notifyWin').html(msg.join("</br></br>"));
                     $('#oLogistics_return_notifyWin').window('open');
                    }else{
                        info(data.resInfo);
                    }
                }

            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="logisticsImportFileForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="requestType" value="orderReturn">
            <table class="grid" id="">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control"
                               style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>