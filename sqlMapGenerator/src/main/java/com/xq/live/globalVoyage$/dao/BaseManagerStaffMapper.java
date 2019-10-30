package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseManagerStaff;
import com.xq.live.globalVoyage$.model.BaseManagerStaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseManagerStaffMapper {
    long countByExample(BaseManagerStaffExample example);

    int deleteByExample(BaseManagerStaffExample example);

    int insert(BaseManagerStaff record);

    int insertSelective(BaseManagerStaff record);

    List<BaseManagerStaff> selectByExample(BaseManagerStaffExample example);

    int updateByExampleSelective(@Param("record") BaseManagerStaff record, @Param("example") BaseManagerStaffExample example);

    int updateByExample(@Param("record") BaseManagerStaff record, @Param("example") BaseManagerStaffExample example);
}