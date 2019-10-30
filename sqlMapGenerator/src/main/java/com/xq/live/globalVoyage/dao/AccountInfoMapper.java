package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.AccountInfo;

public interface AccountInfoMapper {
    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);
}