
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
            var c = $('#compatibleModal');
            detailOptFun(urls.compatibleDetail+row.id,function (d) {
                paintContent($('#compatibleModal'), 'edit', d ,'编辑浏览器兼容配置');
                c.find('select.kernel').show();
                c.find('input.kernel').hide();
                c.find('.rowId').val(d.id);
                c.find('.urlBefore').val(d.url);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.compatibleDetail+row.id,function (d) {
                paintContent($('#compatibleModal'), 'scan', d ,'浏览器兼容配置详情');
                $('#compatibleModal').find('select.kernel').hide();
                $('#compatibleModal').find('input.kernel').show();
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.compatibleDelete + row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.compatibleList,
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
        columns: [{
            title: '序号',
            width:60,
            formatter: function (value, row, index) {
                var pageSize=$listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber=$listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        },{
            field: 'url',
            title: 'URL',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'kernel',
            title: '浏览器内核',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'operate',
            title: '操作',
            width: 80,
            events: operateEvents,
            formatter: function(idx,row){
                return [
                    '<i title="详情" class="detailOpt glyphicon glyphicon-eye-open mr20"></i>',
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange mr20"></i>',
                    '<i title="删除" class="delOpt glyphicon glyphicon-remove red"></i>'
                ].join('');
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
            limit : params.limit, // 每页显示数量
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            kernel:$(".searchItems .kernel").val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }
    //新建角色弹窗
    $(".addBtn").click(function(){
        $('#compatibleModal').modal('show');
        $('#compatibleModal .submitUserForm').show();
        $('#compatibleModal .modal-title').text('新增浏览器兼容配置');
        resetForm($('#compatibleModal'));
        $('#compatibleModal').find('.rowId').val('');
        $('#compatibleModal').find('.urlBefore').val('');
        $('#compatibleModal').find('select.kernel').show();
        $('#compatibleModal').find('input.kernel').hide();
    });

    //重置
    $(".resetBtn").click(function(){
        resetSearchItems();
        $(".searchBtn").trigger('click');
    });

    /*查询*/
    $(".searchBtn").click(function(){
        $listGrid.bootstrapTable('refresh');
    });

    //保存 存库
    $('#compatibleModal .submitUserForm').on("click",function(){
        var validator = $('#compatibleModal form').validate();
        if(!validator.form()){
            return false;
        }
        var c = $('#compatibleModal');
        var data = getRequestHeader(c);
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
            if(c.find('.urlBefore').val() == c.find('.url').val()){
                delete data.url;
            }
        }
        data = JSON.stringify(data);
        $.ajax({
            url: c.find('.rowId').val()?urls.compatibleUpdate:urls.compatibleAdd,
            method: "post",
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
