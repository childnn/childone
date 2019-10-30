package regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 * 爬虫: 获取网页中的 email 地址.
 *
 * @author MiaoOne
 * @since 2020/1/4 20:18
 */
public class GetEmailFromHtml {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\emailTest.html"));
            String line = "";
            while ((line = br.readLine()) != null) {//读取文件的每一行
                parse(line);//解析其中的email地址
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parse(String line) {
        Pattern p = Pattern.compile("[\\w.-]+@[\\w.-]+\\.[\\w]+");
        Matcher m = p.matcher(line);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
