package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.GoodsActivityLog;

public interface GoodsActivityLogMapper {
    int insert(GoodsActivityLog record);

    int insertSelective(GoodsActivityLog record);
}