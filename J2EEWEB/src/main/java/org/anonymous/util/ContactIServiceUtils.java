package org.anonymous.util;

import org.anonymous.service.ContactService;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author child
 * 2019/4/1 19:31
 * 加载 service 层的配置文件: 便于后期的事务和逻辑代码的扩展
 */
public abstract class ContactIServiceUtils {
    private static ContactService contactService;

    static {
        Properties properties = new Properties();
        try {
            // 加载 properties 文件
            // 方式一: ResourceBundle
            String className = ResourceBundle.getBundle("login").getString("className");

            //            properties.setProperty("className", "org.anonymous.service.ContactService"); //
            // 方式二: Properties.load
            /*properties.load(Objects.requireNonNull(ContactIServiceUtils.class.getClassLoader().getResourceAsStream("login.properties")));
            String className = properties.getProperty("className");*/
            contactService = (ContactService) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static ContactService getContactService() {
        return contactService;
    }

    public static void main(String[] args) {
        ContactService contactService = getContactService();
        System.out.println("contactService.getClass() = " + contactService.getClass());
    }

}
