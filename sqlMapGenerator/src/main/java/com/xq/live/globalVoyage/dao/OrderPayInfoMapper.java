package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.OrderPayInfo;

public interface OrderPayInfoMapper {
    int insert(OrderPayInfo record);

    int insertSelective(OrderPayInfo record);
}