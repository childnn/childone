package com.example.querydsl.service.impl;

import com.example.querydsl.dao.UserJPA;
import com.example.querydsl.entity.QUser;
import com.example.querydsl.entity.User;
import com.example.querydsl.service.UserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:30
 */
@Service
public class UserServiceImpl implements UserService, InitializingBean {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @Autowired
    private UserJPA userJPA;


    @Override
    public List<User> queryAll() {
        final QUser qUser = QUser.user;

        return queryFactory
                .selectFrom(qUser)
                .orderBy(qUser.id.desc())
                .fetch();
    }

    @Override
    public User detail(Long id) {
        final QUser qUser = QUser.user;
        return queryFactory
                .selectFrom(qUser)
                .where(qUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public User detail2(Long id) {
        final QUser qUser = QUser.user;
        return userJPA.findOne(qUser.id.eq(id)).orElse(null);
    }

    /**
     * todo:
     * 此处的 getOne 与以上的查询方法不一样, 必须在
     * User 上加 {@link com.fasterxml.jackson.annotation.JsonIgnoreProperties}
     * 应为结果对象的生成方式不同, 暂不明具体原因
     * @param id
     * @return
     */
    @Override
    public User detail3(Long id) {
        return userJPA.getOne(id);
    }

    @Override
    public List<User> likeQueryWithName(String name) {
        final QUser qUser = QUser.user;
        return queryFactory
                .selectFrom(qUser)
                .where(qUser.username.like("%" + name + "%"))
                .fetch();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAFactory...");
    }
}
