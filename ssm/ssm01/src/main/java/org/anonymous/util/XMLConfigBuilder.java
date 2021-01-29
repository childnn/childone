package org.anonymous.util;

import org.anonymous.beans.Configuration;
import org.anonymous.beans.Mapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author child
 * 2019/4/9 21:58
 * 解析 数据库连接核心配置文件: SqlMapConfig.xml
 * 得到数据库连接的相关信息及 sql 执行信息 -- 封装 Configuration 对象
 * 数据库连接相关信息:
 * 驱动/url/数据库连接用户名/密码
 * sql 执行信息:
 * sql 语句 + resultType
 */
public abstract class XMLConfigBuilder {

    /**
     * @param is 解析数据库核心配置文件的 输入流对象
     * @return 解析完成的 数据库核心配置文件后封装的 Configuration 对象
     * @throws DocumentException
     */
    public static Configuration builderConfiguration(InputStream is) throws DocumentException {
        Configuration configuration = new Configuration();
        //解析并封装
        //DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setNamespaceAware(false);
        //DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //docBuilder.
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding(StandardCharsets.UTF_8.name());

        //        InputStream is = getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        //        Document document = saxReader.read(Paths.get("E:\\dev-code\\WorkSpace\\child\\ssm\\ssm01\\src\\main\\resources\\mybatis\\SqlMapConfig.xml").toFile()); // todo: ? system id 指什么?
        Document document = saxReader.read(is); // todo: ? system id 指什么?
        Element root = document.getRootElement();
        List<Node> list = root.selectNodes("//property");
        //List<Element> list = root.selectNodes("//property");
        for (Node e : list) {
            //String name = e.attributeValue("name");
            //String value = e.attributeValue("value");
            if (e instanceof Element) {
                String name = e.valueOf("name");
                String value = e.valueOf("value");
                if ("driver".equals(name)) {
                    //同 name 则 设置 对应的 value
                    configuration.setDriver(value);
                }
                if ("password".equals(name)) {
                    configuration.setPassword(value);
                }
                if ("url".equals(name)) {
                    configuration.setUrl(value);
                }
                if ("username".equals(name)) {
                    configuration.setUsername(value);
                }
            }
        }
        //对 sql 的 map 集合赋值
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();
        //解析 sql 映射的配置文件: UserMapper.xml
        Element mappersElement = root.element("mappers");
        if (null != mappersElement) {
            List<Element> mapperElements = mappersElement.elements("mapper");
            for (Element mapperElement : mapperElements) {
                //UserMapper.xml 配置文件的相对路径
                String path = mapperElement.attributeValue("resource");

                //将每一个 sql 映射配置文件 对象的 map 集合存入到大的 map 中
                mappers.putAll(loadXmlForMapper(path));
            }
        }
        //给 configuration 对象的 mappers 属性赋值
        configuration.setMappers(mappers);

        return configuration;
    }

    //根据给定的 sql 映射配置文件 (UserMapper.xml) 地址, 解析并封装 Mapper 对象
    private static Map<String, Mapper> loadXmlForMapper(String path) throws DocumentException {
        //path 是 sql 配置文件的路径,将路径转化为字节流
        //类加载器加载配置文件的路径:相对于 src 的路径
        InputStream is = XMLConfigBuilder.class.getClassLoader().getResourceAsStream(path);
        //dom4j 解析
        //创建 SAXReader
        SAXReader reader = new SAXReader();
        //获取 Document 对象(xml文档)
        Document document = reader.read(is);
        // 获取根节点
        Element rootElement = document.getRootElement();
        //封装 Mapper 对象的 map
        Map<String, Mapper> map = new HashMap<>();

        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.elements("select");
        for (Element element : list) {
            /*
             <mapper namespace="user"> -- 模块名: 唯一
            *   <select id="getUser" resultType="全限定名">
            *       select * from xxx
            *   </select>
            * </mapper>
            * */
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String sql = element.getText(); //文本即 定义的 sql 语句
            Mapper mapper = new Mapper();
            mapper.setQuerySql(sql);
            mapper.setResultType(resultType);
            //key : user.getUser -- value : mapper
            map.put(namespace + "." + id, mapper);
        }
        return map;
    }
}
