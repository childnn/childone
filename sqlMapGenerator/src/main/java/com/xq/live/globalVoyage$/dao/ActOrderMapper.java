package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.ActOrder;
import com.xq.live.globalVoyage$.model.ActOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActOrderMapper {
    long countByExample(ActOrderExample example);

    int deleteByExample(ActOrderExample example);

    int insert(ActOrder record);

    int insertSelective(ActOrder record);

    List<ActOrder> selectByExample(ActOrderExample example);

    int updateByExampleSelective(@Param("record") ActOrder record, @Param("example") ActOrderExample example);

    int updateByExample(@Param("record") ActOrder record, @Param("example") ActOrderExample example);
}