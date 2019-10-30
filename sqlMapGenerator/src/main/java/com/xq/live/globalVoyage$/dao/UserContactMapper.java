package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.UserContact;
import com.xq.live.globalVoyage$.model.UserContactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactMapper {
    long countByExample(UserContactExample example);

    int deleteByExample(UserContactExample example);

    int insert(UserContact record);

    int insertSelective(UserContact record);

    List<UserContact> selectByExample(UserContactExample example);

    int updateByExampleSelective(@Param("record") UserContact record, @Param("example") UserContactExample example);

    int updateByExample(@Param("record") UserContact record, @Param("example") UserContactExample example);
}