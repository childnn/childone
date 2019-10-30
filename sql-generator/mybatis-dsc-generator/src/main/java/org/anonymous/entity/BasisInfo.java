package org.anonymous.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasisInfo implements Serializable {
    private static final long serialVersionUID = 123123L;

    private String project;

    private String author;

    private String version;

    private String dbUrl;

    private String dbName;

    private String dbPassword;

    private String database;

    private String table;

    private String entityName;

    private String lowerEntityName;

    private String objectName;

    private String entityComment;

    private String createTime;

    private String agile;

    private String agileValues;

    private String entityUrl;

    private String entityVoUrl;

    private String daoUrl;

    private String mapperUrl;

    private String serviceUrl;

    private String serviceImplUrl;

    private String abstractControllerUrl;

    private String controllerUrl;

    private String commandUrl;

    private String swaggerConfigUrl;

    private String idType;

    private String idJdbcType;

    private List<PropertyInfo> cis;

    private String isSwagger = "true";

    private Set<String> pkgs = new HashSet<String>();

    public BasisInfo(String author, String version, String dbUrl, String dbName, String dbPassword,
                     String database, String project, String isSwagger) {
        this.project = "xq-cloud";
        this.author = author;
        this.version = version;
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbPassword = dbPassword;
        this.database = database;
        this.createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.agile = System.currentTimeMillis() + "";
        this.entityUrl = "com.xq.live." + project + ".model";
        this.daoUrl = "com.xq.live." + project + ".dao";
        this.mapperUrl = "com.xq.live." + project + ".mapper.xml";
        this.serviceUrl = "com.xq.live." + project + ".service";
        this.serviceImplUrl = "com.xq.live." + project + ".service.impl";
        this.controllerUrl = "com.xq.live." + project + ".controller";
        this.entityVoUrl = "com.xq.live.spi." + project + ".vo";
        this.commandUrl = "com.xq.live.spi." + project + ".command";
        this.abstractControllerUrl = "com.xq.live.spi." + project + ".controller";
        this.swaggerConfigUrl = controllerUrl.substring(0, controllerUrl.lastIndexOf(".")) + ".config";
        this.isSwagger = isSwagger;
    }
}
