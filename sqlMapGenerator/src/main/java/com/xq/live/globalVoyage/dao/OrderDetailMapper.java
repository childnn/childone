package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.OrderDetail;

public interface OrderDetailMapper {
    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);
}