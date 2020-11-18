
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var list = [];
    resize();
    var $listGrid=$('#detailGrid');
    //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            var c = $('#watermarkModal');
            detailOptFun(urls.concurrenceAmountDetail,function (d) {
                paintContent($('#watermarkModal'), 'edit', d ,'修改设定限值');
            })
        }
    };
    $listGrid.bootstrapTable({
        url: urls.concurrenceAmountList,
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
            field: 'concurrenceAmount',
            title: '当前设定限值',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'operate',
            title: '操作',
            width: 80,
            events: operateEvents,
            formatter: function(idx,row){
                return [
                    '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange"></i>'
                ].join('');
            }
        }],
        //返回的数据进行数据结构转换
        responseHandler:function(result){
            if(result.result=='300'){
                window.top.location.href = loginPage;
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

    //保存
    $('#watermarkModal .submitUserForm').on("click",function(){
        var validator = $('#watermarkModal form').validate();
        if(!validator.form()){
            return false;
        }
        var c = $('#watermarkModal');
        var data = getRequestHeader(c);
        if(list.length>0){
            $.each(list,function () {
                $.extend(data,this);
            })
        }
        data = JSON.stringify(data);
        $.ajax({
            url: urls.concurrenceAmountupdate,
            type: 'post',
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
