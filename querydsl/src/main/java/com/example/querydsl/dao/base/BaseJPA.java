package com.example.querydsl.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:21
 */
@NoRepositoryBean
public interface BaseJPA<T> extends JpaRepository<T, Long>,
        JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {
}
