package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.BaseManagerAuthentication;

public interface BaseManagerAuthenticationMapper {
    int insert(BaseManagerAuthentication record);

    int insertSelective(BaseManagerAuthentication record);
}