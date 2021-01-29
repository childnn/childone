package org.anonymous.service.impl;

import org.anonymous.dao.ServiceDao;
import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/13 23:06
 * 依赖注入: 给 spring 容器管理的属性赋值
 */
public class ServiceImpl5 implements AccountService {
    private ServiceDao serviceDao;

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public void save() {
        serviceDao.save();
    }
}
