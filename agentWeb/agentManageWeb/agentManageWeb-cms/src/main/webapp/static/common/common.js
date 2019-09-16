$.ajaxSetup({
    statusCode:{
        404:function(){
            err("资源未找到");
        },
        401:function(){
            err("无权访问");
        }
    }
});
$.extend({
    ajaxL:function(opt){
        $.ajax({
            type: opt.type?opt.type:"POST",
            url: opt.url?opt.url:"/agentEnter/agentEnterIn",
            dataType:opt.dataType?opt.dataType:'json',
            async:(typeof opt.async == 'boolean'?opt.async:true),
            contentType:opt.contentType?opt.contentType:'application/x-www-form-urlencoded',
            data: opt.data?opt.data:{} ,
            beforeSend : opt.beforeSend ?opt.beforeSend :function(){},
            success: opt.success?opt.success:function(){},
            complete:opt.complete?opt.complete:function (XMLHttpRequest, textStatus){},
            statusCode:{
                404:function(){
                    err("资源未找到");
                },
                401:function(){
                    err("无权访问");
                }
            }

        });
    }
});


function info(msg){
    parent.$.messager.alert('提示',msg, 'info');
}

function err(msg){
    parent.$.messager.alert('错误', msg, 'error');
}
function isFloat(n){
    return /^\d+\.\d+$/.test(n);
}
//附件类型
var ATT_DATA_TYPE_STATIC = {
    "SFZZM":{key:"SFZZM",name:"身份证正面"},
    "YYZZ":{key:"YYZZ",name:"营业执照"},
    "YHKSMJ":{key:"YHKSMJ",name:"银行卡扫描件"},
    "KHXUZ":{key:"KHXUZ",name:"开户许可证"},
    "HTJCTZ":{key:"HTJCTZ",name:"合同解除通知"},
    "DKXX":{key:"DKXX",name:"打款信息"},
    "KPXX":{key:"KPXX",name:"开票信息"},
    "YBNSRZM":{key:"YBNSRZM",name:"一般纳税人证明"},
    "ZHBGB":{key:"ZHBGB",name:"账户变更表"},
    "JSRSFZ":{key:"JSRSFZ",name:"结算人身份证"},
    "FFRSQS":{key:"FFRSQS",name:"非法人授权书"}
};
