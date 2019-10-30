package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.PayRefundDetails;

public interface PayRefundDetailsMapper {
    int insert(PayRefundDetails record);

    int insertSelective(PayRefundDetails record);
}