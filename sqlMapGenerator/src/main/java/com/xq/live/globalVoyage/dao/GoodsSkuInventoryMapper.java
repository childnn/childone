package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.GoodsSkuInventory;

public interface GoodsSkuInventoryMapper {
    int insert(GoodsSkuInventory record);

    int insertSelective(GoodsSkuInventory record);
}