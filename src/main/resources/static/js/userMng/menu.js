$(function () {
    var h = 0;
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    resize();
    bindEvent();
    var $listGrid = $('#detailGrid');
   //js实现对特殊字符的处理
   inuputReader();
    window.operateEvents = {
        'click .editMenu': function (e, value, row, index) {
            detailOptFun(urls.menuDetail+row.id,function (d) {
                paintContent($('#menuModal'),'edit',d,'编辑菜单');
                $('#menuModal').find('.htmlUrl').attr('disabled', true);
            });
        },
        'click .menuStatus' : function (e, value, row, index) {
            enableFun(urls.menuEnable,row,$listGrid,'菜单',function () {
                getLeftMenu();
            });
        },
        'click .delOpt' : function (e, value, row) {
            delOptFun(urls.menuDelete+row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.menuList,
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign: 'left',
        buttonsAlign: 'right',
        contentType: 'application/json',
        dataType: 'json',
        ajaxOptions: {
            headers: {"Authorization": token}
        },
        queryParams: queryParams,//传递参数（*）
        singleSelect: true,
        offset: 10, // 页面数据条数
        pageNumber: 1, // 首页页码
        pageList: [10, 20, 30], // 设置页面可以显示的数据条数
        clickToSelect: true,
        sidePagination: 'server', // 设置为服务器端分页
        // detailView: true,
        // detailFormatter: detailFormatter,
        columns: [{
            title: '序号',
            width: 60,
            formatter: function (value, row, index) {
                var pageSize = $listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber = $listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'parentName',
            title: '父名称',
            width: 120,
            formatter: paramsMatter
        }, {
            field: 'urlName',
            title: '子名称',
            width: 160,
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
            field: 'updateTime',
            title: '创建时间',
            width: 180,
            formatter: paramsMatter
        }, {
            field: 'operate',
            title: '操作',
            width: 120,
            events: operateEvents,
            formatter: function (idx, row) {
                if(row.parentName == '首页'){
                    var arr = ['<i title="编辑" rowId="' + row.id + '" class=" glyphicon glyphicon-edit mr20 grey"></i>',
                        '<i title="删除" class=" glyphicon glyphicon-trash mr20 grey"></i>'];
                }else{
                    var arr = ['<i title="编辑" rowId="' + row.id + '" class="editMenu glyphicon glyphicon-edit mr20 orange"></i>',
                        '<i title="删除" class="delOpt glyphicon glyphicon-trash mr20 red"></i>'];
                }

                if (row.checkStatus == 0) {
                    arr.push('<i title="禁用" style="color: #0079FE;" class="menuStatus glyphicon glyphicon-minus-sign"></i>');
                } else {
                    arr.push('<i title="启用" style="color: green;" class="menuStatus glyphicon glyphicon-play-circle"></i>');
                }
                return arr.join('');
            }
        }],
        //返回的数据进行数据结构转换
        responseHandler: function (result) {
            if (result.result == '300') {
                window.location.href=loginPage;
            }
            var data = result.data.records;
            if (!data || data.length == 0) {
                return {total: 0,rows: []}
            }
            return {
                total: result.data.total,
                rows: result.data.records
            }
        }
    });
    //设置附件展开
    function detailFormatter(index, row) {
        $.ajax({
            url: urls.menuSonList+row.parentName,
            type: "get",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                var list = data.data;
                var str = '';
                if (list.length == 0) {
                    str = '<P>暂无数据！</p>';
                    return false;
                }
                for (var i = 0; i < list.length; i++) {
                    str += '<tr class="detail-view"><td style="width:30px;"></td>';
                    str += '<td style="width:60px;">'+(i+1)+'</td>';
                    str += '<td title="'+list[i].parentName+'" class="textEllipse" style="width:120px;">'+list[i].parentName+'</td>';
                    str += '<td title="'+list[i].urlName+'" class="textEllipse"style="width:130px;">'+list[i].urlName+'</td>';
                    str += '<td style="width:80px;">'+(list[i].checkStatus==0?"已启用":"已禁用")+'</td>';
                    str += '<td style="width:140px;">'+list[i].createTime+'</td>';
                    str += '<td style="width:80px;">';
                    str += '<i title="编辑" rowId="' + row.id + '" class="editMenu glyphicon glyphicon-edit mr20 orange"></i>'+
                           '<i title="删除" class="delOpt glyphicon glyphicon-trash mr20 red"></i>'
                    if (row.checkStatus == 0) {
                        str += '<i title="禁用" style="color: #0079FE;" class="menuStatus glyphicon glyphicon-minus-sign"></i>';
                    } else {
                        str += '<i title="启用" style="color: green;" class="menuStatus glyphicon glyphicon-play-circle"></i>';
                    }
                    str += '</td></tr>';
                }
                $(".detail-icon").eq(index).parent().parent().after(str);
            }
        })
    }

    function queryParams(params) {
        var temp = {
            offset: params.limit,  //页码
            parentName: $(".js_accountNumber").val(),
            urlName: $('.js_name').val(),
            current: $listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }
    function bindEvent(){
        //新建模块弹框
        $(".js_newUsers").click(function () {
            $('#menuModal').modal('show');
            resetForm($('#menuModal'));
            $('#menuModal .modal-title').text('新建菜单');
            $('#menuModal #rowId').val('');
        });
        //添加及修改模块确定按钮
        $("#menuModal .menuFormBtn").on('click',function () {
            var c = $("#menuModal");
            var validator = c.find('form').validate();
            if (!validator.form()) {
                return false;
            }
            var data = getRequestHeader(c);
            if (c.find('#rowId').val()){
                data.id = c.find('#rowId').val();
            }
            data = JSON.stringify(data);
            if (c.find('#rowId').val()) {

                $.ajax({
                    url: urls.menuUpdate,
                    type: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        if (data.result == 200) {
                            bootbox.alert('操作成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                            c.modal('hide');
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        }else{
                            bootbox.alert(data.message);
                            c.modal('hide');
                        }
                    }
                });
            } else {
                $.ajax({
                    url: urls.menuAdd,
                    type: "post",
                    contentType: 'application/json',
                    beforeSend: function (request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        if (data.result == 200) {
                            bootbox.alert('操作成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                        } else if (data.result == 300) {
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        } else {
                            bootbox.alert(data.message);
                        }
                        c.modal('hide');
                    },
                    error: function () {
                        bootbox.alert('服务出错!');
                        c.modal('hide');
                    }
                })
            }
        });
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
