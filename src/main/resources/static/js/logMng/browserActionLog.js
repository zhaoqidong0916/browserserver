
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var mac;
    resize();
    var $listGrid=$('#detailGrid');
    var $detailGrid=$('#detailGrid2');
    $('.datetimepicker').datetimepicker({
        language: "zh-CN",
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        clearBtn: true,
        minView:'month'
    });
    window.operateEvents = {
        'click .detailList': function(e, value, row, index) {
            mac = row.mac;
            var opt = {
                silent: true,
                pageNumber: 1,
                url: urls.browserLog2,
                query: {
                    'mac': row.mac
                }
            };
            $('#detailGrid2').bootstrapTable('refresh',opt);
            $('.content1').show().siblings().hide();
            $('.searchItems1').show();
        }
    };
    $('#detailGrid2').bootstrapTable({
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
        queryParams: function(params) {
            var temp = {
                offset: params.limit,  //页码,
                pageSize: params.pageSize,
                ipAddress:$(".searchItems1 .ipAddress").val(),
                mac:mac,
                createTime:$('.searchItems1 .createTime').val(),
                current:$detailGrid.bootstrapTable('getOptions').pageNumber
            };
            return temp;
        },
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
                var pageSize=$detailGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber=$detailGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        },{
            field: 'ipAddress',
            title: 'IP地址',
            width: 80,
            formatter:paramsMatter
        },{
            field: 'mac',
            title: 'mac',
            width: 80,
            formatter:paramsMatter
        },{
            field: 'title',
            title: '浏览网页的标题',
            width: 130,
            formatter:paramsMatter
        },{
            field: 'url',
            title: 'url',
            width: 130,
            formatter:paramsMatter
        },{
            field: 'browserVersion',
            title: '浏览器版本',
            width: 80,
            formatter:paramsMatter
        },{
            field: 'createTime',
            title: '创建时间',
            width: 80,
            formatter:paramsMatter
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

    $('#detailGrid').bootstrapTable({
        url: urls.browserLog,
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
        queryParams: queryParams,//传递参数（*）
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
            field: 'mac',
            title: 'mac',
            width: 80,
            events: operateEvents,
            formatter: function(value, row, index){
                return '<span class="detailList blue cursorDft">'+value+'</span>';
            }
        },{
            field: 'count',
            title: '请求次数',
            width: 80,
            events: operateEvents,
            formatter: function(value, row, index){
                return '<span class="detailList blue cursorDft">'+value+'</span>';
            }
        },{
            field: 'createTime',
            title: '最近请求时间',
            width: 80,
            formatter:paramsMatter
        }],
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
    // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
    function queryParams(params) {
        var temp = {
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            ipAddress:$(".searchItems .ipAddress").val(),
            mac:$('.searchItems .mac').val(),
            createTime:$('.searchItems .createTime').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }
    $('#backBtn').click(function(){
       $('.content1').hide().siblings().show();
       $('.searchItems1').hide();
    });

    //重置
    $(".js_resetQuery").click(function(){
        resetSearchItems();
        $(".js_jqQuery").trigger('click');
    });
    $(".resetBtn").click(function(){
        resetSearchItems();
        $(".searchBtn").trigger('click');
    });
    /*查询*/
    $(".js_jqQuery").click(function(){
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $listGrid.bootstrapTable('refresh',opt);
    });
    /*查询*/
    $(".searchBtn").click(function(){
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $detailGrid.bootstrapTable('refresh',opt);
    });

    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height() - 50 - 30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }

});
