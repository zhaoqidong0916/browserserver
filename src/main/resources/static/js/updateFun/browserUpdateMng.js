
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var list = [];
    var c = $('#browserUpdateModal');
    resize();
    var $listGrid=$('#detailGrid');
    //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            detailOptFun(urls.browserDetail+row.id,function (d) {
                paintContent($('#browserUpdateModal'),'edit',d,'编辑浏览器升级包');
                c.find('#uploader').parent().hide();
                c.find('.rowId').val(d.id);
                c.find('.browserType').val(d.browserType);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.browserDetail+row.id,function (d) {
                paintContent($('#browserDetailModal'),'scan',d,'查看浏览器升级包');

                $('#browserDetailModal').find('.constraintStatus').val(d.constraintStatus == 0?'是':'否' ).attr('disabled',true);
                $('#browserDetailModal').find('.historyStatus').val(d.historyStatus == 0?'是':'否').attr('disabled',true);
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.browserDelete + row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.browserUpdateList,
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign:'left',
        buttonsAlign:'right',
        contentType:'application/json',
        dataType:'json',
        ajaxOptions:{
            headers:{"Authorization":token}
        },
        queryParams: queryParams,
        singleSelect:true,
        offset: 10, // 页面数据条数
        pageNumber: 1, // 首页页码
        pageList: [10, 20,30], // 设置页面可以显示的数据条数
        clickToSelect: true,
        sidePagination: 'server', // 设置为服务器端分页
        detailView: true,
        detailFormatter: detailFormatter,
        columns: [{
            title: '序号',
            width:60,
            formatter: function (value, row, index) {
                var pageSize=$listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber=$listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        },{
            field: 'browserName',
            title: '安装包名称',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'version',
            title: '安装包版本号',
            width: 90,
            formatter:paramsMatter
        },{
            field: 'browserDescribe',
            title: '安装包描述',
            width: 130,
            formatter:paramsMatter
        },{
            field: 'constraintStatus',
            title: '强制更新',
            width: 70,
            formatter:function (value, row, index) {
                if(value == 0){
                    return '是';
                }else {
                    return '否';
                }
            }
        },{
            field: 'historyStatus',
            title: '当前版本',
            width: 70,
            formatter:function (value, row, index) {
                if(value == 0){
                    return '是';
                }else {
                    return '否';
                }
            }
        },{
            field: 'browserTypeName',
            title: '系统类型',
            width: 70,
            formatter:paramsMatter
        },{
            field: 'createTime',
            title: '创建时间',
            width: 140,
            formatter:paramsMatter
        },{
            field: 'operate',
            title: '操作',
            width: 80,
            events: operateEvents,
            formatter: function(idx,row){
                return [
                    '<i title="详情" class="detailOpt glyphicon glyphicon-eye-open mr20"></i>',
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange"></i>'
                    // '<i title="删除" class="delOpt glyphicon glyphicon-remove red"></i>'
                ].join('');
            }
        }],
        responseHandler:function(result){
            if(result.result=='300'){
                window.location.href=loginPage;
            }
            var data=result.data.records;
            if(!data||data.length==0){
                return {
                    total:0,
                    rows:[]
                }
            }
            return {
                total:result.data.total,
                rows:result.data.records
            }
        }
    });
    function queryParams(params) {
        var temp = {
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        $.extend(temp,getRequestHeader($('.searchItems')));
        return temp;
    }

    //设置附件展开
    function detailFormatter(index, row) {
        $.ajax({
            url: urls.browserHistory+row.browserType,
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
                    str += '<td title="'+list[i].browserName+'" class="textEllipse" style="width:120px;">'+list[i].browserName+'</td>';
                    str += '<td title="'+list[i].version+'" class="textEllipse" style="width:90px;">'+list[i].version+'</td>';
                    str += '<td title="'+list[i].browserDescribe+'" class="textEllipse"style="width:130px;">'+list[i].browserDescribe+'</td>';
                    str += '<td style="width:70px;">'+(list[i].constraintStatus==0?"是":"否")+'</td>';
                    str += '<td style="width:70px;">'+(list[i].historyStatus==0?"是":"否")+'</td>';
                    str += '<td style="width:70px;">'+list[i].browserTypeName+'</td>';
                    str += '<td style="width:140px;">'+list[i].createTime+'</td>';
                    str += '<td style="width:80px;"><i rowId="'+list[i].id+'" title="详情" class="sonDetailOpt glyphicon glyphicon-eye-open mr20"></i><i rowId="'+list[i].id+'" browserType="'+list[i].browserType+'" title="回退" class="backOpt glyphicon glyphicon-repeat green"></i></td></tr>';
                }
                $(".detail-icon").eq(index).parent().parent().after(str);
            }
        })
    }

    $('#detailGrid').on('click','.sonDetailOpt',function () {
        var rowid = $(this).attr('rowId');
        $.ajax({
            url: urls.browserDetail+rowid,
            type: "get",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                if (data.result == 200) {
                    $('#browserDetailModal').modal('show');
                    resetForm($('#browserDetailModal'));
                    $('#browserDetailModal').find('.browserDescribe').val(data.data.browserDescribe).attr('disabled',true);
                    $('#browserDetailModal').find('.constraintStatus').val(data.data.constraintStatus == 0?'是':'否' ).attr('disabled',true);
                    $('#browserDetailModal').find('.browserName').val(data.data.browserName).attr('disabled',true);
                    $('#browserDetailModal').find('.downloadUrl').val(data.data.downloadUrl).attr('disabled',true);
                    $('#browserDetailModal').find('.browserTypeName').val(data.data.browserTypeName).attr('disabled',true);
                    $('#browserDetailModal').find('.historyStatus').val(data.data.historyStatus == 0?'是':'否').attr('disabled',true);
                }else if(data.result==300){
                    bootbox.alert('未登录!',function () {
                        window.top.location.href=loginPage;
                    });
                }else{
                    bootbox.alert(data.message);
                }
            }
        });
    })

    $('#detailGrid').on('click','.backOpt',function () {
        var rowId = $(this).attr('rowId');
        var browserType = $(this).attr('browserType');
        $.ajax({
            url: urls.browserReset,
            type: "post",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            data:JSON.stringify({id:rowId,browserType:browserType}),
            dataType: "json",
            success: function (data) {
                if (data.result == 200) {
                    bootbox.alert('版本回退成功！',function () {
                        $listGrid.bootstrapTable('refresh');
                    });
                }else if(data.result==300){
                    bootbox.alert('未登录!',function () {
                        window.location.href=loginPage;
                    });
                }else {
                    bootbox.alert(data.message);
                }
            },
            error:function () {
                bootbox.alert('服务出错！');
            }
        });
    })

    var list;
    //新建角色弹窗
    $(".customerAdd").click(function(){
        $('#browserUpdateModal').modal('show');
        $('#browserUpdateModal .modal-title').text('新增浏览器升级包');
        resetForm(c);
        c.find('.rowId').val('');
        c.find('.browserType').val('');
        c.find('#uploader').parent().show();
        c.find('.submitUserForm').show();

        var filePicker = c.find("#filePicker");
        list = webUploadrs(filePicker,c,urls.browserUpload,false,null,'browser');
    });

    //重置
    $(".js_resetQuery").click(function(){
        resetSearchItems();
        $(".js_jqQuery").trigger('click');
    });

    /*查询*/
    $(".js_jqQuery").click(function(){
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    //保存 存库
    $('#browserUpdateModal .submitUserForm').on("click",function(){
        $('#browserUpdateModal').find("#filePicker").show();
        var validator = $('#browserUpdateModal form').validate();
        if(!validator.form()){
            return false;
        }
        var data = {};
        data.browserDescribe = c.find(".browserDescribe").val();
        data.constraintStatus = c.find(".constraintStatus").val();
        if(list.length>0){
            $.each(list,function () {
                $.extend(data,this);
            })
            data.md5 = '1';
        }
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
            data.browserType = c.find('.browserType').val();
        }
        data = JSON.stringify(data);
        $.ajax({
            url: c.find('.rowId').val()?urls.browserUpdate:urls.browserAdd,
            type: "post",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            data: data,
            dataType: "json",
            success: function(data){
                ajaxResponse(data,$listGrid,c);
            }
        })

    });

    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height() - 50 - 30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }
});
