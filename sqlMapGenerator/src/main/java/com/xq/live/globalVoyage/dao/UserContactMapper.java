package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.UserContact;

public interface UserContactMapper {
    int insert(UserContact record);

    int insertSelective(UserContact record);
}