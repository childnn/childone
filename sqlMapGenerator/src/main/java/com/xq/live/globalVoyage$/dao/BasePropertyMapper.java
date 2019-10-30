package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseProperty;
import com.xq.live.globalVoyage$.model.BasePropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePropertyMapper {
    long countByExample(BasePropertyExample example);

    int deleteByExample(BasePropertyExample example);

    int insert(BaseProperty record);

    int insertSelective(BaseProperty record);

    List<BaseProperty> selectByExample(BasePropertyExample example);

    int updateByExampleSelective(@Param("record") BaseProperty record, @Param("example") BasePropertyExample example);

    int updateByExample(@Param("record") BaseProperty record, @Param("example") BasePropertyExample example);
}