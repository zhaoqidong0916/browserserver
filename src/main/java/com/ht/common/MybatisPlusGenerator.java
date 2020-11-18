//package com.ht.common;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Method
// * @Author yakun.shi
// * @Description 代码生成器
// * @Return
// * @Date 2019/11/7 11:20
// */
//public class MybatisPlusGenerator {
//    public static void main(String[] args) {
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        //这里写你自己的java目录
//
//        gc.setOutputDir("D:\\haitai\\poc\\browserserver\\src\\main\\java");
//
//        //是否覆盖
//        gc.setFileOverride(false);
//        gc.setActiveRecord(true);
//        // XML 二级缓存
//        gc.setEnableCache(false);
//        // XML ResultMap
//        gc.setBaseResultMap(true);
//        // XML columList
//        gc.setBaseColumnList(true);
//        gc.setAuthor("yakun.shi");
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//            // 自定义数据库表字段类型转换【可选】
//            @Override
//            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                return super.processTypeConvert(globalConfig,fieldType);
//            }
//        });
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        dsc.setUrl("jdbc:mysql://localhost:3306/browser?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false");
//        mpg.setDataSource(dsc);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
////        strategy.setTablePrefix(new String[]{""});// 此处可以修改为您的表前缀
//        //需要生成的表
//        strategy.setInclude(new String[]{"upgrade_count_log"});
//
//        //表名生成策略
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        mpg.setStrategy(strategy);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent(null);
//        pc.setEntity("com.ht.common.entity");
//        pc.setMapper("com.ht.common.dao");
//        pc.setXml("com.ht.common.mapper");
//        pc.setService("com.ht.common.service");
//        pc.setServiceImpl("com.ht.common.service.impl");
//        pc.setController("com.ht.common.controller");
//        mpg.setPackageInfo(pc);
//
//        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//        mpg.setCfg(cfg);
//
//        // 执行生成
//        mpg.execute();
//
//        // 打印注入设置
//        System.err.println(mpg.getCfg().getMap().get("abc"));
//    }
//}
