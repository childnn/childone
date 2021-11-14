package org.anonymous;

import lombok.Data;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;
import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
import org.hibernate.boot.internal.SessionFactoryOptionsBuilder;
import org.hibernate.engine.spi.SessionImplementor;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.spi.PersistenceProvider;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/5/28 9:31
 * @see org.hibernate.SessionFactory 它是一个 thread-safe, immutable 的工厂, 作为建立
 *  {@link org.hibernate.Session} 会话实例的工厂. SessionFactory 的建立代价很大, 占用 内存, CPU 资源很大,
 *  所以一个应用一般只需一个 SessionFactory. SessionFactory 维护 Hibernate 的所有 Session, 二级缓冲, 连接池, 事务等等.
 * Session 是一个单线程, 短生命周期的对象, 是按 "unit of work" 模式的概念构建的. 封装了 JDBC 的连接对象 {@link java.sql.Connection}
 * 作为 {@link org.hibernate.Transaction} 实例的工厂, 维护应用程序 domain model 中通用(repeateable-read)的持久化内容(一级缓冲).
 * @see org.hibernate.Transaction 单线程, 短生命周期的对象, 用于界定具体事务的范围. 作为一个抽象 API 接口用于隔离
 * 各种应用于底层的事务系统.
 * @see org.hibernate.context.spi.CurrentSessionContext
 * @see org.hibernate.service.ServiceRegistry
 * @see org.hibernate.boot.SessionFactoryBuilder
 * {@link org.hibernate.Session} API 与 {@link javax.persistence.EntityManager} API 表示处理持久化数据的环境.
 * 这个概念称为 持久化上下文(persistence context). 持久化数据又状态, 这种状态表示底层数据库与持久化上下文之间的关系.
 * Entity 的几种状态
 *  1. transient: 瞬时态. 实体刚刚被实例化并且没有与 持久化上下文关联. 也没有写入数据库中. 通常还没有 ID 值.
 *  2. managed/persistent: 托管态/持久态. 实体已经有了 ID 并且与持久化上下文关联. 在物理数据库中可能存在也可能不存在.
 *  3. detatched: 游离态. 实体已经有了关联的 ID, 但是不再与持久化上下文关联(通常因为持久化上下文已经关闭或者实体被上下文 evicted)
 *  4. removed: 删除态. 实体已经有了 ID 并且与持久化上下文有了关联, 然而数据库已经计划删除数据.
 * 大部分 {@link org.hibernate.Session} 与 {@link javax.persistence.EntityManager} 的方法, 在这些状态之间移动对象.
 * {@link org.hibernate.Session#delete} Hibernate 自身能够删除游离态对象与托管态对象, 但是 JPA 只能通过
 * {@link javax.persistence.EntityManager#remove} 方法删除托管态对象.
 * ---
 * 有时会涉及到 lazy loading, 从一个实体中得到一个引用, 同时不加载他的数据是非常重要的能力. 通常, 这用于需要在两个实体间建立关联.
 * 1. 获取没有初始化数据的实体
 *  {@link org.hibernate.Session#byId} {@link org.hibernate.IdentifierLoadAccess#getReference(Object)}
 *  {@link javax.persistence.EntityManager#getReference}
 *  上面的两种方式获取实体的引用. 这两种方式是假设实体被定义为懒加载(一般通过运行时代理).
 *  有两种情况将抛出异常:
 *   a. 指定实体没有关联到实际数据库时;
 *   b. 应用程序试图访问返回代理的数据时.
 * 2. 获取已经初始化数据的实体引用
 *   {@link org.hibernate.Session#byId} {@link org.hibernate.IdentifierLoadAccess#load}
 *   {@link javax.persistence.EntityManager#find}
 *   在这些情况下, 如果没有匹配的数据行被发现则返回 null.
 * @see org.hibernate.annotations.NaturalId 标注在 entity 属性上
 * 在 Hibernate API 中通过标识符 identifier 和 自然 ID(natural-id) 访问持久数据方法是一样的.
 * {@link org.hibernate.Session#getReference}
 * {@link org.hibernate.NaturalIdLoadAccess#getReference}
 * {@link org.hibernate.SimpleNaturalIdLoadAccess#getReference}
 * getReference 方法假定标识符一定存在, 如果不存在则会报错. 所以此方法一定不要用于测试是否存在.
 * 这是因为此方法建立与返回的代理是否与 Session 关联, 而不是与数据库有关. 此方法经典的用法用于外键关联.
 * -
 * {@link org.hibernate.Session#load}
 * {@link org.hibernate.NaturalIdLoadAccess#load}
 * {@link org.hibernate.SimpleNaturalIdLoadAccess#load}
 * load 方法, 如果对象的标识符不存在将返回 null, 如果存在就返回带有标识符的对象.
 * 除了以上两种方式, 其他每个定义的方法都有一个 {@link org.hibernate.LockOptions} 参数.
 * --
 * refresh:
 * {@link org.hibernate.Session#refresh}
 * {@link javax.persistence.EntityManager#refresh}
 * 使用刷新的一种情况是: 当知道数据库状态发生改变时. 刷新允许当前的数据库状态被重新写入实体实例与持久化上下文环境.
 * 使用刷新的另一种情况是: 使用数据库触发器初始化实体的某些属性时. 请注意, 除非指定关联的级联刷新, 否则只有实体与实体
 * 集合被刷新(不定义级联刷新只刷新实体自身的属性, 不包括关联的引用). 但是, 请注意, Hibernate 有能力自动处理刷新.
 * ---
 * 更改 托管态/持久态
 *  在 托管态/持久态 的实体可以被应用程序操纵, 任何更改将被自动发现, 并在持久化上下文刷新时持久化.
 *  这时无需调用什么特殊的方法就可以使这些更改持久化了.
 *  {@link #update}
 * ---
 * 使用游离状态数据
 * 游离是一种工作过程, 数据在持久化上下文范围之外. 数据变成游离态有很多种方式. 一旦持久化上下文关闭, 与之相关的的所有数据都成为游离态.
 * 清理持久化上下文环境有同样的效果. 从持久化上下文回收特定的实体, 可以使它们变成游离态. 最后, 序列化也可以使反序列化的形式称为游离态
 * (原始实体还是托管态).
 * 游离后的数据仍然可以被操作, 但是持久化上下文不再自动知道这些更改, 应用程序需要进行干预才可以使这些更改持久化.
 * ---
 * 复位游离态数据: 游离态变成托管态
 *   复位是指游离态的实体实例被再次取回, 并且再次与持久化上下文关联(再次变成托管态).
 *   JPA 不支持这种模式, 只有 Hibernate 的 {@link org.hibernate.Session} 支持.
 *   {@link #reset()}
 *   这个方法并不意味着 SQL-UPDATE 将要马上执行. 而是意味着当持久化上下文刷新时 SQL-UPDATE 可能被执行.
 *   但是在执行前, 要经过 select 过程, 这被称为 select-before-update. 这时因为 Hibernate 更新前不知道
 *   以前的状态是否变化了, 所以要进行比较. 这种情况下, Hibernate 将从实际数据库中取得数据, 然后决定数据是否需要更新.
 *   针对游离的实体, update/save'OrUpdate 方法执行完全相同的操作.
 * ---
 * 合并游离态数据
 *  合并过程是指: 将一个传入的游离态的对象中的数据复制到一个新的托管态的对象中.
 *  {@link #merge()}
 * ---
 * 验证对象的状态
 * 1. 验证托管状态
 *  程序能够验证持久化上下文中的有关实体与集合的状态.
 *  {@link org.hibernate.Session#contains}
 *  {@link javax.persistence.EntityManager#contains}
 * 2. 验证懒加载
 *   {@link org.hibernate.Hibernate#isInitialized}
 *   {@link org.hibernate.Hibernate#isPropertyInitialized}
 *   {@link javax.persistence.PersistenceUnitUtil#isLoaded}
 *      -- {@link javax.persistence.EntityManager#getEntityManagerFactory()}
 *         {@link javax.persistence.EntityManagerFactory#getPersistenceUnitUtil()}
 *   {@link javax.persistence.PersistenceUtil#isLoaded}
 *      -- {@link javax.persistence.Persistence#getPersistenceUtil()}
 * ---
 * 从 JPA 访问 Hibernate
 *   JPA 定义了非常有用的方法, 以便程序访问底层提供者的 API
 *   {@link javax.persistence.EntityManager#unwrap}
 *   {@link #unwrap()}
 * ---
 * @see org.hibernate.cfg.AvailableSettings
 * @see org.hibernate.dialect.Dialect
 * --
 * @see javax.persistence.EntityManager#getCriteriaBuilder()
 * @see javax.persistence.criteria.CriteriaBuilder
 * @see javax.persistence.criteria.CriteriaQuery
 * @see javax.persistence.criteria.Root
 * @see javax.persistence.Tuple
 * --
 * @see org.hibernate.annotations.DynamicInsert 是否在 SQL-insert 中包括空的属性值
 * @see org.hibernate.annotations.DynamicUpdate 是否在 SQL-update 中包括未被修改的属性
 * @see org.hibernate.annotations.Entity
 * @see org.hibernate.annotations.Immutable 不可变
 * @see org.hibernate.cfg.ImprovedNamingStrategy 表明, 字段名 与 entity 属性名的映射关系
 * @see javax.persistence.Embedded
 * @see PersistenceProvider
 * .@see org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider
 * .@see org.springframework.orm.jpa.AbstractEntityManagerFactoryBean
 * .@see org.springframework.orm.jpa.JpaVendorAdapter
 * .@see org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter#HibernateJpaVendorAdapter()
 */
public class HibernatePreview {

    // unwrap
    void unwrap() {
        EntityManager em = getEm(getSession());
        Session se = em.unwrap(Session.class);

        SessionImplementor unwrap = em.unwrap(SessionImplementor.class);

    }


    // 合并游离态
    void merge() {
        Session se = getSession();
        Cat cat = new Cat();
        cat.setId(1L);
        se.merge(cat);

        EntityManager em = getEm(se);
        em.merge(cat);

    }

    // 复位游离态
    void reset() {
        Session se = getSession();
        Cat cat = new Cat(); // 游离态
        se.lock(cat, LockMode.NONE);
        se.saveOrUpdate(cat);
    }


    // 更新托管状态的实体: 修改实体的数据, 自动被发现
    void update() {
        Session se = getSession();
        Cat cat = se.get(Cat.class, 1);
        cat.setUsername("Garfield");
        se.flush(); // 通常不必显式调用

        EntityManager em = getEm(se);
        Cat c = em.find(Cat.class, 1);
        c.setUsername("Garfield");
        em.flush(); // 通常不必显式调用
    }

    private EntityManager getEm(Session se) {
        return se.getEntityManagerFactory().createEntityManager();
    }

    @Data
    @Entity
    class Cat {

        @Id
        @GeneratedValue
        Long id;

        @NaturalId
        String username;


        void t1() {
            Session se = getSession();
            // 使用 getReference 方法建立关联(引用)
            Cat cat = se.bySimpleNaturalId(Cat.class)
                    .getReference("Tom");

            // 使用 load 方法填充数据
            Cat tom = se.bySimpleNaturalId(Cat.class).load("Tom");
        }

    }


    @Entity
    class User {

        @Id
        @GeneratedValue
        Long id;

        @NaturalId
        String username;

        @NaturalId
        String system;

        // ...

        void t1() {
            Session se = getSession();
            // 使用 getReference 方法建立关联
            User re = se.byNaturalId(User.class)
                    .using("username", "Steve")
                    .using("system", "prod")
                    .getReference();

            // 使用 load 方法填充数据
            User user = se.byNaturalId(User.class)
                    .using("username", "Steve")
                    .using("system", "prod")
                    .load();
        }
    }

    Session getSession() {
        return new SessionFactoryBuilderImpl(null, (SessionFactoryOptionsBuilder) null)
                .build().getCurrentSession();
    }


}
