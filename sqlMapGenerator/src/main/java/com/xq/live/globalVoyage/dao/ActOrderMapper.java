package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.ActOrder;

public interface ActOrderMapper {
    int insert(ActOrder record);

    int insertSelective(ActOrder record);
}