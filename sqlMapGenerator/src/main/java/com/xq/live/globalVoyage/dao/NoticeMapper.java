package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.Notice;

public interface NoticeMapper {
    int insert(Notice record);

    int insertSelective(Notice record);
}