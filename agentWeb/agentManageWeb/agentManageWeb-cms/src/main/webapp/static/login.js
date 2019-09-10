// 判断时候在Iframe框架内,在则刷新父页面
if (self != top) {
    parent.location.reload(true);
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    } else {
        window.stop();
    }
}

$(function () {
    //验证密码（数字，字母，特殊字符）
    var pwdRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}$/;
    // 得到焦点
    $("#password").focus(function () {
        $("#left_hand").animate({
            left: "150",
            top: " -38"
        }, {
            step: function () {
                if (parseInt($("#left_hand").css("left")) > 140) {
                    $("#left_hand").attr("class", "left_hand");
                }
            }
        }, 2000);
        $("#right_hand").animate({
            right: "-64",
            top: "-38px"
        }, {
            step: function () {
                if (parseInt($("#right_hand").css("right")) > -70) {
                    $("#right_hand").attr("class", "right_hand");
                }
            }
        }, 2000);
    });
    // 密码失去焦点
    $("#password").blur(function () {
        $("#left_hand").attr("class", "initial_left_hand");
        $("#left_hand").attr("style", "left:100px;top:-12px;");
        $("#right_hand").attr("class", "initial_right_hand");
        $("#right_hand").attr("style", "right:-112px;top:-12px");
    });
    // 验证码
    $("#captcha").click(function() {
        var $this = $(this);
        var url = $this.data("src") + new Date().getTime();
        $this.attr("src", url);
    });
    // 登录
    $('#loginform').form({
        url: basePath + '/login',
        onSubmit : function() {
            progressLoad();
            var isValid = $(this).form('validate');
            if(!isValid){
                progressClose();
            }
            return isValid;
        },
        success:function(result){
            progressClose();
            result = $.parseJSON(result);
            var password = $("#password").val();
            password = atob(password.substring(4));
            if (result.success) {
                if (!pwdRegex.test(password)) {
                    $.messager.alert('密码强度较低，请修改密码！');
                    window.location.href = basePath + '/user/toEditPwd';
                    return;
                }
                window.location.href = basePath + '/index';
            }else{
                // 刷新验证码
                $("#captcha")[0].click();
                showMsg(result.msg);
            }
        }
    });
});
function submitForm(){
    //密码加密
    var password = $("#password").val();
    // 加密密码,赋值表单
    $("#password").val(""+getPass(4) + btoa(password));
    $('#loginform').submit();
}
function clearForm(){
    $('#loginform').form('clear');
}
//回车登录
function enterlogin(){
    if (event.keyCode == 13){
        event.returnValue=false;
        event.cancel = true;
        submitForm();
    }
}

function sendSms() {
    if($("input[name='username']").val()==''){
        alert("账号有误，请重填");
        return false;
    }
    var url = basePath+"/sendSms";
    var data = "username=" + $("input[name='username']").val();
    $.ajax({
        url: url,
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        dataType: "json",
        type: "post",
        data: data,
        success: function (data) {
            if (data.rspCode=='1') {
                alert("发送成功");
            } else{
                alert(data.rspMsg);
            }

        }
    });
}

//生成指定长度的随机大写字母
function getPass(len) {
    var tmpCh = "";
    for (var i = 0; i < len; i++) {
        tmpCh += String.fromCharCode(Math.floor(Math.random() * 26) + "a".charCodeAt(0));
    }
    return tmpCh.toUpperCase();
}

