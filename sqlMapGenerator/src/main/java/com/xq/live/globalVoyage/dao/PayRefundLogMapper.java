package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.PayRefundLog;

public interface PayRefundLogMapper {
    int insert(PayRefundLog record);

    int insertSelective(PayRefundLog record);
}