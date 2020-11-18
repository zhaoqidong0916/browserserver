
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    resize();
    var $listGrid=$('#detailGrid');
    $('.datetimepicker').datetimepicker({
        language: "zh-CN",
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        clearBtn: true,
        minView:'month'
    });
    $listGrid.bootstrapTable({
        url: urls.operateLog,
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
            field: 'loginName',
            title: '账号',
            width: 80,
            formatter:paramsMatter
        },{
            field: 'operateType',
            title: '概述',
            width: 80,
            formatter:function (value) {
                if(value == 1){
                    return '添加';
                }else if(value == 2){
                    return '删除';
                }else if(value == 3){
                    return '修改';
                }
            }
        },{
            field: 'ipAddress',
            title: 'IP地址',
            width: 140,
            formatter:paramsMatter
        },{
            field: 'value',
            title: '日志详情',
            width: 800,
            formatter:paramsMatter
        },{
            field: 'createTime',
            title: '创建时间',
            width: 180,
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
    function queryParams(params) {
        var temp = {
            offset: params.limit,  //页码
            loginName:$(".js_accountNumber").val(),
            ipAddress:$('.js_ipNumber').val(),
            createTime:$('.createTime').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

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

    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height() - 50 - 30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }
});
