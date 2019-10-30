package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsSku;
import com.xq.live.globalVoyage$.model.GoodsSkuExample;
import com.xq.live.globalVoyage$.model.GoodsSkuWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSkuMapper {
    long countByExample(GoodsSkuExample example);

    int deleteByExample(GoodsSkuExample example);

    int insert(GoodsSkuWithBLOBs record);

    int insertSelective(GoodsSkuWithBLOBs record);

    List<GoodsSkuWithBLOBs> selectByExampleWithBLOBs(GoodsSkuExample example);

    List<GoodsSku> selectByExample(GoodsSkuExample example);

    int updateByExampleSelective(@Param("record") GoodsSkuWithBLOBs record, @Param("example") GoodsSkuExample example);

    int updateByExampleWithBLOBs(@Param("record") GoodsSkuWithBLOBs record, @Param("example") GoodsSkuExample example);

    int updateByExample(@Param("record") GoodsSku record, @Param("example") GoodsSkuExample example);
}