package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.GoodsSkuWithBLOBs;

public interface GoodsSkuMapper {
    int insert(GoodsSkuWithBLOBs record);

    int insertSelective(GoodsSkuWithBLOBs record);
}