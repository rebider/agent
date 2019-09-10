var callBackCp = "";
function multFileUpload(callBack,attDataType) {
    callBackCp = callBack;
    parent.$.modalDialog({
        title : attDataType!=null && attDataType!=undefined ? ATT_DATA_TYPE_STATIC[attDataType].name:'多文件上传',
        width : 300,
        height : 110,
        href : (attDataType!=null && attDataType!=undefined) ?"/multiFile/toUploadPage?attDataType="+attDataType:"/multiFile/toUploadPage",
        buttons : [ {
            text : '确定',
            handler : function() {
                var f = parent.$.modalDialog.handler.find('#multiFileForm');
                f.submit();
            }
        } ]
    });
}
function stepping(data) {
    callBackCp(data);
}