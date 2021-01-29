package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

import java.util.Map;

/**
 * @author child
 * 2019/4/13 22:54
 * map/properties: k-v
 */
public class ServiceImpl3 implements AccountService {
    private Map<String, String> map;

    @Override
    public void save() {
        System.out.println(map);
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
