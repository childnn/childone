package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsSpu;
import com.xq.live.globalVoyage$.model.GoodsSpuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSpuMapper {
    long countByExample(GoodsSpuExample example);

    int deleteByExample(GoodsSpuExample example);

    int insert(GoodsSpu record);

    int insertSelective(GoodsSpu record);

    List<GoodsSpu> selectByExample(GoodsSpuExample example);

    int updateByExampleSelective(@Param("record") GoodsSpu record, @Param("example") GoodsSpuExample example);

    int updateByExample(@Param("record") GoodsSpu record, @Param("example") GoodsSpuExample example);
}