package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.OrderLog;
import com.xq.live.globalVoyage$.model.OrderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderLogMapper {
    long countByExample(OrderLogExample example);

    int deleteByExample(OrderLogExample example);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    List<OrderLog> selectByExample(OrderLogExample example);

    int updateByExampleSelective(@Param("record") OrderLog record, @Param("example") OrderLogExample example);

    int updateByExample(@Param("record") OrderLog record, @Param("example") OrderLogExample example);
}