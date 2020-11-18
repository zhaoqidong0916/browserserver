
$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);

    var h = 0;
    var list = [];
    var c = $('#certUpdateModal');
    var listLen = 0;
    resize();
    getPluginTypes();
    var $listGrid=$('#detailGrid');
   //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            detailOptFun(urls.certDetail + row.id, function (d) {
                paintContent($('#certUpdateModal'), 'edit', d, '编辑证书');
                c.find('#uploader').parent().hide();
                c.find('.rowId').val(d.id);
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.certDetail+row.id,function (d) {
                paintContent($('#certDetailModal'),'scan',d,'查看证书');
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.certDelete + row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.certUpdateList,
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
            field: 'certName',
            title: '证书名称',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'certDescribe',
            title: '证书描述',
            width: 130,
            formatter:paramsMatter
        },{
            field: 'certTypeName',
            title: '证书类型',
            width: 80,
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
                    // '<i title="编辑" class="editOpt glyphicon glyphicon-edit orange mr20"></i>',
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
    var list;
    //新建角色弹窗
    $(".customerAdd").click(function(){
        if(!listLen || listLen == 0){
            bootbox.alert('暂无证书类型，请先配置证书类型！');
            return false;
        }
        $('#certUpdateModal').modal('show');
        $('#certUpdateModal .modal-title').text('新增证书');
        resetForm(c);
        c.find('.rowId').val('');
        c.find('#uploader').parent().show();
        c.find('.submitUserForm').show();

        var filePicker = c.find("#filePicker");
        list = webUploadrs(filePicker,c,urls.certUpload,false,null,'cert');
    });
    $('.addCertType').click(function () {
        $('#certTypeModal').modal('show');
        resetForm($('#certTypeModal'));
    })

    //重置
    $(".js_resetQuery").click(function(){
        resetSearchItems();
        $listGrid.bootstrapTable('refresh');
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
    $('#certUpdateModal .submitUserForm').on("click",function(){
        $('#certUpdateModal').find("#filePicker").show();
        var validator = $('#certUpdateModal form').validate();
        if(!validator.form()){
            return false;
        }
        var data = {};
        data.certTypeName = c.find(".certType option:selected").text();
        data.certType = c.find(".certType").val();
        data.certDescribe = c.find(".describe").val();
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
            url: c.find('.rowId').val()?urls.certUpdate:urls.certAdd,
            type: "post",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            data: data,
            dataType: "json",
            success: function(d){
                if(d.result==200){
                    bootbox.alert('操作成功!',function () {
                        $listGrid.bootstrapTable('refresh');
                    });
                }else if(d.result==20){
                    updateList(d, data,urls.certAddUpdate,$listGrid);
                }else if(d.result==300){
                    bootbox.alert('未登录!',function () {
                        window.top.location.href=loginPage;
                    });
                }else{
                    bootbox.alert(d.message);
                }
                c.modal('hide');
            }
        })

    });

    $('#certTypeModal .submitUserForm').on("click",function(){
        var validator = $('#certTypeModal form').validate();
        if(!validator.form()){
            return false;
        }
        var c = $('#certTypeModal');
        var data = {};
        data.certTypeName = c.find('.certTypeName').val();
        data = JSON.stringify(data);
        $.ajax({
            url: urls.typeCertAdd,
            type: "post",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            data: data,
            dataType: "json",
            success: function(data){
                ajaxResponse(data,null,c,function () {
                    getPluginTypes();
                })
            }
        })
    });

    $('#certTypeModal').on('click','.delType',function () {
        delOptFun(urls.typeCertDelete + $(this).parent().attr('value'),null,function () {
            getPluginTypes();
        });
    })
    function getPluginTypes(callback) {
        $.ajax({
            url: urls.certTypeList,
            type: "get",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                var arr = [];
                var arr1 = [];
                var strArr = [];
                $.each(data.data,function () {
                    arr.push('<option value="'+this.certType+'">'+this.certTypeName+'</option>');
                    arr1.push('<option value="'+this.certTypeName+'">'+this.certTypeName+'</option>');
                    strArr.push('<div class="everyType" value="'+this.id+'"><span>'+this.certTypeName+'</span><i class="glyphicon glyphicon-remove red delType"></i></div>');
                });
                listLen = data.data.length;
                $('#certUpdateModal').find('.certType').html(arr.join(''));
                $('.searchItems .certTypeName').html('<option value="">==请选择==</option>'+arr1.join(''));
                $('#certTypeModal .typeList').html(strArr.join(''));
                if(callback){
                    callback();
                }
            }
        })
    }
    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height() - 50 - 30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }
});
