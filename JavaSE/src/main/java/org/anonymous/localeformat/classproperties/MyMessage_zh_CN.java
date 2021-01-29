package org.anonymous.localeformat.classproperties;

import java.util.ListResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/13 11:30
 */
public class MyMessage_zh_CN extends ListResourceBundle {

    /**
     * 二维数组, 外层数组的每个元素为长度为 2 的数组
     * 内层数组: 第一个元素对应 properties 文件 key(String 类型), 第二个元素对应 value(任意类型)
     */
    private static final String[][] DATA = {
            {"hello", "你好!"},
            {"name", "杰克"},
            {"msg", "你好,{0}!!! 今天是{1}."},
            {"date_format", "yyyy年MM月dd日"},
    };

    @Override
    protected Object[][] getContents() {
        return DATA;
    }
}
