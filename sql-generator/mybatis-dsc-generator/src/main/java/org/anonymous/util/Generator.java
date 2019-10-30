package org.anonymous.util;

import org.anonymous.entity.BasisInfo;
import org.anonymous.entity.PropertyInfo;
import org.anonymous.entity.ResultJson;

import java.util.List;

public class Generator {
    //路径信息
    public static final String ENTITY = "entity";
    public static final String DAO = "dao";
    public static final String DAO_IMPL = "daoImpl";
    public static final String SERVICE = "service";
    public static final String SERVICE_IMPL = "serviceImpl";
    public static final String COMMAND = "command";
    public static final String CONTROLLER = "controller";
    public static final String CONTROLLER_INTERFACE = "controllerInterface";
    public static final String SWAGGER_CONFIG = "swaggerConfig";


    public static void create(String url, BasisInfo bi) {
        //开始生成文件
        String aa1 = createEntity(url, bi).toString();
        String aa2 = createEntityVo(url, bi).toString();
        String aa3 = createDao(url, bi).toString();
        String aa4 = createDaoImpl(url, bi).toString();
        // 是否创建swagger配置文件
        String aa6 = Generator.createService(url, bi).toString();
        String aa7 = Generator.createServiceImpl(url, bi).toString();
        String aa8 = Generator.createControllerInterface(url, bi).toString();
        String aa9 = Generator.createController(url, bi).toString();
        System.out.println(aa1);
        System.out.println(aa2);
        System.out.println(aa3);
        System.out.println(aa4);
        System.out.println(aa6);
        System.out.println(aa7);
        System.out.println(aa8);
        System.out.println(aa9);
    }

    //①创建实体类
    public static ResultJson createEntity(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityUrl(), bi.getEntityName(), ENTITY);
        return FreemarkerUtil.createFile(bi, "entity.ftl", fileUrl);
    }

    //①创建实体Vo类
    public static ResultJson createEntityVo(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityVoUrl(), bi.getEntityName() + "Vo", ENTITY);
        return FreemarkerUtil.createFile(bi, "entityVo.ftl", fileUrl);
    }

    //②创建DAO
    public static ResultJson createDao(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getDaoUrl(), bi.getEntityName(), DAO);
        return FreemarkerUtil.createFile(bi, "dao.ftl", fileUrl);
    }

    //③创建mapper配置文件
    public static ResultJson createDaoImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getMapperUrl(), bi.getEntityName(), DAO_IMPL);
        List<PropertyInfo> list = bi.getCis();
        String agile = "";
        String agileValues = "";
        for (PropertyInfo propertyInfo : list) {
            agile = agile + propertyInfo.getColumn() + ", ";
            agileValues = agileValues + "#{item." + propertyInfo.getProperty() + "},";
        }
        agile = agile.substring(0, agile.length() - 2);
        agileValues = agileValues.substring(0, agileValues.length() - 1);
        bi.setAgile(agile);
        bi.setAgileValues(agileValues);
        return FreemarkerUtil.createFile(bi, "mapper.ftl", fileUrl);
    }

    //④创建SERVICE
    public static ResultJson createService(String url, BasisInfo bi) {
        createAddCommand(url, bi);
        createUpdateCommand(url, bi);
        createPageCommand(url, bi);
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceUrl(), bi.getEntityName(), SERVICE);
        return FreemarkerUtil.createFile(bi, "service.ftl", fileUrl);
    }

    //⑤创建SERVICE_IMPL
    public static ResultJson createServiceImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceImplUrl(), bi.getEntityName(), SERVICE_IMPL);
        return FreemarkerUtil.createFile(bi, "serviceImpl.ftl", fileUrl);
    }

    //创建新增请求类
    public static ResultJson createAddCommand(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getCommandUrl(), "Add" + bi.getEntityName() + "Command", COMMAND);
        return FreemarkerUtil.createFile(bi, "addCommand.ftl", fileUrl);
    }

    //创建修改请求
    public static ResultJson createUpdateCommand(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getCommandUrl(), "Update" + bi.getEntityName() + "Command", COMMAND);
        return FreemarkerUtil.createFile(bi, "updateCommand.ftl", fileUrl);
    }

    //创建分页请求
    public static ResultJson createPageCommand(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getCommandUrl(), "Page" + bi.getEntityName() + "Command", COMMAND);
        return FreemarkerUtil.createFile(bi, "pageCommand.ftl", fileUrl);
    }


    //创建controller
    public static ResultJson createController(String url, BasisInfo bi) {
        createControllerInterface(url, bi);
        String fileUrl = getGeneratorFileUrl(url, bi.getControllerUrl(), bi.getEntityName(), CONTROLLER);
        return FreemarkerUtil.createFile(bi, "controller.ftl", fileUrl);
    }

    //创建ControllerInterface
    public static ResultJson createControllerInterface(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getAbstractControllerUrl(), bi.getEntityName(), CONTROLLER_INTERFACE);
        return FreemarkerUtil.createFile(bi, "controllerInterface.ftl", fileUrl);
    }


    //生成文件路径和名字
    public static String getGeneratorFileUrl(String url, String packageUrl, String entityName, String type) {
        if (type.equals("entity")) {
            return url + pageToUrl(packageUrl) + entityName + ".java";
        } else if (type.equals("dao")) {
            return url + pageToUrl(packageUrl) + entityName + "Dao.java";
        } else if (type.equals("daoImpl")) {
            return url + pageToUrl(packageUrl) + entityName + "Mapper.xml";
        } else if (type.equals("service")) {
            return url + pageToUrl(packageUrl) + entityName + "Service.java";
        } else if (type.equals("serviceImpl")) {
            return url + pageToUrl(packageUrl) + entityName + "ServiceImpl.java";
        } else if (type.equals("controller")) {
            return url + pageToUrl(packageUrl) + entityName + "Controller.java";
        } else if (type.equals("command")) {
            return url + pageToUrl(packageUrl) + entityName + ".java";
        } else if (type.equals("controllerInterface")) {
            return url + pageToUrl(packageUrl) + entityName + "ControllerInterface.java";
        }
        return null;
    }

    public static String pageToUrl(String url) {
        return url.replace(".", "/") + "/";
    }
}
