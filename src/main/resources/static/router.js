$(function(){

    router.start({
        view: '#ui-view',
        errorTemplateId: '#error', // 可选
        router: {
            'homePage': {//
                templateUrl: 'home/homePage.html',
                controller: '../js/home/homePage.js',
                styles:'../css/homePage.css',
                animate:''
            },
            'compatibleConfig': {//浏览器兼容配置
                templateUrl: 'tacticsMng/compatibleConfig.html',
                controller: '../js/tacticsMng/compatibleConfig.js',
                animate:''
            },
            'generalConfig': {//浏览器通用配置
                templateUrl: 'tacticsMng/generalConfig.html',
                controller: '../js/tacticsMng/generalConfig.js',
                styles:'',
                animate:''
            },
            'watermarkConfig': {//浏览器水印配置
                templateUrl: 'tacticsMng/watermarkConfig.html',
                controller: '../js/tacticsMng/watermarkConfig.js',
                animate:''
            },
            'browserActionLog': {//浏览器行为日志
                templateUrl: 'logMng/browserActionLog.html',
                controller: '../js/logMng/browserActionLog.js',
                styles:'',
                animate:''
            },
            'systemLog': {//系统日志
                templateUrl: 'logMng/systemLog.html',
                controller: '../js/logMng/systemLog.js',
                styles:'',
                animate:''
            },
            'operateLog': {//系统日志
                templateUrl: 'logMng/operateLog.html',
                controller: '../js/logMng/operateLog.js',
                styles:'',
                animate:''
            },
            'userFun': {//用户功能
                templateUrl: 'userMng/userFun.html',
                controller: '../js/userMng/userFun.js',
                styles:'',
                animate:''
            },
            'menu': {//菜单功能
                templateUrl: 'userMng/menu.html',
                controller: '../js/userMng/menu.js',
                styles:'',
                animate:''
            },
            'role': {//角色功能
                templateUrl: 'userMng/role.html',
                controller: '../js/userMng/role.js',
                styles:'',
                animate:''
            },
            'certificateTypeMng': {//证书类型管理
                templateUrl: 'typeMng/certificateTypeMng.html',
                controller: '../js/typeMng/certificateTypeMng.js',
                styles:'',
                animate:''
            },
            'controlTypeMng': {//控件类型管理
                templateUrl: 'typeMng/controlTypeMng.html',
                controller: '../js/typeMng/controlTypeMng.js',
                styles:'',
                animate:''
            },
            'browserUpdateMng': {//
                templateUrl: 'updateFun/browserUpdateMng.html',
                controller: '../js/updateFun/browserUpdateMng.js',
                styles:'',
                animate:''
            },
            'certificateUpdateMng': {//
                templateUrl: 'updateFun/certificateUpdateMng.html',
                controller: '../js/updateFun/certificateUpdateMng.js',
                styles:'',
                animate:''
            },
            'controlUpdateMng': {//
                templateUrl: 'updateFun/controlUpdateMng.html',
                controller: '../js/updateFun/controlUpdateMng.js',
                styles:'',
                animate:''
            },
            'pluginMng': {//
                templateUrl: 'safeMng/pluginMng.html',
                controller: '../js/safeMng/pluginMng.js',
                styles:'',
                animate:''
            },
            'visitMng': {//
                templateUrl: 'safeMng/visitMng.html',
                controller: '../js/safeMng/visitMng.js',
                styles:'',
                animate:''
            },
            'browserUrl': {//浏览器九宫格
                templateUrl: 'applicationMng/browserUrl.html',
                controller: '../js/applicationMng/browserUrl.js',
                styles:'',
                animate:''
            },
            'browserResource': {//背景管理
                templateUrl: 'applicationMng/browserResource.html',
                controller: '../js/applicationMng/browserResource.js',
                styles:'',
                animate:''
            },
            'countRateLimiter': {// 限流管理--流量管控
                templateUrl: 'rateLimiterMng/countRateLimiter.html',
                controller: '../js/rateLimiterMng/countRateLimiter.js',
                styles:'',
                animate:''
            },
            'errorLog': {//错误日志
                templateUrl: 'logMng/errorLog.html',
                controller: '../js/logMng/errorLog.js',
                styles:'',
                animate:''
            },
            'errorJsLog': {//JS错误统计
                templateUrl: 'logMng/errorJsLog.html',
                controller: '../js/logMng/errorJsLog.js',
                styles:'',
                animate:''
            },
            'browserActionLog2': {//新浏览器行为日志统计
                templateUrl: 'logMng/browserActionLog2.html',
                controller: '../js/logMng/browserActionLog2.js',
                styles:'',
                animate:''
            },
            'defaults': '' //默认路由
        }
    });

});
