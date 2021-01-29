package org.jspservlet.entity;

import lombok.Data;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/1 12:03
 */
@Data
public class TableMetaData {
    private String columnName;
    private String columnTypeName;
    private int columnDisplaySize;
    private String columnClassName;

}
