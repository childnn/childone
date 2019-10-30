package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseProperty;

public interface BasePropertyMapper {
    int insert(BaseProperty record);

    int insertSelective(BaseProperty record);
}