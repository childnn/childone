package org.anonymous.test;

import org.anonymous.entity.BasisInfo;
import org.anonymous.util.EntityInfoUtil;
import org.anonymous.util.Generator;
import org.anonymous.util.MySqlToJavaUtil;

import java.sql.SQLException;

/**
 * Copyright: Copyright (c) 2019
 *
 * <p>说明： 自动生成工具</P>
 * <p>源码地址：https://gitee.com/flying-cattle/mybatis-dsc-generator</P>
 */
public class MyGenerator {
    // 基础信息：作者、版本
    public static final String AUTHOR = "xxx";
    public static final String VERSION = "V1.0";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String URL = "jdbc:mysql://120.78.70.187:3306/elephant_car_center?useSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";
    public static final String NAME = "root";
    public static final String PASS = "lipeng632930871";
    public static final String DATABASE = "elephant_car_center";

    public static final String TABLE = "cash_apply_log";
    public static final String CLASSCOMMENT = "提现进度日志";

    // 路径信息，分开路径方便聚合工程项目，微服务项目
    public static final String projectName = "elephant";
    //是否是Swagger配置
    public static final String IS_SWAGGER = "true";

    private static final String OUTPUT_URL = "E:\\";// 生成文件存放位置


    public static void main(String[] args) {
        BasisInfo bi = new BasisInfo(AUTHOR, VERSION, URL, NAME, PASS, DATABASE, projectName, IS_SWAGGER);
        bi.setTable(TABLE);
        bi.setEntityName(MySqlToJavaUtil.getClassName(TABLE));
        bi.setLowerEntityName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
        bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
        bi.setEntityComment(CLASSCOMMENT);
        try {
            bi = EntityInfoUtil.getInfo(bi);

            //开始生成文件
            Generator.create(OUTPUT_URL, bi);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
