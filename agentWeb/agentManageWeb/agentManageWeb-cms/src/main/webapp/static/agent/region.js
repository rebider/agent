/**
 * isCheckbox 是否多选 true false
 * eg: /region/toRegionPage?isCheckbox=true
 * 回调函数
 */
function showRegionFrame(options,url,isCheckbox) {
    if(options.pid && options.pid !=undefined){
        url= url+"?pCode="+options.pid;
    }
    parent.$.modalDialog({
        title : '    ',
        width : 400,
        height : 500,
        href : "region/toRegionPage?url="+url+"&isCheckbox="+isCheckbox,
        buttons : [ {
            text : '确定',
            handler : function() {
                var nodeList = parent.nodeList;
                if(options.callBack)
                options.callBack(nodeList,options);
                parent.$.modalDialog.handler.dialog('close');
                if (nodeList.text!=null){
                    $.extend($.fn.validatebox.methods, {
                        remove: function(jq, newposition){
                            return jq.each(function(){
                                $(this).removeClass("validatebox-text");
                                $(this).removeClass("validatebox-invalid");
                            });
                        },
                    });
                    $('#busRegion').validatebox('remove');
                    $('#bankRegion').validatebox('remove');
                }
                parent.nodeList = {};

            }
        } ]
    });
}

/**
 * 业务平台 业务区域树
 * @param options
 * @param url
 * @param isCheckbox
 */
function showBusRegionFrame(options) {
    var but =options.but?[ {
        text : '确定',
        handler : function() {
            // var nodeList = parent.nodeList;
            var nodeList = parent.busRegionTree.tree('getChecked');
            if(options.callBack)
                options.callBack(nodeList,options);
            if(nodeList.length>0){
                $(options.target).parent().find("#busRegion").removeClass("validatebox-text");
                $(options.target).parent().find("#busRegion").removeClass("validatebox-invalid");
                $(options.target).parent().find("#busRegion1").removeClass("validatebox-text");
                $(options.target).parent().find("#busRegion1").removeClass("validatebox-invalid");
                $(options.target).parent().find("#busRegion3").removeClass("validatebox-text");
                $(options.target).parent().find("#busRegion3").removeClass("validatebox-invalid");
            }
            parent.$.modalDialog.handler.dialog('close');
            parent.nodeList = {};
        }
    }]:[];
    parent.$.modalDialog({
        title : '业务区域',
        width : 400,
        height : 600,
        href : "/region/toBusRegionList?bufdata="+options.bufData,
        buttons : but
    });
}

// 业务结构使用
function showSynRegionFrame(options,url,isCheckbox) {
    parent.$.modalDialog({
        title : '    ',
        width : 400,
        height : 500,
        href : "region/toSynRegionPage?url="+url+"&isCheckbox="+isCheckbox,
        buttons : [ {
            text : '确定',
            handler : function() {
                var nodeList = parent.nodeList;
                if(options.callBack)
                    options.callBack(nodeList,options);
                parent.$.modalDialog.handler.dialog('close');
                parent.nodeList = {};

            }
        } ]
    });
}


/**
 * 地区变更
 * @param t
 * @param c
 */
function regionChange(t,c){
    var v = $(t).combobox("getValue");
    $(c).combobox({
        valueField: 'rCode',
        textField: 'rName',
    });
    $.ajaxL({
        type:"GET",
        async:false,
        url:basePath+"/region/queryRegions",
        dataType:'json',
        data:{rCode:v},
        success:function(data){
            $(c).combobox("loadData",data);
            var addrv = $(c).attr("addrv");
            if(undefined!=addrv && addrv.length>0){
                var flag = false;
                $.each(data,function(i,n){
                    if(addrv==n.rCode){
                        $(c).combobox("setValue",addrv);
                        flag = true;
                    }
                });
                if(flag)return;
            }
            if(data.length>0){
                $(c).combobox("setValue",data[0].rCode);
            }
        },
        complete:function (XMLHttpRequest, textStatus) {

        }
    });

}





