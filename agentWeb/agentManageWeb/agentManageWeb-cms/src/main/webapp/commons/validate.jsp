<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script>
    $.extend($.fn.validatebox.defaults.rules, {
//验证汉字
        CHS: {
            validator: function (value) {
                var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
                return reg.test(value);
            },
            message: '只允许输入中文,英文或者数字'
        },

        CP: {
            validator: function (value) {
                var reg = /([\u4E00-\u9FA5]|||\（|\）){1,}/;
                return reg.test(value);
            },
            message: '只允许输入中文'
        },
//手机号
        Mobile: {//value值为文本框中的值
            validator: function (value) {
              //  var reg = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
                var reg =/^[\d\-]+$/;
                return reg.test(value);
            },
            message: '请输入正确的手机号'
        },
//国内邮编
        ZipCode: {
            validator: function (value) {
                var reg = /^[0-9]\d{5}$/;
                return reg.test(value);
            },
            message: 'The zip code must be 6 digits and 0 began.'
        },
//数字
        Number: {
            validator: function (value) {
                var reg = /^[0-9]*$/;
                return reg.test(value);
            },
            message: '请输入数字,不能输入空格'
        },
//银行卡号
        BankCard: {
            validator: function (value) {
                var reg = /^([1-9]{1})(\d{14}|\d{18})$/;
                return reg.test(value);
            },
            message: '请输入正确的卡号'
        },
//邮箱
        Email: {
            validator: function (value) {
                var reg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
                return reg.test(value);
            },
            message: '请输入正确的邮箱'

        },
//金额
        Money: {
            validator: function (value) {
                var reg=/^([1-9][\d]{0,7}|0)(\.{0,1}[\d]{0,6})?$/;
                return reg.test(value);
            },
            message: '金额格式不正确'

        },

        //中文  英文  括号
            ChinaAndEng: {
            validator: function (value) {
                var reg = /^[A-Za-z\u4e00-\u9fa5]|\（|\）+$/;
                return reg.test(value);
            },
            message: '只允许输入中文,英文'
        },

        //身份证
        IdCard: {
            validator: function (value) {
                var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                return reg.test(value);
            },
            message: '请输入正确的证件号'
        },
    })

</script>