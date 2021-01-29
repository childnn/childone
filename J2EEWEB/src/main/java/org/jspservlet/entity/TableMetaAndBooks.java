package org.jspservlet.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/1 13:05
 */
@Data
public class TableMetaAndBooks {

    private List<TableMetaData> tableMetaDatum;

    private List<Map<String, Object>> books;

}
