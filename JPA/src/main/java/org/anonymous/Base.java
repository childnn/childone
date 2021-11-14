package org.anonymous;

import org.anonymous.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/30 23:14
 * @see javax.persistence.Table
 * @see javax.persistence.Entity
 */
public class Base {

    /**
     * @see javax.persistence.Persistence
     * @see javax.persistence.EntityManagerFactory
     * @see org.hibernate.SessionFactory
     * @see javax.persistence.EntityManager
     * @see org.hibernate.Session
     * @see javax.persistence.Query
     * @see org.hibernate.query.Query
     * @see javax.transaction.Transaction
     * @see javax.persistence.EntityTransaction
     * @see javax.transaction.UserTransaction
     * @see org.hibernate.Transaction
     */
    public static void main(String... args) {
        String persistenceUnitName = "jpa-1";
        // 可以添加属性: map 参数
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName); // 等同 hibernate session factory
        EntityManager em = emf.createEntityManager(); // hibernate session
        EntityTransaction tx = em.getTransaction(); // hibernate transaction
        tx.begin();

        // em.getCriteriaBuilder().

        Customer cust = new Customer();
        // cust.setId(121); // 如果指定主键自增, 不可给 id 赋值, 否则报错 org.hibernate.PersistentObjectException: detached entity passed to persist
        cust.setLastName("rose");
        cust.setEmail("132132@qq.com");
        cust.setAge(17);

        em.persist(cust);

        tx.commit();

        // 查看 sql 打印时机:
        Customer csu = em.find(Customer.class, 1); // 查询
        System.out.println(csu.getClass());
        System.out.println("=======================");
        System.out.println("csu = " + csu);
        Customer reference = em.getReference(Customer.class, 52);
        System.out.println(reference.getClass()); // proxy
        System.out.println("-----------------------------");
        System.out.println("reference = " + reference); // 使用时才查询

        em.close();
        emf.close();

    }
}
