/*
 * Copyright 1999,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package tomcat4.org.apache.catalina.startup;

/**
 * String constants for the startup package.
 * <br>
 * Note that some values include a leading '/' and that some do not. This is
 * intentional based on how the values are used.
 *
 * @author Craig R. McClanahan
 */
public final class Constants {

    public static final String Package = "org.apache.catalina.startup";

    public static final String ApplicationContextXml = "META-INF/context.xml";
    public static final String ApplicationWebXml = "/WEB-INF/web.xml";
    public static final String TomcatWebXml = "/WEB-INF/tomcat-web.xml";
    public static final String DefaultContextXml = "conf/context.xml";
    public static final String DefaultWebXml = "conf/web.xml";
    public static final String HostContextXml = "context.xml.default";
    public static final String HostWebXml = "web.xml.default";
    public static final String WarTracker = "/META-INF/war-tracker";

    /**
     * A dummy value used to suppress loading the default web.xml file.
     *
     * <p>
     * It is useful when embedding Tomcat, when the default configuration is
     * done programmatically, e.g. by calling
     * <code>Tomcat.initWebappDefaults(context)</code>.
     *
     * @see Tomcat
     */
    public static final String NoDefaultWebXml = "org/apache/catalina/startup/NO_DEFAULT_XML";

    /**
     * Name of the system property containing
     * the tomcat product installation path
     */
    public static final String CATALINA_HOME_PROP = "catalina.home";

    /**
     * Name of the system property containing
     * the tomcat instance installation path
     */
    public static final String CATALINA_BASE_PROP = "catalina.base";
}


/**
 * String constants for the startup package.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.6 $ $Date: 2004/08/26 21:41:12 $
 *//*


public final class Constants {

    public static final String Package = "org.apache.catalina.startup";

    public static final String ApplicationWebXml = "/WEB-INF/web.xml";
    public static final String DefaultWebXml = "conf/web.xml";

    public static final String TldDtdPublicId_11 =
        "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";
    public static final String TldDtdResourcePath_11 =
        //        "conf/tld_11.dtd";
        "/javax/servlet/jsp/resources/web-jsptaglibrary_1_1.dtd";

    public static final String TldDtdPublicId_12 =
        "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";
    public static final String TldDtdResourcePath_12 =
        //        "conf/tld_12.dtd";
        "/javax/servlet/jsp/resources/web-jsptaglibrary_1_2.dtd";

    public static final String WebDtdPublicId_22 =
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN";
    public static final String WebDtdResourcePath_22 =
        //      "conf/web_22.dtd";
        "/javax/servlet/resources/web-app_2_2.dtd";

    public static final String WebDtdPublicId_23 =
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
    public static final String WebDtdResourcePath_23 =
        //      "conf/web_23.dtd";
        "/javax/servlet/resources/web-app_2_3.dtd";

}
*/
