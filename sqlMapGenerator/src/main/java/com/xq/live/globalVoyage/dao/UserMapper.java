package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}