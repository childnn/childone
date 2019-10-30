package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsActivityLog;
import com.xq.live.globalVoyage$.model.GoodsActivityLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsActivityLogMapper {
    long countByExample(GoodsActivityLogExample example);

    int deleteByExample(GoodsActivityLogExample example);

    int insert(GoodsActivityLog record);

    int insertSelective(GoodsActivityLog record);

    List<GoodsActivityLog> selectByExample(GoodsActivityLogExample example);

    int updateByExampleSelective(@Param("record") GoodsActivityLog record, @Param("example") GoodsActivityLogExample example);

    int updateByExample(@Param("record") GoodsActivityLog record, @Param("example") GoodsActivityLogExample example);
}