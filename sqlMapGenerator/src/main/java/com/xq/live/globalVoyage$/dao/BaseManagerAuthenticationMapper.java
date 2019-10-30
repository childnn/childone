package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.BaseManagerAuthentication;
import com.xq.live.globalVoyage$.model.BaseManagerAuthenticationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseManagerAuthenticationMapper {
    long countByExample(BaseManagerAuthenticationExample example);

    int deleteByExample(BaseManagerAuthenticationExample example);

    int insert(BaseManagerAuthentication record);

    int insertSelective(BaseManagerAuthentication record);

    List<BaseManagerAuthentication> selectByExample(BaseManagerAuthenticationExample example);

    int updateByExampleSelective(@Param("record") BaseManagerAuthentication record, @Param("example") BaseManagerAuthenticationExample example);

    int updateByExample(@Param("record") BaseManagerAuthentication record, @Param("example") BaseManagerAuthenticationExample example);
}