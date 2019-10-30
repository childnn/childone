package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.GoodsDestination;

public interface GoodsDestinationMapper {
    int insert(GoodsDestination record);

    int insertSelective(GoodsDestination record);
}