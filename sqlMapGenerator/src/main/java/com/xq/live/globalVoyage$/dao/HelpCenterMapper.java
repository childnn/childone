package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.HelpCenter;
import com.xq.live.globalVoyage$.model.HelpCenterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HelpCenterMapper {
    long countByExample(HelpCenterExample example);

    int deleteByExample(HelpCenterExample example);

    int insert(HelpCenter record);

    int insertSelective(HelpCenter record);

    List<HelpCenter> selectByExampleWithBLOBs(HelpCenterExample example);

    List<HelpCenter> selectByExample(HelpCenterExample example);

    int updateByExampleSelective(@Param("record") HelpCenter record, @Param("example") HelpCenterExample example);

    int updateByExampleWithBLOBs(@Param("record") HelpCenter record, @Param("example") HelpCenterExample example);

    int updateByExample(@Param("record") HelpCenter record, @Param("example") HelpCenterExample example);
}