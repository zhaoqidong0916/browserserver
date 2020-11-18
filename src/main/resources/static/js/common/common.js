var loginPage = window.localStorage.getItem('pageUrl');
var $url = window.localStorage.getItem('url');
var token = window.localStorage.getItem('token');
var userId = window.localStorage.getItem('userId');
var userName = window.localStorage.getItem('userName');

var urls = {
    staNum: $url + 'count/',
    homepageVisit: $url + 'browserUrlCount/',
    clientActive: $url + 'browser/',
    login: $url + 'userLogin',
    logout: $url + 'logout',
    userDelete: $url + 'authUser/delete/',
    userDetail: $url + 'authUser/detail/',
    userList: $url + 'authUser/list',
    userRoleList: $url + 'authUser/role/list',
    userRegister: $url + 'authUser/register',
    resetPwd: $url + 'authUser/reset/password',
    userUpdate: $url + 'authUser/update',
    menuTree: $url + 'system/left_data/',
    menuDelete: $url + 'system/resource/delete/',
    menuDetail: $url + 'system/resource/detail/',
    menuAdd: $url + 'system/resource/add',
    menuEnable: $url + 'system/resource/enable',
    menuList: $url + 'system/resource/list',
    menuSonList: $url + 'system/resource/sonList',
    menuUpdate: $url + 'system/resource/update',
    roleAdd: $url + 'system/role/add',
    roleDelete: $url + 'system/role/delete/',
    roleDetail: $url + 'system/role/detail/',
    roleEnable: $url + 'system/role/enable',
    roleList: $url + 'system/role/list',
    roleResourceAdd: $url + 'system/role/resourceAdd',
    roleResourceList: $url + 'system/role/resourceList/',
    roleUpdate: $url + 'system/role/update',
    ss: $url + 'system/ss',
    pluginUpdateList: $url + 'upgrade/plugin/list',
    pluginDelete: $url + 'upgrade/plugin/delete/',
    pluginAdd: $url + 'upgrade/plugin/add',
    pluginDetail: $url + 'upgrade/plugin/detail/',
    pluginUpdate: $url + 'upgrade/plugin/update',
    pluginAddUpdate: $url + 'upgrade/plugin/add/update',
    pluginUpload: $url + 'upgrade/plugin/upload',
    pluginTypeList: $url + 'upgrade/pluginType/list',
    browserUpdateList: $url + 'upgrade/browser/list',
    browserAdd: $url + 'upgrade/browser/add',
    browserDelete: $url + 'upgrade/browser/delete/',
    browserDetail: $url + 'upgrade/browser/detail/',
    browserHistory: $url + 'upgrade/browser/history/',
    browserReset: $url + 'upgrade/browser/reset',
    browserUpdate: $url + 'upgrade/browser/update',
    browserUpload: $url + 'upgrade/browser/upload',
    certUpdateList: $url + 'upgrade/cert/list',
    certAdd: $url + 'upgrade/cert/add',
    certDelete: $url + 'upgrade/cert/delete/',
    certDetail: $url + 'upgrade/cert/detail/',
    certUpdate: $url + 'upgrade/cert/update',
    certAddUpdate: $url + 'upgrade/cert/add/update',
    certUpload: $url + 'upgrade/cert/upload',
    certTypeList: $url + 'upgrade/certType/list',
    typeCertList: $url + 'type/cert/list',
    typeCertAdd: $url + 'type/cert/add',
    typeCertDelete: $url + 'type/cert/delete/',
    typeCertDetail: $url + 'type/cert/detail/',
    typeCertUpdate: $url + 'type/cert/update',
    typePluginList: $url + 'type/plugin/list',
    typePluginAdd: $url + 'type/plugin/add',
    typePluginDelete: $url + 'type/plugin/delete/',
    typePluginDetail: $url + 'type/plugin/detail/',
    typePluginUpdate: $url + 'type/plugin/update',
    safePluginAdd: $url + 'safe/plugin/add',
    safePluginDelete: $url + 'safe/plugin/delete/',
    safePluginDetail: $url + 'safe/plugin/detail/',
    safePluginList: $url + 'safe/plugin/list',
    safePluginUpdate: $url + 'safe/plugin/update',
    safePluginState: $url + 'safe/plugin/state/edit',
    safeVisitAdd: $url + 'safe/visit/add',
    safeVisitDelete: $url + 'safe/visit/delete/',
    safeVisitDetail: $url + 'safe/visit/detail/',
    safeVisitList: $url + 'safe/visit/list',
    safeVisitUpdate: $url + 'safe/visit/update',
    safeVisitState: $url + 'safe/visit/state/edit',
    compatibleUpdate: $url + 'config/compatible/update',
    compatibleAdd: $url + 'config/compatible/add',
    compatibleList: $url + 'config/compatible/list',
    compatibleDetail: $url + 'config/compatible/detail/',
    compatibleDelete: $url + 'config/compatible/delete/',
    watermarkDetail: $url + 'config/watermark/detail/',
    watermarkList: $url + 'config/watermark/list',
    watermarkUpdate: $url + 'config/watermark/update',
    watermarkUpload: $url + 'config/watermark/upload',
    generalDetail: $url + 'config/general/detail/',
    generalList: $url + 'config/general/list',
    generalUpdate: $url + 'config/general/update',
    browserLog: $url + 'log/browserLog',
    browserLog2: $url + 'log/browserLog2',
    loginLog: $url + 'log/loginLog',
    operateLog: $url + 'log/operateLog',
    applicationAdd: $url + 'application/url/add',
    applicationDelete: $url + 'application/url/delete/',
    applicationDetail: $url + 'application/url/detail/',
    applicationList: $url + 'application/url/list',
    applicationUpdate: $url + 'application/url/update',
    iconLoad: $url + 'application/icon/upload',
    iconList: $url + 'application/icon/list',
    iconDelete: $url + 'application/icon/delete/',
    resourceUpdate: $url + 'application/resource/update',
    resourceAdd: $url + 'application/resource/add',
    resourceApp: $url + 'application/resource/app',
    resourceDelete: $url + 'application/resource/delete/',
    resourceDetail: $url + 'application/resource/detail/',
    resourceList: $url + 'application/resource/list',
    resourceUpload: $url + 'application/resource/upload',
    concurrenceAmountList: $url + 'countRateLimiter/concurrenceAmount/list',
    concurrenceAmountDetail: $url + 'countRateLimiter/concurrenceAmount/detail',
    concurrenceAmountupdate: $url + 'countRateLimiter/concurrenceAmount/update',
    errorLog: $url + 'log/errorLog',
    errorJsLog: $url + 'log/browserJsErrorLogList',
    browserActionLog: $url + 'log/browserActionLog',
    browserActionLog2: $url + 'log/browserActionLog2'
}

function getLeftMenu() {
    $.ajax({
        type: "get",
        url: urls.menuTree + userId,
        dataType: "json",
        contentType: 'application/json',
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if (data.result == '200') {
                var urlInfo = {};
                var str = '';
                $('.menu').html('');
                var d = data.data;
                var s = 0;
                for (var i = 0; i < d.length; i++) {
                    if (d[i].iconType == 'icon1') {
                        d[i].iconType = 'userIcon';
                    } else if (d[i].iconType == 'icon3') {
                        d[i].iconType = 'logIcon';
                    } else if (d[i].iconType == 'icon4') {
                        d[i].iconType = 'applyIcon';
                    } else if (d[i].iconType == 'icon11') {
                        d[i].iconType = 'safeIcon';
                    } else if (d[i].iconType == 'icon7') {
                        d[i].iconType = 'tacticsIcon';
                    } else if (d[i].iconType == 'icon6') {
                        d[i].iconType = 'updateIcon';
                    } else if (d[i].iconType == 'icon0') {
                        d[i].iconType = 'homeIcon';
                    } else {
                        d[i].iconType = 'tacticsIcon';
                    }

                    if (d[i].iconType == 'homeIcon') {
                        str += '<li class="level1"><a class="firstLevel home currentEj pr" href="#none"><em class="pa iconCommon homeIcon"></em>首页</a></li>';
                        urlInfo.homePage = {
                            parentName: '首页',
                            urlName: '首页'
                        };
                    } else {
                        str += '<li class="level1">' +
                            '<a class="firstLevel pr" href="#none"><em class="pa iconCommon ' + d[i].iconType + '"></em>' + d[i].name + '<i class="downArrow"></i></a>';

                        if (d[i].children && d[i].children.length > 0) {
                            var child = d[i].children;
                            str += '<ul class="level2">';
                            for (var k = 0; k < d[i].children.length; k++) {
                                var htmlUrl = child[k].htmlUrl;
                                var result = 'give you some color see see';
                                if (htmlUrl.indexOf('/') >= 0) {
                                    result = htmlUrl.split('/')[1].split('.')[0]
                                }
                                urlInfo[result] = {
                                    parentName: child[k].parentName,
                                    urlName: child[k].urlName
                                };
                                s = s + 1;
                                str += '<li data-src="' + result + '"><a href="javascript:;" class="secondLevel">' + child[k].urlName + '</a></li>';
                            }
                            str += '</ul>';
                        }
                        str += '</li>';
                    }
                }
                $('.menu').html(str);
                if (d.length > 0) {
                    location.hash = d[0].children[0].htmlUrl.split('/')[1].split('.')[0];
                    window.localStorage.setItem('routerDefault', d[0].children[0].htmlUrl.split('/')[1].split('.')[0]);
                }
                window.localStorage.setItem('urlInfo', JSON.stringify(urlInfo));
                var hash = window.location.hash.slice(1);
                $('.menu .level2 li').each(function () {
                    if ($(this).attr('data-src') == hash) {
                        $(this).parents('.level2').show();
                        $(this).parents('.level1').find('.firstLevel i').removeClass('downArrow').addClass('upArrow');
                        $(this).find('a').addClass('currentEj');
                    }
                })
            } else if (data.result == 300) {
                window.location.href = loginPage;
            }
        },
        error: function (xhr) {
        }
    });
}


function ajaxResponse(data, grid, c, callback) {
    if (data.result == 200) {
        bootbox.alert('操作成功!', function () {
            if (grid) {
                grid.bootstrapTable('refresh');
            }
            if (callback) {
                callback();
            }
        });
    } else if (data.result == 300) {
        bootbox.alert('未登录!', function () {
            window.top.location.href = loginPage;
        });
    } else {
        bootbox.alert(data.message);
    }
    if (c) {
        c.modal('hide');
    }

}

function resetForm(c) {
    c.find('input[type=text]').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('input[type=url]').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('input[type=phone]').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('input[type=email]').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('input[type=password]').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('select').each(function () {
        $(this).val($(this).find('option').eq(0).val()).attr('disabled', false).css('background-color', '#fff');
    });
    c.find('textarea').each(function () {
        $(this).val('').attr('disabled', false);
    });
    c.find('input[type=radio]').each(function () {
        $(this).removeAttr('checked').attr('disabled', false);
    });
    c.find('input[type=radio]').eq(0).prop("checked", true);
    c.find('label.error').hide();
}

//表格提示信息
function paramsMatter(value, row, index) {
    if (!value) {
        return '-';
    }
    return '<div class="textEllipse" title="' + value + '">' + value + '</div>';
}

function resetSearchItems() {
    $('.searchItems input').val('');
    $('.searchItems select').each(function () {
        $(this).val($(this).find('option').eq(0).val());
    })
}

function paintContent(c, type, data, title) {
    resetForm(c);
    c.modal('show');
    c.find('.modal-title').text(title);
    if (type == 'scan') {
        c.find('.submitUserForm').hide();
    } else {
        c.find('.submitUserForm').show();
    }
    for (var key in data) {
        if (c.find('input[name=' + key + ']').length > 0 && key != 'sex') {
            c.find('input[name=' + key + ']').val(data[key]).attr('disabled', type == 'scan' ? true : false);
        }
        if (c.find('select[name=' + key + ']').length > 0) {
            c.find('select[name=' + key + ']').val(data[key]).attr('disabled', type == 'scan' ? true : false);
        }
        if (c.find('textarea[name=' + key + ']').length > 0) {
            c.find('textarea[name=' + key + ']').val(data[key]).attr('disabled', type == 'scan' ? true : false);
        }
    }
}

function getRequestHeader(c) {
    var obj = {};
    c.find('input[type=text]').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('input[type=url]').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('input[type=email]').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('input[type=phone]').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('input[type=password]').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('select').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    c.find('textarea').each(function () {
        if ($(this).val()) {
            obj[$(this).attr('name')] = $(this).val();
        }
    })
    return obj;
}

//启用禁用
function enableFun(url, row, grid, tips, callback) {
    var ableWin = bootbox.confirm({
        message: row.checkStatus == 0 ? '确认禁用该' + tips + '?' : '确认启用该' + tips + '?',
        buttons: {
            confirm: {
                label: '<i class="icon-ok"></i>确定',
                className: 'btn-info'
            },
            cancel: {
                label: '<i class=" icon-remove"></i>取消',
                className: 'btn-default'
            }
        },
        callback: function (msg) {
            if (msg) {
                $.ajax({
                    url: url,
                    method: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: JSON.stringify({id: row.id, checkStatus: row.checkStatus == 0 ? 1 : 0}),
                    dataType: "json",
                    success: function (data) {
                        if (data.result == 200) {
                            bootbox.alert('操作成功!', function () {
                                grid.bootstrapTable('refresh');
                                if (callback) {
                                    callback();
                                }
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!', function () {
                                window.top.location.href = loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                        ableWin.modal('hide');
                    },
                    error: function () {
                        ableWin.modal('hide');
                    }
                })
            }
        }
    })
}

//删除方法
function delOptFun(url, grid, callback) {
    var delWin = bootbox.confirm({
        message: '确定删除选中数据？',
        buttons: {
            confirm: {
                label: '<i class="icon-ok"></i>确定',
                className: 'btn-info'
            },
            cancel: {
                label: '<i class=" icon-remove"></i>取消',
                className: 'btn-default'
            }
        },
        callback: function (msg) {
            if (msg) {
                $.ajax({
                    url: url,
                    method: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.result == 200) {
                            bootbox.alert('操作成功!', function () {
                                if (grid) {
                                    grid.bootstrapTable('refresh');
                                }
                                if (callback) {
                                    callback();
                                }
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!', function () {
                                window.top.location.href = loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                        delWin.modal('hide');
                    },
                    error: function () {
                        delWin.modal('hide');
                    }
                })
            }
        }
    })
}

//详情方法
function detailOptFun(url, callback) {
    $.ajax({
        url: url,
        type: "get",
        contentType: 'application/json',
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        dataType: "json",
        success: function (data) {
            if (data.result == 200) {
                callback(data.data);
            } else if (data.result == 300) {
                bootbox.alert('未登录!', function () {
                    window.top.location.href = loginPage;
                });
            } else {
                bootbox.alert(data.message);
            }
        }
    });
}

function updateList(result, data, url, grid) {
    var comformWin = bootbox.confirm({
        message: result.message,
        buttons: {
            confirm: {
                label: '<i class="icon-ok"></i>确定',
                className: 'btn-info'
            },
            cancel: {
                label: '<i class=" icon-remove"></i>取消',
                className: 'btn-default'
            }
        },
        callback: function (msg) {
            if (msg) {
                $.ajax({
                    url: url,
                    type: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        comformWin.modal('hide');
                        if (data.result == 200) {
                            bootbox.alert('更新成功！', function () {
                                grid.bootstrapTable('refresh');
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!', function () {
                                window.top.location.href = loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                    },
                    error: function () {
                        comformWin.modal('hide');
                        bootbox.alert('服务出错！');
                    }
                });
            }
        }
    })
}

//上传文件 先关掉上传控件
function webUploadrs(filePicker, c, url, multiple, callback, type) {
    var list = [];
    var $list = c.find('#fileList'),
        ratio = window.devicePixelRatio || 1,// 优化retina, 在retina下这个值是2
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,
        uploader = WebUploader.create({
            auto: false,// 自动上传。
            swf: '../../uploadHeader/Uploader.swf',// swf文件路径
            server: url,// 文件接收服务端。控制层,可以带参数
            threads: '30',        //同时运行30个线程传输
            fileNumLimit: multiple ? '10' : '1',  //文件总数量只能选择10个 
            headers: {"Authorization": token},
            pick: {
                id: filePicker,  //选择文件的按钮
                multiple: multiple ? true : false
            },   //允许可以同时选择多个图片
            quality: 100// 图片质量，只有type为`image/jpeg`的时候才有效
        });
    uploader.on('beforeFileQueued', function (file) {
        if (file.size == 0) {
            bootbox.alert('不能上传空文件！');
            return false;
        }
    })
    uploader.on('fileQueued', function (file) {
        var fileName = file.name;
        var arr1 = fileName.split(".");
        //获取后缀名
        var suffixName = arr1[arr1.length - 1];
        if (type == 'browser') {
            var parttrn = ["w_x86_exe", "l_x86_deb", "l_x86_rpm", "l_arm_deb", "l_arm_rpm", "l_mips_deb", "l_mips_rpm"];
            var arr = fileName.split("_");
            var length = arr.length;
            if (length < 4) {
                bootbox.alert('文件格式错误！');
                uploader.removeFile(file);
                return false;
            }
            //获取操作系统标识  l ,w
            var platform = arr[length - 3];
            //获取内核架构标识  x86 arm mips
            var framework = arr[length - 2];

            //得到匹配的字符串
            var s = platform + "_" + framework + "_" + suffixName;
            //同数组中的资源进行比对，包含代表格式正确，反之错误
            if (parttrn.indexOf(s) <= -1) {
                bootbox.alert('文件格式错误！');
                uploader.removeFile(file);
                return false;
            }
        } else if (type == 'cert') {
            if (suffixName != 'cer') {
                bootbox.alert('文件格式错误！');
                uploader.removeFile(file);
                return false;
            }
        } else if (type == 'plugin') {
            var flag = true;
            var fileName = file.name;
            var arr1 = fileName.split(".");
            //获取后缀名
            var suffixName = arr1[arr1.length - 1];
            if (suffixName == "exe") {
                //windows
                flag = false;
            } else if (suffixName == "deb") {
                //兆芯 deb 版本
                if (fileName.indexOf("x86") >= 0 || fileName.indexOf("x86_64") >= 0 || fileName.indexOf("amd64") >= 0) {
                    flag = false;
                }
                //arm deb版本
                else if (fileName.indexOf("arm") >= 0 || fileName.indexOf("arm6464") >= 0 || fileName.indexOf("aarch64") >= 0) {
                    flag = false;
                }
                //龙芯 deb 版本
                else if (fileName.indexOf("mips") >= 0 || fileName.indexOf("mips64") >= 0 || fileName.indexOf("mips64el") >= 0 || fileName.indexOf("loogson") >= 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            } else if (suffixName == "rpm") {
                //兆芯 rpm
                if (fileName.indexOf("x86") >= 0 || fileName.indexOf("x86_64") >= 0 || fileName.indexOf("amd64") >= 0) {
                    flag = false;
                }
                //arm rpm版本
                else if (fileName.indexOf("arm") >= 0 || fileName.indexOf("arm6464") >= 0 || fileName.indexOf("aarch64") >= 0) {
                    flag = false;
                }
                //龙芯 rpm 版本
                else if (fileName.indexOf("mips") >= 0 || fileName.indexOf("mips64") >= 0 || fileName.indexOf("mips64el") >= 0 || fileName.indexOf("loogson") >= 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
            if (flag) {
                bootbox.alert('文件格式错误！');
                uploader.removeFile(file);
                return false;
            }
        }
        c.find('#filePicker').hide();
        c.find(".flagPs").hide();
        c.find('.tips').hide();
        var $li = $(
            '<div style="background-color: #f5f5f5;" id="' + file.id + '" class="item">' + '<div class="info" title="' + file.name + '" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
            + file.name
            + '<i class="glyphicon glyphicon-remove remove-this mr20" id="removeClick" style="float: right;cursor: pointer;"></i>'
            + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
            + '</div>'
            ),
            $img = $li.find('img');
        // $list为容器jQuery实例
        $list.append($li);

        // 创建缩略图
        // thumbnailWidth x thumbnailHeight 为 100 x 100  打开后可以看到上传图片的代码，先关掉
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr('src', src);
        }, thumbnailWidth, thumbnailHeight);

        //删除上传的文件
        $list.on('click', '.remove-this', function () {
            c.find('#filePicker').show();
            if ($(this).parent().parent().attr('id') == file.id) {
                uploader.removeFile(file);
                $(this).parent().parent().remove();
            }
        });
    });
    // 文件上传过程中创建进度条实时显示。    uploadProgress事件：上传过程中触发，携带上传进度。 file文件对象 percentage传输进度 Nuber类型
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id, window.parent.document),
            $percent = $li.find('.progress .progress-bar', window.parent.document);
        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" style="height:20px; background-color:green;width: 0%" role="progressbar">' +
                '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');
        $percent.css('width', percentage * 100 + '%');
    });

    // 文件上传成功时候触发，给item添加成功class, 用样式标记上传成功。 file：文件对象，    response：服务器返回数据
    uploader.on('uploadSuccess', function (file, response) {
        if (response.result == 200) {
            c.find('#' + file.id).find('p.state').text('已上传');
            c.find('#' + file.id).addClass('upload-state-done');
            var obj = {};
            for (var key in response.data) {
                obj[key] = response.data[key];
            }
            list.push(obj);
            if (callback) {
                callback();
            }
        } else if (response.result == '300') {
            window.top.location.href = loginPage;
            return false;
        } else {
            bootbox.alert(response.message, function () {
                c.find('#removeClick').trigger('click');
            });
        }
    });

    // 文件上传失败    file:文件对象 ， code：出错代码
    uploader.on('uploadError', function (file, code) {
        // c.find('#' + file.id).find('p.state').text('上传出错');
        var $li = $('#' + file.id),
            $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        $error.text('上传失败!');
    });
    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        c.find('#' + file.id).find('.progress').fadeOut();
    });

    // 不管成功或者失败，文件上传完成时触发。 file： 文件对象
    uploader.on('uploadComplete', function (file) {
        c.find('#' + file.id).find('.progress').remove();
        c.find(".flagPs").hide();
    });

    //新建用户取消按钮
    c.find('.submitUserForm').on("click", function () {
        c.find('#filePicker').show();
        if (uploader) {
            uploader.destroy();
        }
        c.find("#btn").val("开始上传");
        c.find("#fileList").html('<p style="display: none;color: red;" class="flagPs">请选择需要上传的文件!</p>\n' +
            '<p style="display: none;color: red;" class="tips"></p>');
        c.find("textarea").val("");
    });
    c.find('.closeBtn').on("click", function () {
        c.find('#filePicker').show();
        if (uploader) {
            uploader.destroy();
        }
        c.find("#btn").val("开始上传");
        c.find("#fileList").html('<p style="display: none;color: red;" class="flagPs">请选择需要上传的文件!</p>\n' +
            '<p style="display: none;color: red;" class="tips"></p>');
        c.find("textarea").val("");
    });
    //绑定提交事件
    c.find('#btn').on("click", function () {
        uploader.upload();   //执行手动提交
        c.find(".flagPs").hide();
    });
    return list;

}
//对特殊字符处理的js-chang
function inuputReader(){
   $("input[type='text']").bind('keyup afterpaste keypress', function() {
   this.value=this.value.replace(/(<)|(>)|(&lt;)|(&gt;)|(script)/g,"");
  });
  $("input[type='url']").bind('keyup afterpaste keypress', function() {
   this.value=this.value.replace(/(<)|(>)|(&lt;)|(&gt;)|(script)/g,"");
  });
  $("textarea").bind('keyup afterpaste keypress', function() {
   this.value=this.value.replace(/(<)|(>)|(&lt;)|(&gt;)|(script)/g,"");
  });
 
}
