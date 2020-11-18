$(function () {
    // var ctx = 'http://localhost:20000';
    var ctx = $("#interfaceUrl").val();
    var imgArr = [];
    var timer = null;
    var iconTimer = null;
    // $.ajax({
    //     type:'get',
    //     url:ctx+"/application/resourceList",
    //     dataType:'json',
    //     success:function(result){
    //         if(result.result == 200){
    //             var arr = result.data;
    //             $.each(arr,function () {
    //                 imgArr.push(this.downloadUrl);
    //             });
    //             if(timer){
    //                 clearInterval(timer);
    //             }
    //             $('body').css('background-image','url('+imgArr[0]+')');
    //             var k = 0;
    //             if(imgArr.length > 1) {
    //                 timer = setInterval(function () {
    //                     ++k;
    //                     if (k == imgArr.length) {
    //                         k = 0;
    //                     }
    //                     $('body').css('background-image', 'url(' + imgArr[k] + ')');
    //                 }, 5000);
    //             }
    //         }
    //     }
    // });
    $.ajax({
        type: 'get',
        url: ctx + "/application/urlList",
        dataType: 'json',
        success: function (result) {
            if (result.result == 200) {
                var arr = result.data;
                var str = '';
                $.each(arr, function (i, val) {
                    if ((i + 1) % 8 == 1) {
                        str += '<div class="group">';
                    }
                    str += '<div href="' + val.urlAddress + '" class="dl" title="' + val.urlName + '">' +
                        '        <div class="bgCommon sb dt" style="background-image: url(' + val.iconUrl + ')"></div>' +
                        '        <div class="dd">' + val.urlName + '</div>' +
                        '    </div>';
                    if ((i + 1) % 8 == 0 || i == arr.length - 1) {
                        str += '</div>';
                    }
                })
                $('.appStore').html(str);
                $('.appStore .group').hide();
                $('.appStore .group').eq(0).show();
                if (iconTimer) {
                    clearInterval(iconTimer);
                }
                var len = arr.length;
                //翻页
                var pointStr = '';
                for (var j = 0; j < len / 8; j++) {
                    if (j > 0) {
                        pointStr += '<li class="unselected"></li>';
                    } else {
                        pointStr += '<li></li>';
                    }
                }
                $('.pageBtn').html(pointStr);
                //轮播
                if (len > 8) {
                    var x = 0;
                    iconTimer = setInterval(function () {
                        x++;
                        if (x >= (len / 8)) {
                            x = 0;
                        }
                        $('.appStore .group').hide();
                        $('.appStore .group').eq(x).show();
                        $('.pageBtn li').addClass('unselected');
                        $('.pageBtn li').eq(x).removeClass('unselected');
                    }, 10000);
                }
            }
        }
    });

    $('.appStore').on('click', '.dl', function () {
        window.location.href = $(this).attr('href');
    })

    $('.pageBtn').on('click', 'li', function () {
        if (iconTimer) {
            clearInterval(iconTimer);
        }
        var index = $(this).index();
        $('.pageBtn li').addClass('unselected');
        $(this).removeClass('unselected');
        $('.appStore .group').hide();
        $('.appStore .group').eq(index).show();
        if ($('.pageBtn li').length > 1) {
            var y = index;
            iconTimer = setInterval(function () {
                y++;
                if (y >= ($('.pageBtn li').length)) {
                    y = 0;
                }
                $('.appStore .group').hide();
                $('.appStore .group').eq(y).show();
                $('.pageBtn li').addClass('unselected');
                $('.pageBtn li').eq(y).removeClass('unselected');
            }, 10000);
        }
    })

})