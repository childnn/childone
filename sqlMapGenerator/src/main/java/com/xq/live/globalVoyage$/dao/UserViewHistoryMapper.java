package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.UserViewHistory;
import com.xq.live.globalVoyage$.model.UserViewHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserViewHistoryMapper {
    long countByExample(UserViewHistoryExample example);

    int deleteByExample(UserViewHistoryExample example);

    int insert(UserViewHistory record);

    int insertSelective(UserViewHistory record);

    List<UserViewHistory> selectByExample(UserViewHistoryExample example);

    int updateByExampleSelective(@Param("record") UserViewHistory record, @Param("example") UserViewHistoryExample example);

    int updateByExample(@Param("record") UserViewHistory record, @Param("example") UserViewHistoryExample example);
}