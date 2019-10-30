package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.CashLog;

public interface CashLogMapper {
    int insert(CashLog record);

    int insertSelective(CashLog record);
}