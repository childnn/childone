package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BillDetail;

public interface BillDetailMapper {
    int insert(BillDetail record);

    int insertSelective(BillDetail record);
}