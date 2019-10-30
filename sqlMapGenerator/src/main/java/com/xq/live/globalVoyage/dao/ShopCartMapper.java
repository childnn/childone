package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.ShopCart;

public interface ShopCartMapper {
    int insert(ShopCart record);

    int insertSelective(ShopCart record);
}