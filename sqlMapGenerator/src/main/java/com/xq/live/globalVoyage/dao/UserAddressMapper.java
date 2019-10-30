package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.UserAddress;

public interface UserAddressMapper {
    int insert(UserAddress record);

    int insertSelective(UserAddress record);
}