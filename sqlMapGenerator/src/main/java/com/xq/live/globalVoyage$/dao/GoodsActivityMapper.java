package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsActivity;
import com.xq.live.globalVoyage$.model.GoodsActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsActivityMapper {
    long countByExample(GoodsActivityExample example);

    int deleteByExample(GoodsActivityExample example);

    int insert(GoodsActivity record);

    int insertSelective(GoodsActivity record);

    List<GoodsActivity> selectByExample(GoodsActivityExample example);

    int updateByExampleSelective(@Param("record") GoodsActivity record, @Param("example") GoodsActivityExample example);

    int updateByExample(@Param("record") GoodsActivity record, @Param("example") GoodsActivityExample example);
}