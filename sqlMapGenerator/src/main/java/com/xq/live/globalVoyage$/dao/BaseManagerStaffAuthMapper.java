package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseManagerStaffAuth;
import com.xq.live.globalVoyage$.model.BaseManagerStaffAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseManagerStaffAuthMapper {
    long countByExample(BaseManagerStaffAuthExample example);

    int deleteByExample(BaseManagerStaffAuthExample example);

    int insert(BaseManagerStaffAuth record);

    int insertSelective(BaseManagerStaffAuth record);

    List<BaseManagerStaffAuth> selectByExample(BaseManagerStaffAuthExample example);

    int updateByExampleSelective(@Param("record") BaseManagerStaffAuth record, @Param("example") BaseManagerStaffAuthExample example);

    int updateByExample(@Param("record") BaseManagerStaffAuth record, @Param("example") BaseManagerStaffAuthExample example);
}