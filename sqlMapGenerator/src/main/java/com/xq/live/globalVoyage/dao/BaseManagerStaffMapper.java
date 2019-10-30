package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseManagerStaff;

public interface BaseManagerStaffMapper {
    int insert(BaseManagerStaff record);

    int insertSelective(BaseManagerStaff record);
}