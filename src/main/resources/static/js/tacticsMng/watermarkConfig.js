
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
            detailOptFun(urls.watermarkDetail+row.id,function (d) {
                paintContent($('#watermarkModal'), 'edit', d ,'修改水印信息');
                c.find('.rowId').val(d.id);
                c.find('.downloadUrl').val(d.downloadUrl);
                $('#watermarkModal').find('.uploadDiv').hide();
                $('#watermarkModal').find('.patchDiv').hide();
                $('#watermarkModal').find('input.watermarkState').hide();
                $('#watermarkModal').find('select.watermarkState').show();

                // var filePicker = c.find("#filePicker");
                // list = webUploadrs(filePicker,c,urls.watermarkUpload);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.watermarkDetail+row.id,function (d) {
                $('#watermarkModal').find('.uploadDiv').hide();
                $('#watermarkModal').find('.patchDiv').hide();
                $('#watermarkModal').find('select.watermarkState').hide();
                paintContent($('#watermarkModal'), 'scan', d ,'水印信息详情');
                $('#watermarkModal').find('input.watermarkState').show().attr('disabled',false).val(d.watermarkState==0?'禁止':'开启').attr('disabled',true);
                if(d.downloadUrl){
                    var arr = d.downloadUrl.split('.');
                    var str = '';
                    var type = arr[arr.length-1];
                    if(type=='mp4'||type=='avi'||type=='rmvb'||type=='rm'||type=='mpeg'){
                        str = '<video width="200" height="200" controls src="'+d.downloadUrl+'"></video>'
                    }else{
                        str = '<image style="width: 100%;height: 200px;" src="'+d.downloadUrl+'" />';
                    }
                    $('#watermarkModal').find('.playerDiv').html('').append(str);
                }else{
                    $('#watermarkModal').find('.playerDiv').html('无附件');
                }
            })
        }
    };
    $listGrid.bootstrapTable({
        url: urls.watermarkList,
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
            field: 'watermarkName',
            title: '水印名称',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'transparency',
            title: '透明度',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'watermarkState',
            title: '水印开启状态',
            width: 120,
            formatter:function (value) {
                if(value == 0){
                    return '禁止';
                }
                return '开启';
            }
        },{
            field: 'operate',
            title: '操作',
            width: 80,
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
        $('#watermarkModal').modal('show');
        $('#watermarkModal .submitUserForm').show();
        $('#watermarkModal .modal-title').text('新增水印信息');
        resetForm($('#watermarkModal'));
        $('#watermarkModal').find('.rowId').val('');
        $('#watermarkModal').find('.downloadUrl').val('');
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
    $('#watermarkModal .submitUserForm').on("click",function(){
        var validator = $('#watermarkModal form').validate();
        if(!validator.form()){
            return false;
        }
        var c = $('#watermarkModal');
        var data = getRequestHeader(c);
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
            data.downloadUrl = c.find('.downloadUrl').val();
        }
        if(list.length>0){
            $.each(list,function () {
                $.extend(data,this);
            })
        }
        data = JSON.stringify(data);
        $.ajax({
            url: urls.watermarkUpdate,
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
