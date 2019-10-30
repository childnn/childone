package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseManagerDepartment;

public interface BaseManagerDepartmentMapper {
    int insert(BaseManagerDepartment record);

    int insertSelective(BaseManagerDepartment record);
}