package org.jpa.test;

import org.anonymous.model.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/30 23:44
 */
public class HelloJpa {

    /**
     * @see org.hibernate.boot.registry.classloading.spi.ClassLoaderService
     * @see org.hibernate.boot.registry.StandardServiceRegistryBuilder#DEFAULT_CFG_RESOURCE_NAME
     */
    @Test
    public void test() {
        SessionFactory sf = new Configuration() // 读取 classpath:Hibernate.properties
                .configure()
                // .setProperties()
                // .setProperty()
                // .addResource()
                // .addAnnotatedClass() // entities
                // .addPackage() // entity package
                .buildSessionFactory();


    }

    // @Test
    public static void main(String... args) {
        String persistenceUnitName = "jpa-1";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer cust = new Customer();
        cust.setId(121);
        cust.setLastName("Jack");
        cust.setEmail("132132@qq.com");
        cust.setAge(17);

        tx.commit();

        em.close();
        emf.close();

    }

}
