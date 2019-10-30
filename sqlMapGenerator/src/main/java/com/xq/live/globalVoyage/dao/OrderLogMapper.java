package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.OrderLog;

public interface OrderLogMapper {
    int insert(OrderLog record);

    int insertSelective(OrderLog record);
}