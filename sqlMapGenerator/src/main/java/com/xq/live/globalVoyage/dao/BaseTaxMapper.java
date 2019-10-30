package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseTax;

public interface BaseTaxMapper {
    int insert(BaseTax record);

    int insertSelective(BaseTax record);
}