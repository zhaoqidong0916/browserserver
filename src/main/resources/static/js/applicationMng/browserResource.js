
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    var list = [];
    var c = $('#resourceModal');
    resize();
    var $listGrid=$('#detailGrid');
  //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            detailOptFun(urls.resourceDetail+row.id,function (d) {
                paintContent(c,'edit',d,'编辑背景配置');
                c.find('.rowId').val(d.id);
                c.find('select.resourcesType').attr('disabled',true);
                if(d.resourcesType == 1){
                    c.find('.loadDiv').hide();
                }else {
                    c.find('.loadDiv').show();
                    var filePicker = c.find("#filePicker");
                    list = webUploadrs(filePicker,c,urls.resourceUpload);
                }
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.resourceDetail+row.id,function (d) {
                paintContent($('#resourceDetailModal'),'scan',d,'查看背景配置');
                if(d.resourcesType == 1){
                    $('#resourceDetailModal').find('.resourcesType').val('文本');
                }else if(d.resourcesType == 2){
                    $('#resourceDetailModal').find('.resourcesType').val('图片');
                }else if(d.resourcesType == 3){
                    $('#resourceDetailModal').find('.resourcesType').val('视频');
                }else{
                    $('#resourceDetailModal').find('.resourcesType').val('未知');
                }
                if(d.resourcesType == 1){
                    $('#resourceDetailModal').find('.patchDiv').hide();
                }else {
                    $('#resourceDetailModal').find('.patchDiv').show();
                    if (d.downloadUrl) {
                        var arr = d.downloadUrl.split('.');
                        var str = '';
                        var type = arr[arr.length - 1];
                        if (type == 'mp4' || type == 'avi' || type == 'rmvb' || type == 'rm' || type == 'mpeg') {
                            str = '<video width="200" height="200" controls src="' + d.downloadUrl + '"></video>'
                        } else {
                            str = '<image style="width: 100%;height: 200px;" src="' + d.downloadUrl + '" />';
                        }
                        $('#resourceDetailModal').find('.playerDiv').html(str);
                    } else {
                        $('#resourceDetailModal').find('.playerDiv').html('无附件');
                    }
                }
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.resourceDelete + row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.resourceList,
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
            field: 'resourcesName',
            title: '名称',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'downloadUrl',
            title: 'url',
            width: 130,
            formatter:paramsMatter
        },{
            field: 'resourcesType',
            title: '资源类型',
            width: 80,
            formatter:function (value, row) {
                if(value == 1){
                    return '文本';
                }else if(value == 2){
                    return '图片';
                }else if(value == 3){
                    return '视频';
                }else {
                    return '--';
                }
            }
        },{
            field: 'resourcesDescribe',
            title: '描述',
            width: 200,
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
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        $.extend(temp,getRequestHeader($('.searchItems')));
        return temp;
    }
    $('#resourceModal .resourcesType').change(function () {
        if($(this).val()==1){
            $('#resourceModal .loadDiv').hide();
        }else {
            $('#resourceModal .loadDiv').show();
        }
    })

    var list;
    //新建角色弹窗
    $(".customerAdd").click(function(){
        $('#resourceModal').modal('show');
        $('#resourceModal .modal-title').text('新增背景配置');
        resetForm(c);
        c.find('.rowId').val('');
        c.find('#uploader').parent().show();
        c.find('.submitUserForm').show();

        var filePicker = c.find("#filePicker");
        list = webUploadrs(filePicker,c,urls.resourceUpload);
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
    $('#resourceModal .submitUserForm').on("click",function(){
        $('#resourceModal').find("#filePicker").show();
        var validator = $('#resourceModal form').validate();
        if(!validator.form()){
            return false;
        }
        var data = getRequestHeader(c);
        if(list.length>0){
            $.each(list,function () {
                $.extend(data,this);
            })
        }
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
        }
        data = JSON.stringify(data);
        $.ajax({
            url: c.find('.rowId').val()?urls.resourceUpdate:urls.resourceAdd,
            type: "post",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            data: data,
            dataType: "json",
            success: function(data){
                ajaxResponse(data, $listGrid, c);
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
