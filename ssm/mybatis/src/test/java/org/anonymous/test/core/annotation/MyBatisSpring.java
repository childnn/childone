package org.anonymous.test.core.annotation;

import org.anonymous.config.AppConfig;
import org.anonymous.dao.ContactDao;
import org.anonymous.domain.Contact;
import org.anonymous.service.TxService;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author child
 * 2019/4/10 14:02
 * 反射/注解/接口/动态代理/配置文件
 * 使用 mybatis 框架: 动态代理 + 配置文件
 */
// @Component
public class MyBatisSpring {

    // @Autowired
    // private TxService txService;

    /**
     * 与 {@link org.springframework.context.annotation.AnnotationConfigApplicationContext} 非 web 应用对应的 web 应用 context
     * {@link org.springframework.web.context.support.AnnotationConfigWebApplicationContext} {@link org.springframework.web.WebApplicationInitializer}
     * ---
     *
     * @see org.springframework.core.env.Environment
     * @see org.springframework.core.io.ResourceLoader
     * ---
     * @see org.springframework.context.annotation.AnnotatedBeanDefinitionReader#AnnotatedBeanDefinitionReader(org.springframework.beans.factory.support.BeanDefinitionRegistry, org.springframework.core.env.Environment)
     * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry)
     * {@link org.springframework.context.annotation.ConfigurationClassPostProcessor} 注入的点 {@link org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)}
     * 几个在此注入的  PostProcessor
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor  属于 BeanFactory 级别的 PostProcessor
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor 属于 Bean 级别的 PostProcessor
     * @see org.springframework.context.event.EventListenerMethodProcessor
     * ---
     * 解析 AnnotationConfig 的工具类 {@link org.springframework.context.annotation.AnnotationConfigUtils}
     */
    public static void main(String[] args) {
        // 默认的 BeanFactory 在 org.springframework.context.support.GenericApplicationContext.GenericApplicationContext() 中构建
        // 传入的住配置类被解析为 org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        TxService tx = ctx.getBean(TxService.class);
        tx.execute();
    }

    @Test
    public void mybatisConfig() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        TxService tx = ctx.getBean(TxService.class);
        Configuration mybatisConfig = tx.mybatisConfig();
        Collection<Cache> caches = mybatisConfig.getCaches();
        System.out.println("caches = " + caches);
    }


    /**
     * 1. 加载驱动
     * 2. 获取连接
     * 3. 构建预处理器: 编译 sql
     * 4. 设置参数
     * 5. 执行
     */
    public void jdbc() throws ClassNotFoundException, SQLException {
        Class.forName("驱动");
        String url = "null";
        String username = "";
        String pwd = "";
        Connection conn = DriverManager.getConnection(url, username, pwd);
        String sql = "";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(0, "");
        pst.executeQuery();
        // pst.execute();
        // pst.executeUpdate();
    }

    @Test
    public void test1() throws IOException {
        //创建 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //获取 SqlSessionFactory
        //        InputStream is = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        //获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //执行: 传递 接口,底层就是 动态代理,返回接口的代理对象(实现类)
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = mapper.findAll();
        for (Contact contact : list) {
            System.out.println(contact);
        }
    }
}
