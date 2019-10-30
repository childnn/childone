package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.CashLog;
import com.xq.live.globalVoyage$.model.CashLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CashLogMapper {
    long countByExample(CashLogExample example);

    int deleteByExample(CashLogExample example);

    int insert(CashLog record);

    int insertSelective(CashLog record);

    List<CashLog> selectByExample(CashLogExample example);

    int updateByExampleSelective(@Param("record") CashLog record, @Param("example") CashLogExample example);

    int updateByExample(@Param("record") CashLog record, @Param("example") CashLogExample example);
}