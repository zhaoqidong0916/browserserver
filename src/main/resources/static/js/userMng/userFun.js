$(function(){
    var hash = window.location.hash.slice(1);
    var urlInfo = JSON.parse(window.localStorage.getItem('urlInfo'));
    var userId = window.localStorage.getItem('userId');
    $('.searchTitle .searchText').text(urlInfo[hash].parentName+'>>'+urlInfo[hash].urlName);
    var h = 0;
    resize();
    bindEvent();
    var $listGrid=$('#detailGrid');
    getRoleList();
    var c = $("#popUser");
     //js实现对特殊字符的处理
    inuputReader();
    window.operateEvents = {
        'click .delOpt': function (e, value, row) {
        	var id=row.id;
        	//当前登录人删除自己，不允许
        	if(id==userId){
        		 bootbox.alert("当前用户已被使用！");
        	}else{
        		delOptFun(urls.userDelete+id, $listGrid);
        	}
        },
        'click .scanUser': function (e, value, row, index) {
            detailOptFun(urls.userDetail+row.id, function (d) {
                paintContent(c,'scan',d,'查看用户详情');
                c.find('.password,select.roleName').hide();
                c.find('input.roleName').show();
                c.find('input[name=sex][value="' + d.sex + '"]').prop("checked", true);
            });
        },
        'click .editUser': function (e, value, row) {
            detailOptFun(urls.userDetail+row.id, function (d) {
                c.find('.password,input.roleName').hide();
                paintContent(c,'edit',d,'修改用户');
                c.find('.loginName').attr('disabled',true);
                c.find('select.roleName').show().val(d.roleId);
                c.find('.userId').val(row.id);
                c.find('input[name=sex][value="' + d.sex + '"]').prop("checked", true);
            });
        },
        'click .userStatus':function (e, value, row) {
            $("#ajaxRes").modal('show');
            $("#ajaxRes .modal-title").text('添加权限');
            $('#ajaxRes .id').val(row.id);
            if(row.checkStatus == 0){
                $('#ajaxRes .status').val(1);
                $('#ajaxRes .authTips').text('确认禁用该用户？');
            }else{
                $('#ajaxRes .status').val(0);
                $('#ajaxRes .authTips').text('确认启用该用户？');
            }
        }
    };
    $listGrid.bootstrapTable({
        url: urls.userList,
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign: 'left',
        buttonsAlign: 'right',
        sortable: 'false',
        smartDisplay: false,
        contentType: 'application/json',
        dataType: 'json',
        ajaxOptions: {
            headers: {"Authorization": token}
        },
        //传递参数（*）
         queryParams: queryParams,//传递参数（*）
        singleSelect: true,
        offset: 10, // 页面数据条数
        pageNumber: 1, // 首页页码
        pageList: [10, 20, 30], // 设置页面可以显示的数据条数
        clickToSelect: true,
        sidePagination: 'server', // 设置为服务器端分页
        columns: [{
            title: '序号',
            width: 60,
            formatter: function (value, row, index) {
                var pageSize = $listGrid.bootstrapTable('getOptions').pageSize;
                var pageNumber = $listGrid.bootstrapTable('getOptions').pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'loginName',
            title: '账号',
            width: 80,
            formatter: paramsMatter
        }, {
            field: 'username',
            title: '用户名',
            width: 80,
            formatter: paramsMatter
        }, {
            field: 'roleName',
            title: '角色',
            width: 140,
            formatter: paramsMatter
        }, {
            field: 'sex',
            title: '性别',
            width: 60,
            formatter: function (idx, row) {
                if (row.sex == 0) {
                    return '女';
                } else {
                    return '男';
                }
            }
        }, {
            field: 'createTime',
            title: '创建时间',
            width: 180,
            formatter: paramsMatter
        }, {
            field: 'operate',
            title: '操作',
            width: 220,
            events: operateEvents,
            formatter: function (idx, row) {
                var str = "";
                if (row.checkStatus == 0) {
                    str = [
                        '<i title="编辑" class="editUser glyphicon glyphicon-edit mr20 orange"></i>',
                        '<i title="详情" class="scanUser glyphicon glyphicon-eye-open mr20"></i>',
                        '<i title="删除" class="delOpt glyphicon glyphicon-trash red"></i>'
                        // '<i title="禁用" style="color: #0079FE;" class="userStatus glyphicon glyphicon-minus-sign"></i>'
                    ].join('');
                } else {
                    str = [
                        '<i title="编辑" class="editUser glyphicon glyphicon-edit mr20 orange"></i>',
                        '<i title="详情" class="scanUser glyphicon glyphicon-eye-open mr20"></i>',
                        '<i title="删除" class="delOpt glyphicon glyphicon-trash red"></i>'
                        // '<i title="启用" style="color: green;" class="userStatus glyphicon glyphicon-play-circle"></i>'
                    ].join('');
                }
                return str;
            }
        }],
        //返回的数据进行数据结构转换
        responseHandler: function (result) {
            if (result.result == '300') {
                window.location.href=loginPage;
            }
            var data = result.data.records;
            if (!data || data.length == 0) {
                return {
                    total: 0,
                    rows: []
                }
            }
            return {
                total: result.data.total,
                rows: result.data.records
            }
        }
    });

    function queryParams(params) {
        var temp = {
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            loginName:$(".loginName").val(),
            username:$(".username").val(),
            userId:userId,
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        $.extend(temp,getRequestHeader($('.searchItems')));
        return temp;
    }

    function bindEvent() {
        $(".js_newUsers").click(function(){
            c.modal('show');
            resetForm(c);
            c.find('.userId').val('');
            c.find('.password,select.roleName').show();
            c.find('.modal-title').text('新建用户');
            c.find('.submitUserForm').show();
            c.find('.roleDetail,input.roleName').hide();
        });

        //新建用户确定按钮
        $(".submitUserForm").on("click",function () {
            var validator = c.find('form').validate();
            if(!validator.form()){
                return false;
            };
            var data = {};
            $('#popUser input').each(function () {
                var name = $(this).attr('name');
                if(name == 'sex'){
                    data[name] = $('#popUser input[name=sex]:checked').val();
                }else{
                    if($(this).val()){
	                   //对密码输入进行md5加密其他不需要加密
	                    if(name=='password'){
		                  data[name]=hex_md5($(this).val());
	                 }else{
		                 data[name] = $(this).val();
	                 }
                        
                    }
                }
            })
            data.roleId =$('#popUser .roleName').val();
            data.roleName =$('#popUser .roleName option:selected').text();
            data = JSON.stringify(data);
            if($('#popUser .userId').val()){//修改
                $.ajax({
                    url: urls.userUpdate,
                    type: "post",
                    contentType:'application/json',
                    beforeSend: function(request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function(data){
                        if(data.result==200){
                            bootbox.alert('修改成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                        }else if(data.result==300){
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        }else{
                            bootbox.alert(data.message);
                        }
                        $('#popUser').modal('hide');
                    }
                })
            }else{
                $.ajax({
                    url: urls.userRegister,
                    type: "post",
                    contentType:'application/json',
                    beforeSend: function(request) {
                        request.setRequestHeader("Authorization", token);
                    },
                    data: data,
                    dataType: "json",
                    success: function(data){
                        if(data.result==200){
                            bootbox.alert('新用户注册成功!',function () {
                                $listGrid.bootstrapTable('refresh');
                            });
                        }else if(data.result==300){
                            bootbox.alert('未登录!',function () {
                                window.location.href=loginPage;
                            });
                        }else{
                            bootbox.alert(data.message);
                        }
                        $('#popUser').modal('hide');
                    },error:function () {
                        bootbox.alert('服务出错!');
                        $('#popUser').modal('hide');
                    }
                })
            }
        });

    }

    //重置
    $(".js_resetQuery").click(function(){
        resetSearchItems();
        $(".query").trigger('click');
    });
    /*查询*/
    $(".query").click(function(){
        var opt = {
            pageNumber: 1, // 首页页码
            silent: true
        };
        $listGrid.bootstrapTable('refresh',opt);
    });
    function getRoleList() {
        $.ajax({
            url: urls.userRoleList,
            type: "get",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            dataType: "json",
            success: function (data) {
                var str = '';
                $.each(data.data,function () {
                    str += '<option value="'+this.id+'">'+this.roleName+'</option>'
                });
                c.find('.roleName').html(str);
            }
        })
    }

    //计算高度列表
    function resize() {
        $('.main').height($('#ui-view').height());
        $('.contentBox').height($('.main').height()-50-30);
        $('.content').height($('.contentBox').height() - $('.searchItems').height() - 15 * 2);
        h = $('.content').outerHeight();
    }
});
