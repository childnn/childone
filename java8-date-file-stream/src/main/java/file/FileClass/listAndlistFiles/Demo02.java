package file.FileClass.listAndlistFiles;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * shadow
 * 2019/3/14 13:50
 */
public class Demo02 {
    public static void main(String[] args) {
        getFile(new File("D:\\Develope"), ".java");
    }

    //listFiles() :
    private static void getFile(File file, String end) {
        if (!file.exists()) {
            System.out.println("文件路径错误!");
            return;
        }
        //过滤得到".java" 结尾的文件 和 文件夹
        String[] list = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(end) || dir.isDirectory();
            }
        });
        System.out.println(Arrays.toString(list));
        System.out.println(list);
        //遍历数组,得到数组元素, 如果是 .java 结尾的元素, 说明是 文件, 输出 文件名,
        // 如果不是 .java 结尾的元素,说明是文件夹, 继续遍历
       /* for (String f : list) {
            if (f.endsWith(end)) {
                System.out.println(f);
            } else {
                getFile(new File(f), end);
            }
        }*/
    }
}
