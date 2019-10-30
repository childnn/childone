package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.OrderInfo;

public interface OrderInfoMapper {
    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);
}