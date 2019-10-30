package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.CashInfo;

public interface CashInfoMapper {
    int insert(CashInfo record);

    int insertSelective(CashInfo record);
}