package com.global.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yanghanjin
 * @Description:
 * @Date 2019/4/10
 */
public class MybatisPlusGeneratorUtils {

    private static final String javaGenerator = "F:\\yhjdoc\\server\\globalpro\\src\\main\\java";
    private static final String xmlGenerator = "F:\\yhjdoc\\server\\globalpro\\src\\main\\resources\\mapper\\";

    /***
     * 主方法  自动生成
     * @param tableName
     */
    public static void generator(String tableName) {
        AutoGenerator mpg = new AutoGenerator();
        //自定义数据
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("modules", "system");
                this.setMap(map);
            }
        };
        //全局配置
        globalConfig(mpg);
        //数据源 策略配置
        datasourceConfigure(mpg, tableName);
        //包配置
        packageConfigure(mpg);
        //自定义静态资源生成
        staticReosurceConfigure(mpg, cfg);
        //模板文件路径设置
        modulTmplateConfigure(mpg);
        //执行生成
        mpg.execute();
    }

    /***
     * 全局配置
     * @param mpg
     */
    public static void globalConfig(AutoGenerator mpg) {
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("yanghanjin");
        gc.setOutputDir(javaGenerator);
        gc.setFileOverride(true); //是否覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        mpg.setGlobalConfig(gc);
    }

    /**
     * 数据源/策略配置
     *
     * @param mpg
     * @param tableName
     */
    public static void datasourceConfigure(AutoGenerator mpg, String tableName) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
       /* dsc.setDbType(DbType.ORACLE);*/
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123");
        dsc.setUrl("jdbc:mysql://localhost:3306/globalpro?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"tb_", "tsys_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{tableName}); // 需要生成的表
        strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);
    }

    /***
     * 配置包名
     * @param mpg
     */
    public static void packageConfigure(AutoGenerator mpg) {
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.global.api");
        pc.setController("controller.system");
        pc.setEntity("entity.system");
        pc.setMapper("dao.system");
        pc.setService("service.system");
        pc.setServiceImpl("service.system.impl");
        mpg.setPackageInfo(pc);
    }

    /***
     * 自定义静态资源生成
     * @param mpg
     */
    public static void staticReosurceConfigure(AutoGenerator mpg, InjectionConfig cfg) {
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 调整 xml 生成目录
        focList.add(new FileOutConfig("/source/mapperTemp.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return xmlGenerator + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }

    /***
     * 模板文件路径设置
     * @param mpg
     */
    public static void modulTmplateConfigure(AutoGenerator mpg) {
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/source/controllerTemp.java.vm");
        tc.setMapper("/source/daoTemp.java.vm");
        tc.setXml(null);
        tc.setService("/source/serviceTemp.java.vm");
        tc.setServiceImpl("/source/serviceImplTemp.java.vm");
        tc.setEntity("/source/entity.java.vm");
        mpg.setTemplate(tc);
    }

    /***
     * 需要修改的配置：
     * 1、generator 方法中 map.put("modules", "system");  value值修改成当前模块的大类
     * 2、globalConfig 方法中的 author
     * 3、datasourceConfigure 方法中的数据库链接
     * 4、packageConfigure 方法中的包名
     * @param args
     */
    public static void main(String[] args) {
        new MybatisPlusGeneratorUtils().generator("sys_role");
    }

}
