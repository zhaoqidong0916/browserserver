
$(function () {
    if(!token){
        var pageUrl;
        var urlStr = window.location.href;
        if (urlStr.indexOf('localhost') >= 0 || urlStr.indexOf('127.0.0.1') >= 0) {
            var arr = urlStr.split('/');
            pageUrl = arr.slice(0, 3).join('/');
        } else {
            if (urlStr.indexOf('browser') >= 0) {
                pageUrl = window.location.origin + "/browser";
            } else {
                pageUrl = window.location.origin
            }
        }
        window.location.href = pageUrl;
    }
    $("#dropdownUser span").text(window.localStorage.getItem('userName'));
    $('#logout').on('click',function () {
        var logoutBox = bootbox.confirm({
            size: "small",
            message: "确认退出系统?",
            callback: function(result){
                if(result === true) {
                    $.ajax({
                        type: "get",
                        url: urls.logout,
                        dataType: "json",
                        beforeSend: function(request) {
                             request.setRequestHeader("Authorization", token);
                        },
                        contentType:'application/json',
                        success: function (data) {
                            if(data.result==200){
                                //退出时清空所有缓存数据
                                window.location.href=loginPage;
                            }if(data.result==300){
                                //退出时清空所有缓存数据
                                window.location.href=loginPage;
                            }else{
                                $(logoutBox).remove();
                                $('.modal-backdrop').remove();
                                bootbox.alert('访问异常！');
                                return false;
                            }
                        },
                        error: function(){
                            bootbox.alert('未登录，请先登录！');
                        }
                    })
                }else {
                    $(logoutBox).remove();
                    $('.modal-backdrop').remove();
                    return false;
                }
            }
        })
    });
    $('#rePwd').on('click',function () {
        $('#resetPwd').modal('show');
        resetForm($('#resetPwd'));
    })
    $('#resetPwd #pwdSaveBtn').on('click',function () {
        if($('#resetPwd').find('.rePwd').val() != $('#resetPwd').find('.newPwd').val()){
            bootbox.alert('两次密码输入不一致！');
            return false;
        }
        if($('#resetPwd').find('.newPwd').val()==""||$('#resetPwd').find('.oldPwd').val()==""||$('#resetPwd').find('.rePwd').val()==""){
            bootbox.alert('密码不能为空！');
            return false;
        }
        var data = {
            newPassword:hex_md5($('#resetPwd').find('.newPwd').val()),
            oldPassword:hex_md5($('#resetPwd').find('.oldPwd').val()),
            confirmNewPassword:hex_md5($('#resetPwd').find('.rePwd').val()),
            id:userId
        };
        data = JSON.stringify(data);
        $.ajax({
            type: "post",
            url: urls.resetPwd,
            data: data,
            dataType: "json",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                if(data.result==200){
                    bootbox.alert('修改成功！',function () {
                        $('#rePwd').modal('hide');
                        window.location.href=loginPage;
                    })
                }else if(data.result==300){
                    window.location.href=loginPage;
                }else{
                    bootbox.alert(data.message);
                    return false;
                }
            },
            error: function(){
                $('#rePwd').modal('hide');
                bootbox.alert('未登录，请先登录！');
                window.location.href=loginPage;
            }
        })
    });

    getLeftMenu();
    $('.menu').on('click','.level2 li',function () {
        $(this).find('a').addClass('currentEj').parent().siblings().find('a').removeClass('currentEj');
        $(this).find('a').addClass('currentEj').parent().parent().parent().siblings().find('a').removeClass('currentEj');
    });
    $(".amazonmenu").on("click",".level1>a",function(){
        if($(this).hasClass('home')){
            $(this).parent().siblings().find('ul').slideUp();
            $(this).addClass('currentEj').parent().siblings().find('a').removeClass('currentEj');
            window.location.hash = router.stringify('homePage');
            return false;
        }
        if($(this).find('i').hasClass('upArrow')){
            $('.menu .firstLevel i').removeClass('upArrow').addClass('downArrow');
        }else {
            $('.menu .firstLevel i').removeClass('upArrow').addClass('downArrow');
            $(this).find('i').removeClass('downArrow').addClass('upArrow');
        }
        $(this).parent().siblings().find('ul').slideUp();
        $(this).next().slideToggle();
        return false;
    });
    $(".menu").on('click','.level2 li',function(){
        var name=$(this).attr('data-src');
        window.location.hash = router.stringify(name);
    });

    $('#bodyCont').height($(window).height()-50-15);
    $('.treeLeft').height($(window).height()-50);
});