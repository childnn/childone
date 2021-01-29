package file.FileClass.listAndlistFiles;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * shadow
 * 2019/3/14 13:29
 * FilenameFilter 过滤器
 */
public class Demo01 {
    public static void main(String[] args) {
        FilenameFilter filter = new OnlyExt("java");
        File f = new File("Day08FileAndRecursion\\src\\com\\itheima\\FileClass");
        String[] list = f.list(filter);
        System.out.println(Arrays.toString(list));
      /*  for (String s : list) {
            System.out.println(s);
        }*/
    }
}

class OnlyExt implements FilenameFilter {
    private final String ext;

    public OnlyExt(String ext) {
        this.ext = "." + ext;
    }

    @Override
    public boolean accept(File dir, String name) {
        //如果以 .java 结尾 或 是文件夹, 返回 true
        return name.endsWith(ext) || dir.isDirectory();
    }
}