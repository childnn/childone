package org.anonymous.localeformat.classproperties;

import java.util.ListResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/13 11:35
 */
public class MyMessage_en_US extends ListResourceBundle {

    private static final String[][] DATA = {
            {"hello", "Welcome You!"},
            {"name", "Jack"},
            {"msg", "Hello,{0}!!! Today is {1}."},
            {"date_format", "yyyy-MM-dd"},
    };

    @Override
    protected Object[][] getContents() {
        return DATA;
    }
}
