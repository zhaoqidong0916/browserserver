$(function () {
    var urlStr = window.location.href;
    if (urlStr.indexOf('localhost') >= 0 || urlStr.indexOf('127.0.0.1') >= 0) {
        var arr = urlStr.split('/');
        var pageUrl = arr.slice(0, arr.length - 1).join('/');
    } else {
        if (urlStr.indexOf('browser') >= 0) {
            var pageUrl = window.location.origin + "/browser";
        } else {
            var pageUrl = window.location.origin
        }
    }
    // // 页面地址
    window.localStorage.setItem('pageUrl', pageUrl);

    //接口地址
    var $src = $("#interfaceUrl").val();

    $('.loginBox input').focus(function () {
        if ($(this).prev().attr('class').indexOf('-s') > 0) {
            var classArr = $(this).prev().attr('class').split(' ');
            var lastClass = classArr[classArr.length - 1];
            $(this).prev().removeClass(lastClass).addClass(lastClass.slice(0, -2));
        }
        // if ($(this).next().attr('class').indexOf('-s') > 0) {
        //     var classArr1 = $(this).next().attr('class').split(' ');
        //     var lastClass1 = classArr1[classArr1.length - 1];
        //     $(this).next().removeClass(lastClass1).addClass(lastClass1.slice(0, -2));
        // }
    })

    document.onkeydown = function () {
        //回车键的键值为 13
        if (event.keyCode == 13) {
            if ($('.bootbox-alert').css('display') == 'block') {
                return false;
            }
            login();
        }
    };

    /*登录*/
    $(".loginBtn").click(function () {
        login();
    });

    function login() {
        var data = {
            loginName: $(".js_userName").val(),
            password: hex_md5($(".js_passWord").val())
        };	
        data = JSON.stringify(data);
        $.ajax({
            url: $src + 'userLogin',
            type: "post",
            data: data,
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.result == "200") {
                    var data = data.data;
                    window.localStorage.setItem('url', $src);
                    window.localStorage.setItem('userId', data.id);
                    window.localStorage.setItem('userName', data.loginUser);
                    window.localStorage.setItem('token', data.token);
                    window.location.href = "views/index.html"
                } else {
                    bootbox.alert(data.message);
                }
            },
            error: function () {
                bootbox.alert('服务错误，请稍后再试！');
                return false;
            }
        })
    }
});
