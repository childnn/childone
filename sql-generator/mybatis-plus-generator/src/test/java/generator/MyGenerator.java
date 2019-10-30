package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MiaoOne
 * 2019/8/1 0:18
 * 实现成功!
 * 说明：
 * 1. 修改代码生成路径 {@link #DIR_PATH}
 * 2. 修改作者 {@link GlobalConfig#getAuthor()}
 * 3. 修改包名
 * 4. 修改数据库连接信: url, username, password
 * 5. 指定需要生成实体类的表名
 */
public class MyGenerator {

    // 代码生成路径: 这里如果用 单反斜杠, 会报错误提示, 但是不影响结果
    private final static String DIR_PATH = "sql-generator/mybatis-plus-generator\\src\\main\\java";
    private final static String AUTHOR = "MiaoOne";

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //generator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(DIR_PATH);
        gc.setAuthor(AUTHOR); // 作者注释
        gc.setFileOverride(true);  // 是否覆盖
        /**
         * 实体类实现 {@link Model} 抽象类, 直接使用其中基本的 CRUD 方法
         */
        gc.setActiveRecord(true); // 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columnList
        gc.setOpen(true); // 是否打开生成目录： 代码生成完毕, 自动打开目录

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sMapper");
        gc.setServiceName("%sService"); // 如果不指定 service 接口名格式, 默认是 "I%sService" 形式
//        gc.setEntityName("%s");
        // gc.setServiceImplName("%sServiceImpl");
        // gc.setControllerName("%sController");

        generator.setGlobalConfig(gc); // 封装


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert()/*{
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        }*/);

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        // dsc.setUsername("feiyu_db");
//        dsc.setPassword("root");
        dsc.setPassword("lipeng632930871");
        // dsc.setPassword("NSagd$wynb1^$");
        // dsc.setUrl("jdbc:mysql://rm-8vbk210w6ef0q7ne3qo.mysql.zhangbei.rds.aliyuncs.com:3306/global_voyage_center?characterEncoding=utf8&useSSL=false");
        dsc.setUrl("jdbc:mysql://120.78.70.187:3306/elephant_car_center?characterEncoding=utf8&useSSL=false");
//        dsc.setUrl("jdbc:mysql://120.78.70.187:3306/elephant_car_center?characterEncoding=utf8&useSSL=false");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/global_voyage_center?characterEncoding=utf8&useSSL=false");

        generator.setDataSource(dsc); // 封装

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "tb_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略

        //--------------------------------------------------
        // include 属性就是用来设置待生成实体类的表名
        //--------------------------------------------------

        strategy.setInclude("cash_apply_log");
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        strategy.setEntityBuilderModel(true); // set 方法链式编程
        strategy.setEntityLombokModel(true); // lombok

        generator.setStrategy(strategy); // 封装

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.xq.live.elephantCar");
//        pc.setModuleName("warrior");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("mapper");

        generator.setPackageInfo(pc); // 封装

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();
/*        focList.add(new FileOutConfig("/template/list.jsp.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "D://my_" + tableInfo.getEntityName() + ".jsp";
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);*/

        // 调整 xml 生成目录演示
/*        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return dirPath + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        */
//        generator.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
/*        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        generator.setTemplate(tc);*/

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // generator.setTemplate(tc);

        // 执行生成
        generator.execute();

        // 打印注入设置【可无】
//        System.err.println(generator.getCfg().getMap().get("abc"));
    }

}
