package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.OrderPayInfo;
import com.xq.live.globalVoyage$.model.OrderPayInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderPayInfoMapper {
    long countByExample(OrderPayInfoExample example);

    int deleteByExample(OrderPayInfoExample example);

    int insert(OrderPayInfo record);

    int insertSelective(OrderPayInfo record);

    List<OrderPayInfo> selectByExample(OrderPayInfoExample example);

    int updateByExampleSelective(@Param("record") OrderPayInfo record, @Param("example") OrderPayInfoExample example);

    int updateByExample(@Param("record") OrderPayInfo record, @Param("example") OrderPayInfoExample example);
}