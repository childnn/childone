package org.anonymous;

import java.io.File;

public class Constants {
    // public static final String WEB_ROOT = "E:\\dev-code\\WorkSpace\\child\\tomcat\\src\\main\\java\\webroot";
    //System.getProperty("user.dir") + File.separator + "webroot";
    public static final String WEB_ROOT = System.getProperty("user.dir")
            + File.separator + "tomcat"
            + File.separator + "HowTomcatWorks"
            + File.separator + "target"
            + File.separator + "classes"
            + File.separator + "webroot";
}