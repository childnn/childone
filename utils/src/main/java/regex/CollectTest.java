package regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 * 统计一个项目中一共有多少行代码, 多少行注释, 多少个空白行.
 *
 * @author MiaoOne
 * @since 2020/1/4 20:21
 */
public class CollectTest {
    private static long codeLines = 0;
    private static long commentLines = 0;
    private static long whiteLines = 0;
    private static String filePath = "C:\\TankOnline";

    public static void main(String[] args) {
        process(filePath);
        System.out.println("codeLines : " + codeLines);
        System.out.println("commentLines : " + commentLines);
        System.out.println("whiteLines : " + whiteLines);
    }

    /**
     * 递归查找文件
     *
     * @param pathStr
     */
    public static void process(String pathStr) {
        File file = new File(pathStr);
        if (file.isDirectory()) { // 是文件夹则递归查找
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                String fPath = f.getAbsolutePath();
                process(fPath);
            }
        } else if (file.isFile()) {//是文件则判断是否是.java文件
            if (file.getName().matches(".*\\.java$")) {
                parse(file);
            }
        }
    }

    private static void parse(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim(); // 清空每行首尾的空格
                if (line.matches("^[\\s&&[^\\n]]*$")) { // 注意不是以\n结尾, 因为在br.readLine()会去掉\n
                    whiteLines++;
                } else if (line.startsWith("/*") || line.startsWith("*") || line.endsWith("*/")) {
                    commentLines++;
                } else if (line.startsWith("//") || line.contains("//")) {
                    commentLines++;
                } else {
                    if (line.startsWith("import") || line.startsWith("package")) { // 导包不算
                        continue;
                    }
                    codeLines++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
