
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    resize();
    var $listGrid=$('#detailGrid');
   //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            var c = $('#visitModal');
            detailOptFun(urls.safeVisitDetail+row.id,function (d) {
                paintContent($('#visitModal'), 'edit', d ,'编辑URL地址');
                c.find('.rowId').val(d.id);
                c.find('.beforeUrl').val(d.forbiddenUrl);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.safeVisitDetail+row.id,function (d) {
                paintContent($('#visitModal'), 'scan', d ,'URL地址详情');
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.safeVisitDelete + row.id,$listGrid);
        },
        'click .blackListOpt': function (e, value, row) {
            $.ajax({
                url: urls.safeVisitState,
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data:JSON.stringify({id:row.id,state:row.state==0?1:0}),
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        bootbox.alert('操作成功！',function () {
                            $listGrid.bootstrapTable('refresh');
                        })
                    }else if(data.result==300){
                        bootbox.alert('未登录!',function () {
                            window.top.location.href=loginPage;
                        });
                    }else {
                        bootbox.alert(data.message);
                    }
                }
            });
        }
    };
    $listGrid.bootstrapTable({
        url: urls.safeVisitList,
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign:'left',
        buttonsAlign:'right',
        smartDisplay:false,
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
        columns: [{
            title: '序号',
            width:60,
            formatter: function (value, row, index) {
                var pageSize=$listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber=$listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        },{
            field: 'forbiddenUrl',
            title: 'URL地址',
            width: 140,
            formatter:paramsMatter
        },{
            field: 'urlDescription',
            title: '备注',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'state',
            title: '状态',
            width: 70,
            formatter:function (value) {
                if(value == 0){
                    return '白名单';
                }else if(value == 1){
                    return '黑名单';
                }
            }
        },{
            field: 'createTime',
            title: '创建时间',
            width: 140,
            formatter:paramsMatter
        },{
            field: 'operate',
            title: '操作',
            width: 120,
            events: operateEvents,
            formatter: function(idx,row){
                var arr = ['<i title="详情" class="detailOpt glyphicon glyphicon-eye-open mr20"></i>',
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange mr20"></i>',
                    '<i title="删除" class="delOpt glyphicon glyphicon-remove red mr20"></i>'];
                if(row.state == 0){
                    arr.push('<i title="移入黑名单" class="blackListOpt glyphicon glyphicon-user green"></i>');
                }else{
                    arr.push('<i title="移出黑名单" class="blackListOpt glyphicon glyphicon-user"></i>')
                }
                return arr.join('');
            }
        }],
        //返回的数据进行数据结构转换
        responseHandler:function(result){
            if(result.result=='300'){
                window.top.location.href=loginPage;
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
    //新建角色弹窗
    $(".addBtn").click(function(){
        $('#visitModal').modal('show');
        $('#visitModal .submitUserForm').show();
        $('#visitModal .modal-title').text('新增URL地址');
        resetForm($('#visitModal'));
        $('#visitModal').find('.rowId').val('');
        $('#visitModal').find('.beforeUrl').val('');
    });

    //重置
    $(".resetBtn").click(function(){
        resetSearchItems();
        $listGrid.bootstrapTable('refresh');
    });

    /*查询*/
    $(".searchBtn").click(function(){
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    //保存 存库
    $('#visitModal .submitUserForm').on("click",function(){
        var validator = $('#visitModal form').validate();
        if(!validator.form()){
            return false;
        }
        var c = $('#visitModal');
        var data = getRequestHeader(c);
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
            if(c.find('.beforeUrl').val() == c.find('.forbiddenUrl').val()){
                delete data.forbiddenUrl;
            }
        }
        data = JSON.stringify(data);
        $.ajax({
            url: c.find('.rowId').val()?urls.safeVisitUpdate:urls.safeVisitAdd,
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
