package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseTax;
import com.xq.live.globalVoyage$.model.BaseTaxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseTaxMapper {
    long countByExample(BaseTaxExample example);

    int deleteByExample(BaseTaxExample example);

    int insert(BaseTax record);

    int insertSelective(BaseTax record);

    List<BaseTax> selectByExample(BaseTaxExample example);

    int updateByExampleSelective(@Param("record") BaseTax record, @Param("example") BaseTaxExample example);

    int updateByExample(@Param("record") BaseTax record, @Param("example") BaseTaxExample example);
}