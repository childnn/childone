package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.CashInfo;
import com.xq.live.globalVoyage$.model.CashInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CashInfoMapper {
    long countByExample(CashInfoExample example);

    int deleteByExample(CashInfoExample example);

    int insert(CashInfo record);

    int insertSelective(CashInfo record);

    List<CashInfo> selectByExample(CashInfoExample example);

    int updateByExampleSelective(@Param("record") CashInfo record, @Param("example") CashInfoExample example);

    int updateByExample(@Param("record") CashInfo record, @Param("example") CashInfoExample example);
}