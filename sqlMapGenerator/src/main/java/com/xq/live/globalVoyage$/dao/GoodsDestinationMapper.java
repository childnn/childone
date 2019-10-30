package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsDestination;
import com.xq.live.globalVoyage$.model.GoodsDestinationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsDestinationMapper {
    long countByExample(GoodsDestinationExample example);

    int deleteByExample(GoodsDestinationExample example);

    int insert(GoodsDestination record);

    int insertSelective(GoodsDestination record);

    List<GoodsDestination> selectByExample(GoodsDestinationExample example);

    int updateByExampleSelective(@Param("record") GoodsDestination record, @Param("example") GoodsDestinationExample example);

    int updateByExample(@Param("record") GoodsDestination record, @Param("example") GoodsDestinationExample example);
}