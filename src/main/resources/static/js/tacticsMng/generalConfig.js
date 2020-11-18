
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var c = $('#generalModal');
    resize();
    var $listGrid=$('#detailGrid');
  //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            detailOptFun(urls.generalDetail+row.id,function (d) {
                paintContent($('#generalEditModal'),'edit',d,'修改浏览器通用配置');
                $('#generalEditModal').find('input').each(function () {
                    if($(this).val() == 0){
                        $(this).val('关闭');
                    }else if($(this).val() == 1){
                        $(this).val('开启');
                    }
                })
                $('#generalEditModal').find('.rowId').val(d.id);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.generalDetail+row.id,function (d) {
                paintContent(c,'scan',d,'浏览器通用配置查看');
                c.find('input').each(function () {
                    if($(this).val() == 0){
                        $(this).attr('disabled',false).val('关闭').attr('disabled',true);
                    }else if($(this).val() == 1){
                        $(this).attr('disabled',false).val('开启').attr('disabled',true);
                    }
                })
            })
        }
    };
    $listGrid.bootstrapTable({
        url: urls.generalList,
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
            field: 'indexPage',
            title: '浏览器默认首页',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'copyState',
            title: '拷贝状态',
            width: 80,
            formatter:paramsMatter1
        },{
            field: 'dragState',
            title: '拖拽状态',
            width: 80,
            formatter:paramsMatter1
        },{
            field: 'printState',
            title: '打印状态',
            width: 80,
            formatter:paramsMatter1
        },{
            field: 'downloadState',
            title: '下载状态',
            width: 80,
            formatter:paramsMatter1
        },{
            field: 'cacheState',
            title: '缓存状态',
            width: 80,
            formatter:paramsMatter1
        },{
            field: 'integrityState',
            title: '完整性校验',
            width: 90,
            formatter:paramsMatter1
        },{
            field: 'operate',
            title: '操作',
            width: 60,
            events: operateEvents,
            formatter: function(idx,row){
                return [
                    '<i title="详情" class="detailOpt glyphicon glyphicon-eye-open mr20"></i>',
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange"></i>'
                ].join('');
            }
        }],
        //返回的数据进行数据结构转换
        responseHandler:function(result){
            if(result.result=='300' || result.result=='404'){
                window.top.location.href=loginPage;
            }
            var data=result.data;
            if(!data||data.length==0){
                return {
                    total:0,
                    rows:[]
                }
            }
            return {
                total:1,
                rows:result.data
            }
        }
    });
    function queryParams(params) {
        var temp = {
            limit : params.limit, // 每页显示数量
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            certName:$(".certName").val(),
            certTypeName:$(".certTypeName").val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    //表格提示信息
    function paramsMatter1(value, row, index) {
        if(value == '0'){
            return '<div>关闭</div>';
        }else if(value == '1'){
            return '<div>开启</div>';
        }else{
            return '-';
        }
    }

    //重置
    $(".resetBtn").click(function(){
        resetSearchItems();
        $listGrid.bootstrapTable('refresh');
    });

    /*查询*/
    $(".searchBtn").click(function(){
        $listGrid.bootstrapTable('refresh');
    });

    //保存 存库
    $('#generalEditModal .submitUserForm').on("click",function(){
        var validator = $('#generalEditModal form').validate();
        if(!validator.form()){
            return false;
        }
        var data = getRequestHeader($('#generalEditModal'));
        if($('#generalEditModal').find('.rowId').val()){
            data.id = $('#generalEditModal').find('.rowId').val();
        }
        data = JSON.stringify(data);
        $.ajax({
            url: urls.generalUpdate,
            method: "post",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            data: data,
            dataType: "json",
            success: function(data){
                ajaxResponse(data,$listGrid,$('#generalEditModal'));
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
