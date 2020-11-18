$(function () {
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var roleIds = [];
    resize();
    var $listGrid = $('#detailGrid');
     //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .delOpt': function (e, value, row, index) {
            if (row.roleName == 'admin') {
                bootbox.alert('您没有admin删除权限!');
                return false;
            }
            delOptFun(urls.roleDelete+row.id,$listGrid);
        },
        'click .editOpt': function (e, value, row, index) {
            if (row.roleName == 'admin') {
                bootbox.alert('您没有admin编辑权限!');
                return false;
            }
            detailOptFun(urls.roleDetail+row.id,function (d) {
                paintContent($('#popRole'),'edit',d,'编辑角色');
                $('#popRole .roleId').val(row.id);
                $('#popRole .beforeRoleName').val(row.roleName);
            })
        },
        'click .scanOpt': function (e, value, row, index) {
            detailOptFun(urls.roleDetail+row.id,function (d) {
                paintContent($('#popRole'),'scan',d,'查看角色');
            })
        },
        'click .addAuth': function (e, value, row, index) {
            $.ajax({
                url: urls.roleResourceList+row.id,
                type: "get",
                contentType: 'application/json',
                beforeSend: function (request) {
                    request.setRequestHeader("Authorization", token);
                },
                dataType: "json",
                success: function (data) {
                    if (data.result == 200) {
                        if (row.roleName == 'admin') {
                            $('#menuSetModal .addRoleBtn1').hide();//隐藏确定按钮
                        }else{
                            $('#menuSetModal .addRoleBtn1').show();//显示确定按钮
                        }
                        $("#menuSetModal").find('label.error').hide();
                        $("#menuSetModal .roleId").val(row.id);
                        $("#menuSetModal .roleName").val(row.roleName);
                        var str = '';
                        var d = data.data;
                        var arr = [];//用于存储初始状态下一级菜单的勾选状态
                        for(var i=0;i<d.length;i++) {
                            var sd = d[i].resourceList;
                            str += '<div class="form-group parentMenu">' +
                                '<input id="00' + i + '" type="checkbox"  />' +
                                '<label for="00'+ i +'">' + d[i].resourceType + '</label>' +
                                '</div>'+
                                '<div class="form-group sonMenu">';
                            var flag = 0;
                            for(var j = 0;j<sd.length;j++) {
                                if(sd[j].selectEd==1){
                                    flag++;
                                }
                                str += '<input authId="'+sd[j].id+'" authUrl="'+sd[j].htmlUrl+'" '+(sd[j].selectEd==1?"checked":"")+'  type="checkbox" id="'+(i+'00'+j)+'"/>' +
                                            '<label for="'+(i+'00'+j)+'">'+sd[j].urlName+'</label>';
                            }
                            str += '</div>';
                            if(flag == sd.length){
                                arr.push(1);
                            }else{
                                arr.push(0);
                            }
                        }
                        $("#electionBox1").html(str);
                        $.each(arr,function (i,val) {
                            if(this == 1){
                                $("#electionBox1").find('.parentMenu').eq(i).find('input').attr('checked','checked');
                            }
                        });
                        $('#menuSetModal').modal('show');
                    } else if (data.result == 300) {
                        bootbox.alert('未登录!',function () {
                            window.location.href=loginPage;
                        });
                    } else {
                        bootbox.alert(data.message);
                    }
                }
            });
        },
        'click .roleStatus':function (e, value, row, index) {
            enableFun(urls.roleEnable, row, $listGrid,'角色');
        }
    };
    bindEvent();
    $listGrid.bootstrapTable({
        url: urls.roleList,
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign: 'left',
        buttonsAlign: 'right',
        contentType: 'application/json',
        ajaxOptions: {
            headers: {"Authorization": token}
        },
        dataType: 'json',
        queryParams: queryParams,//传递参数（*）
        singleSelect: true,
        offset: 10, // 页面数据条数
        pageNumber: 1, // 首页页码
        pageList: [10, 20, 30], // 设置页面可以显示的数据条数
        clickToSelect: true,
        sidePagination: 'server', // 设置为服务器端分页
        columns: [{
            title: '序号',
            width: 60,
            formatter: function (value, row, index) {
                var pageSize = $listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber = $listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'roleName',
            title: '角色',
            width: 140,
            formatter: paramsMatter
        }, {
            field: 'roleDescription',
            title: '角色描述',
            width: 180,
            formatter: paramsMatter
        }, {
            field: 'checkStatus',
            title: '状态',
            width: 140,
            formatter: function (idx, row) {
                if (row.checkStatus == 0) {
                    return '已启用';
                } else {
                    return '已禁用';
                }
            }
        }, {
            field: 'createTime',
            title: '创建时间',
            width: 180,
            formatter: paramsMatter
        }, {
            field: 'operate',
            title: '操作',
            width: 160,
            events: operateEvents,
            formatter: function (idx, row) {
                var arr = [
                    '<i title="添加角色权限" class="glyphicon glyphicon-plus-sign mr20 addAuth green"></i>',
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit mr20 orange"></i>',
                    '<i title="查看" class="scanOpt glyphicon glyphicon-eye-open mr20"></i>',
                    '<i title="删除" class="delOpt glyphicon glyphicon-trash mr20 red"></i>'
                ];
                if (row.checkStatus == 0) {
                    arr.push('<i title="禁用" style="color: #0079FE;" class="roleStatus glyphicon glyphicon-minus-sign"></i>');
                } else {
                    arr.push('<i title="启用" style="color: green;" class="roleStatus glyphicon glyphicon-play-circle"></i>');
                }
                return arr.join('');
            }
        }],
        responseHandler: function (result) {
            if (result.result == '300') {
                window.location.href=loginPage;
            }
            var data = result.data.records;
            for (var i = 0; i < data.length; i++) {
                if (data[i].roleId) {
                    var obj = {};
                    obj.id = data[i].userId;
                    obj.roleId = data[i].roleId;
                    roleIds.push(obj)
                }
            };
            if (!data || data.length == 0) {
                return {
                    total: 0,
                    rows: []
                }
            }
            return {
                total: result.data.total,
                rows: result.data.records
            }
        }
    });

    function bindEvent() {
        $('#electionBox1').on('click','.parentMenu input',function () {
            if($(this).is(':checked')){
                $(this).parent().next().find('input').prop('checked',true);
            }else{
                $(this).parent().next().find('input').attr('checked',false);
            }
        })
        $('#electionBox1').on('click','.sonMenu input',function () {
            if($(this).is(':checked')){
                if($(this).siblings('input:checked').length == $(this).siblings('input').length){
                    $(this).parent().prev().find('input').prop('checked',true);
                }
            }else{
                $(this).parent().prev().find('input').attr('checked',false);
            }
        })
        //新建角色弹窗
        $(".js_role").click(function () {
            $('#popRole').modal('show');
            resetForm($('#popRole'));
            $('#popRole .modal-title').text('新建角色');
            $('#popRole .addRoleBtn').show();
            $('#popRole .roleId').val('');
            $('#popRole .beforeRoleName').val('');
            $('#popRole .roleName').val('');
        });
        //新建用户确定按钮
        $(".addRoleBtn").on("click", function () {
            var validator = $('#popRole form').validate();
            if (!validator.form()) {
                return false;
            }
            var data = getRequestHeader($('#popRole'));
            if ($('#popRole').find('.roleId').val()) {//修改
                data.id = $('#popRole').find('.roleId').val();
                if($('#popRole').find('.beforeRoleName').val() == $('#popRole').find('.roleName').val()){
                    delete data.roleName;
                }
                data = JSON.stringify(data);
                $.ajax({
                    url: urls.roleUpdate,
                    type: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        $('#popRole').modal('hide');
                        if (data.result == 200) {
                            bootbox.alert('编辑成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                    }
                })
            } else {
                data = JSON.stringify(data);
                $.ajax({
                    url: urls.roleAdd,
                    type: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function (data) {
	debugger;
                        if (data.result == 200) {
                            bootbox.alert('新增成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                        $('#popRole').modal('hide');
                    }
                })
            }
        });

        //添加权限确定按钮
        $(".addRoleBtn1").on("click", function () {
            var data = {
                roleId: $('#menuSetModal .roleId').val(),
                roleName: $('#menuSetModal .roleName').val(),
                params: []
            };
            var checkboxs = $('#electionBox1 .sonMenu input:checked'); //所有单选的input
            checkboxs.each(function (inx, ele) {
                data.params.push({resourceId:$(this).attr('authid')});
            });
            data = JSON.stringify(data);
            $.ajax({
                url: urls.roleResourceAdd,
                type: "post",
                contentType: 'application/json',
                beforeSend: function (request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function (data) {
                    if (data.result == 200) {
                        bootbox.alert('添加权限成功!');
                    } else if (data.result == 300) {
                        bootbox.alert('未登录!',function () {
                            window.location.href=loginPage;
                        });
                    } else {
                        bootbox.alert(data.message);
                    }
                    $("#menuSetModal").modal('hide');
                }
            })
        });
    }
    function queryParams(params) {
        var temp = {
            offset: params.limit,
            pageSize: params.pageSize,
            roleName: $(".roleName").val(),//角色
            current: $listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }
    //重置
    $(".js_resetQuery").click(function () {
        resetSearchItems();
        $listGrid.bootstrapTable('refresh');
    });
    /*查询*/
    $(".query").click(function () {
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height()-50-30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }
});
