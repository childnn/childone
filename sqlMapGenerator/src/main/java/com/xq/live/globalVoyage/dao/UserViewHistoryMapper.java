package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.UserViewHistory;

public interface UserViewHistoryMapper {
    int insert(UserViewHistory record);

    int insertSelective(UserViewHistory record);
}