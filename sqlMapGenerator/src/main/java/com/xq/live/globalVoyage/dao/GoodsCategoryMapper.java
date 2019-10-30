package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.GoodsCategory;

public interface GoodsCategoryMapper {
    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);
}