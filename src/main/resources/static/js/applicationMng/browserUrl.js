
$(function(){
    var h = 0;
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var list;
    resize();
    getAllIcons();
    var $listGrid=$('#detailGrid');
    var c = $('#applicationModal');
  //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .editOpt': function(e, value, row, index) {
            detailOptFun(urls.applicationDetail+row.id,function (d) {
                paintContent(c,'edit',d,'编辑首页配置信息');
                c.find('.rowId').val(d.id);
                c.find('.url').val(d.urlAddress);
                c.find('.editIcon').show();
                c.find('.scanIcon').hide();
                if(d.iconUrl){
                    c.find('.iconUrl').val(d.iconUrl);
                    var arr = d.iconUrl.split('/');
                    var u = arr[arr.length-1].split('.')[0];
                    c.find('.iconsDiv .listDiv').removeClass('blueBorder').addClass('greyBorder');
                    c.find('.iconsDiv i').removeClass('blue').addClass('grey');
                    c.find('.editIcon .listDiv').each(function () {
                        if($(this).attr('dataurl').indexOf(u) >= 0){
                            $(this).removeClass('greyBorder').addClass('blueBorder');
                            $(this).find('i').removeClass('grey').addClass('blue');
                            return;
                        }
                    })
                }
            })
        },
        'click .detailOpt': function(e, value, row, index) {
            detailOptFun(urls.applicationDetail+row.id,function (d) {
                paintContent(c,'scan',d,'查看首页配置信息');
                c.find('.editIcon').hide();
                c.find('.scanIcon').show();
                if(d.iconUrl){
                    c.find('.detailIconsDiv').html('<div class="pr fl listDiv greyBorder"><img class="mr20 iconList" src = ' + d.iconUrl + ' /></div>');
                }
            })
        },
        'click .delOpt': function(e, value, row, index) {
            delOptFun(urls.applicationDelete + row.id,$listGrid);
        }
    };
    $listGrid.bootstrapTable({
        url: urls.applicationList,
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
            field: 'urlAddress',
            title: 'URL',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'urlName',
            title: '名称',
            width: 120,
            formatter:paramsMatter
        },{
            field: 'urlDescribe',
            title: '描述',
            width: 120,
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
                window.top.location.href = loginPage;
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
        c.modal('show');
        $('#applicationModal .submitUserForm').show();
        $('#applicationModal .modal-title').text('新增首页配置');
        resetForm(c);
        c.find('.rowId').val('');
        c.find('.url').val('');
        c.find('.iconUrl').val($('.listDiv').eq(0).attr('dataurl'));
        c.find('.editIcon').show();
        c.find('.scanIcon').hide();
        c.find('.iconsDiv .listDiv').removeClass('blueBorder').addClass('greyBorder');
        c.find('.iconsDiv i').removeClass('blue').addClass('grey');
        c.find('.iconsDiv .listDiv').eq(0).addClass('blueBorder');
        c.find('.iconsDiv i').eq(0).addClass('blue');
    });

    $('.addAppIconBtn').click(function () {
        $('#iconModal').modal('show');
        var filePicker = $('#iconModal').find("#filePicker");
        list = webUploadrs(filePicker,$('#iconModal'),urls.iconLoad,true,function () {
            getAllIcons();
            $('#iconModal #fileList').find('#removeClick').trigger('click');
        });
    });

    function getAllIcons() {
        $.ajax({
            url: urls.iconList,
            type: 'get',
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                if (data.result == 200) {
                    $('#applicationModal .iconsDiv').html('');
                    $('#iconModal .iconsDiv').html('');
                    var arr = data.data;
                    $.each(arr, function () {
                        $('#applicationModal .iconsDiv').append('<div dataurl="'+this.url+'" dataId="'+this.id+'" class="pr fl listDiv greyBorder"><img class="mr20 iconList" src = ' + this.url + ' /><i class="glyphicon glyphicon-ok selectIcon grey"></i></div>');
                        $('#iconModal .iconsDiv').append('<div class="pr fl listDiv greyBorder"><img class="mr20 iconList" src = ' + this.url + ' /><i data-id="' + this.id + '" class="glyphicon glyphicon-remove selectIcon grey"></i></div>');
                    })
                } else if (data.result == 300) {
                    bootbox.alert('未登录!', function () {
                        window.top.location.href = loginPage;
                    });
                } else {
                    bootbox.alert(data.message);
                }
            }
        });
    }

    $('#iconModal').on('click','.selectIcon',function () {
        $.ajax({
            url: urls.iconDelete+$(this).data('id'),
            type: 'post',
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                if(data.result == 200){
                    getAllIcons();
                }else if(data.result==300){
                    bootbox.alert('未登录!',function () {
                        window.top.location.href=loginPage;
                    });
                }else {
                    bootbox.alert(data.message);
                }
            }
        })
    })
    $('#iconModal').on('click','.listDiv',function () {
        if($(this).find('i').hasClass('grey')){
            $(this).siblings().removeClass('redBorder').addClass('greyBorder');
            $(this).siblings().find('i').removeClass('red').addClass('grey');
            $(this).find('i').removeClass('grey').addClass('red');
            $(this).removeClass('greyBorder').addClass('redBorder');
        }else{
            $(this).siblings().removeClass('redBorder').addClass('greyBorder');
            $(this).find('i').removeClass('red').addClass('grey');
            $(this).removeClass('redBorder').addClass('greyBorder');
        }

    })

    $('#applicationModal').on('click','.listDiv',function () {
        if($(this).find('i').hasClass('grey')){
            $('#applicationModal .iconUrl').val($(this).attr('dataurl'));
            $(this).siblings().removeClass('blueBorder').addClass('greyBorder');
            $(this).siblings().find('i').removeClass('blue').addClass('grey');
            $(this).find('i').removeClass('grey').addClass('blue');
            $(this).removeClass('greyBorder').addClass('blueBorder');
        }else{
            $('#applicationModal .iconUrl').val('');
            $(this).siblings().removeClass('blueBorder').addClass('greyBorder');
            $(this).find('i').removeClass('blue').addClass('grey');
            $(this).removeClass('blueBorder').addClass('greyBorder');
        }

    })

    //重置
    $(".resetBtn").click(function(){
        resetSearchItems();
        $(".searchBtn").trigger('click');
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
    $('#applicationModal .submitUserForm').on("click",function(){
        var validator = $('#applicationModal form').validate();
        if(!validator.form()){
            return false;
        }
        var data = getRequestHeader(c);
        if(c.find('.rowId').val()){
            data.id = c.find('.rowId').val();
            if(c.find('.url').val() == c.find('.urlAddress').val()){
                delete data.urlAddress;
            }
        }
        data.iconUrl = c.find('.iconUrl').val();
        if(!c.find('.iconUrl').val()){
            bootbox.alert('请选择图标！');
            return false;
        }
        data = JSON.stringify(data);
        $.ajax({
            url: c.find('.rowId').val()?urls.applicationUpdate:urls.applicationAdd,
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
