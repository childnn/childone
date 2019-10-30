package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.GoodsSkuInventory;
import com.xq.live.globalVoyage$.model.GoodsSkuInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSkuInventoryMapper {
    long countByExample(GoodsSkuInventoryExample example);

    int deleteByExample(GoodsSkuInventoryExample example);

    int insert(GoodsSkuInventory record);

    int insertSelective(GoodsSkuInventory record);

    List<GoodsSkuInventory> selectByExample(GoodsSkuInventoryExample example);

    int updateByExampleSelective(@Param("record") GoodsSkuInventory record, @Param("example") GoodsSkuInventoryExample example);

    int updateByExample(@Param("record") GoodsSkuInventory record, @Param("example") GoodsSkuInventoryExample example);
}