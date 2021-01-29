package org.anonymous.service;

import org.anonymous.beans.Admin;
import org.anonymous.beans.Contact;
import org.anonymous.beans.PageBean1;

/**
 * @author child
 * 2019/4/1 19:17
 * service 层接口,便于后期维护,扩展
 */
public interface ContactService {
    Admin checkAdmin(Admin admin);

    PageBean1 pages(Contact contact, Integer pageNumber, Integer pageSize);
}
