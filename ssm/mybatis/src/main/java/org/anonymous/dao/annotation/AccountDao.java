package org.anonymous.dao.annotation;

import org.anonymous.domain.Account;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author child
 * 2019/4/12 21:42
 */
public interface AccountDao {
    //返回集合
    @Select("select * from account where uid = #{随便写也是用户的id}")
    List<Account> findAccountByUId(int uid);
}
