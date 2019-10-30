package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseManagerDepartment;
import com.xq.live.globalVoyage$.model.BaseManagerDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseManagerDepartmentMapper {
    long countByExample(BaseManagerDepartmentExample example);

    int deleteByExample(BaseManagerDepartmentExample example);

    int insert(BaseManagerDepartment record);

    int insertSelective(BaseManagerDepartment record);

    List<BaseManagerDepartment> selectByExample(BaseManagerDepartmentExample example);

    int updateByExampleSelective(@Param("record") BaseManagerDepartment record, @Param("example") BaseManagerDepartmentExample example);

    int updateByExample(@Param("record") BaseManagerDepartment record, @Param("example") BaseManagerDepartmentExample example);
}