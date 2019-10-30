package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseManagerStaffAuth;

public interface BaseManagerStaffAuthMapper {
    int insert(BaseManagerStaffAuth record);

    int insertSelective(BaseManagerStaffAuth record);
}