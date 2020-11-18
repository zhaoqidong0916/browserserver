
var centerChart = echarts.init(document.getElementById('center-div'));
var leftChart = echarts.init(document.getElementById('bottom-left-chart'));
var rightChart = echarts.init(document.getElementById('bottom-right-chart'));
$(function () {
    var timer = null;
    var stepTimer = 2000;
    staNum(0);
    staNum(1);
    staNum(2);
    staNum(3);

    function staNum(type){
        $.ajax({
            type: "get",
            url: urls.staNum + type,
            dataType: "json",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                if (data.result == '200') {
                    if(data.data.count){
                        $('.flex-container .itemNum').eq(type).text(data.data.count);
                    }else{
                        $('.flex-container .itemNum').eq(type).text(0);
                    }
                    if(data.data.time) {
                        $('.flex-container .itemTime').eq(type).find('p').eq(1).text(data.data.time);
                    }else{
                        $('.flex-container .itemTime').eq(type).find('p').eq(1).text('--');
                    }
                }else{
                    $('.flex-container .itemNum').eq(type).text(0);
                    $('.flex-container .itemTime').eq(type).find('p').eq(1).text('--');
                }
            }
        });
    }

    var lineObj = {
        color:new echarts.graphic.LinearGradient(0,0,0,1,[{
            offset:0,
            color:'#6FA5FB'
        },{
            offset:1,
            color:'#fff'
        }],false)
    };
    var centerOption = {
        title:{
            text:'客户活跃度',
            textStyle:{
                color:'#8798AD',
                fontWeight:400,
                fontSize:16
            }
        },
        tooltip: {
            trigger: 'axis',
            position: function (pt) {
                return [pt[0]-70, '10%'];
            },
            formatter: '时间 {b} <br /> 值  {c}'
        },
        legend: {
            icon:'none',
            data: [{name:'日',textStyle:{color:'#2477F9'}},{ name:'月',textStyle:{color:'#666'}},{ name:'年',textStyle:{color:'#666'}}],
            right:40,
            textStyle:{
                fontSize:14
            }
        },
        grid:{
            left:60,
            right:60,
            bottom:40,
            top:40
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name:'日',
            data: [],
            type: 'line',
            areaStyle: lineObj,
            color:'#2477F9'
        },{
            name:'月',
            data: [],
            type: 'line',
            areaStyle: lineObj,
            color:'#2477F9'
        },{
            name:'年',
            data: [],
            type: 'line',
            areaStyle: lineObj,
            color:'#2477F9'
        }
        ]
    };
    getClientActive(0,function (d) {
        centerChart.clear();
        centerOption.series[0].data = d;
        centerOption.series[1].data = [];
        centerOption.series[2].data = [];
        centerChart.setOption(centerOption);
    });
    function getClientActive(type,callback){
        $.ajax({
            type: "get",
            url: urls.clientActive + type,
            dataType: "json",
            contentType: 'application/json',
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                if (data.result == '200') {
                    callback(data.data);
                }
            }
        })
    }




    var isFirstUnSelect = function(selected) {
        var unSelectedCount = 0;
        for ( name in selected) {
            if (!selected.hasOwnProperty(name)) {
                continue;
            }
            if (selected[name] == false) {
                ++unSelectedCount;
            }
        }
        return unSelectedCount==1;
    };
    var isAllUnSelected = function(selected) {
        var selectedCount = 0;
        for ( name in selected) {
            if (!selected.hasOwnProperty(name)) { continue; }
            // 所有 selected Object 里面 true 代表 selected， false 代表 unselected
            if (selected[name] == true) { ++selectedCount; }
        }
        return selectedCount==0;
    };
    centerChart.on('legendselectchanged', function(obj) {
        var selected = obj.selected;
        var legend = obj.name; // 使用 legendToggleSelect Action 会重新触发 legendselectchanged Event，导致本函数重复运行 // 使得 无 selected 对象
        if (selected != undefined) {
            if(legend=="日"){
                getClientActive(0,function (d) {
                    centerChart.clear();
                    centerOption.xAxis.data = ['8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00'];
                    centerOption.legend.data=[{name:'日',textStyle:{color:'#2477F9'}},{ name:'月',textStyle:{color:'#666'}},{ name:'年',textStyle:{color:'#666'}}];
                    centerOption.series[0].data = d;
                    centerOption.series[1].data = [];
                    centerOption.series[2].data = [];
                    centerChart.setOption(centerOption);
                });
            }else if(legend=="月"){
                getClientActive(1,function (d) {
                    centerChart.clear();
                    centerOption.xAxis.data = d.xLine;
                    centerOption.legend.data=[{name:'日',textStyle:{color:'#666'}},{ name:'月',textStyle:{color:'#2477F9'}},{ name:'年',textStyle:{color:'#666'}}];
                    centerOption.series[1].data = d.yLine;
                    centerOption.series[0].data = [];
                    centerOption.series[2].data = [];
                    centerChart.setOption(centerOption);
                });
            } else if(legend=="年"){
                getClientActive(2,function (d) {
                    centerChart.clear();
                    centerOption.xAxis.data =['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
                    centerOption.legend.data=[{name:'日',textStyle:{color:'#666'}},{ name:'月',textStyle:{color:'#666'}},{ name:'年',textStyle:{color:'#2477F9'}}];
                    centerOption.series[2].data = d;
                    centerOption.series[1].data = [];
                    centerOption.series[0].data = [];
                    centerChart.setOption(centerOption);
                });
            }
            if (isFirstUnSelect(selected)) {
                triggerAction('legendToggleSelect', selected);
            } else if (isAllUnSelected(selected)) {
                triggerAction('legendSelect', selected);
            }
        }
    });


    var leftOption = {
        title:{
            text:'首页菜单访问次数',
            textStyle:{
                color:'#8798AD',
                fontWeight:400,
                fontSize:16
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid:{
            left:60,
            right:40,
            bottom:60,
            top:40
        },
        xAxis: {
            axisLabel :{
                interval:0,
                rotate:'30',
                formatter: function(params) {
                    var newParamsName = '';
                    var paramsNameNumber = params.length;

                    var reg=/^[\u4E00-\u9FA5]+$/;
                    if(!reg.test(params)){
                        var provideNumber = 6;
                    }else {
                        var provideNumber = 5;
                    }
                    if(paramsNameNumber > 6){
                        newParamsName += params.slice(0,provideNumber)+'...';
                    }else{
                        newParamsName += params;
                    }
                    return newParamsName;
                }
            },
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: {
            barWidth:24,
            data: [],
            type: 'bar',
            itemStyle: {
                color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: '#8ECFFF'},
                        {offset: 1, color: '#2477F9'}
                    ]
                )
            }
        }
    };

    $.ajax({
        type: "get",
        url: urls.homepageVisit + '0',
        dataType: "json",
        contentType: 'application/json',
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if (data.result == '200') {
                var arr = data.data.xLine;
                var dataArr = data.data.yLine;
                if(arr.length > 8){
                    leftOption.xAxis.data = arr.slice(0,8);//横坐标
                    leftOption.series.data = dataArr.slice(0,8);
                }else{
                    leftOption.xAxis.data = arr;//横坐标
                    leftOption.series.data = dataArr;
                }
                leftChart.setOption(leftOption);
                if(timer){
                    clearInterval(timer);
                }
                if(arr.length > 8){
                    runTimer(leftOption,leftChart,arr.length,arr,dataArr);
                }
            }
        }
    });

    function runTimer(option,chart,len,arr,data){
        var i = 8;

        if(timer){
            clearInterval(timer);
        }
        timer = setInterval(function () {
            var arr0 = option.xAxis.data;
            var data0 = option.series.data;
            if (i < len) {
                arr0.shift();
                arr0.push(arr[i]);
                data0.shift();
                data0.push(data[i]);
                i++;
            }else {
                i = 0;
                arr0.shift();
                arr0.push(arr[i]);
                data0.shift();
                data0.push(data[i]);
            }
            chart.setOption(option);
        },stepTimer);
    }
    var rightOption = {
        title:{
            text:'首页菜单前五占比',
                textStyle:{
                color:'#8798AD',
                    fontWeight:400,
                    fontSize:16
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'horizontal',
            left: 20,
            bottom: 15,
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                type: 'pie',
                radius: ['35%', '55%'],
                avoidLabelOverlap: false,
                center:['50%','44%'],
                color: ['#2477F9', '#F98D24', '#FBBF60','#24E1F9','#29E350'],
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: false
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            }
        ]
    };
    $.ajax({
        type: "get",
        url: urls.homepageVisit + '1',
        dataType: "json",
        contentType: 'application/json',
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if(data.result == 200) {
                var arr = [];
                var dataArr = [];
                $.each(data.data, function () {
                    arr.push(this.name);
                    dataArr.push({name:this.name,value:this.value});
                })
                rightOption.legend.data = arr;
                rightOption.series[0].data = dataArr;
                rightChart.setOption(rightOption);
            }
        }
    })

})