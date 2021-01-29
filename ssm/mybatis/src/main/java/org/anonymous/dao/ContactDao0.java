package org.anonymous.dao;

import org.anonymous.domain.Contact;
import org.anonymous.domain.POJOContact;

import java.util.List;

/**
 * @author child
 * 2019/4/11 11:01
 */
public interface ContactDao0 {
    //映射
    List<Contact> mapperContact();

    //动态 sql -- 条件查询
    List<Contact> findContact(Contact contact);

    //条件查询1: in(...) -- 数组
    List<Contact> findById(int[] ids);

    //条件查询2:  -- list
    List<Contact> findByList(List<Integer> ids);

    //条件查询3: -- pojo
    List<Contact> findByPojo(POJOContact contact);

}
