package com.xq.live.globalVoyage$.dao;

import com.xq.live.globalVoyage$.model.UserFavorites;
import com.xq.live.globalVoyage$.model.UserFavoritesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFavoritesMapper {
    long countByExample(UserFavoritesExample example);

    int deleteByExample(UserFavoritesExample example);

    int insert(UserFavorites record);

    int insertSelective(UserFavorites record);

    List<UserFavorites> selectByExample(UserFavoritesExample example);

    int updateByExampleSelective(@Param("record") UserFavorites record, @Param("example") UserFavoritesExample example);

    int updateByExample(@Param("record") UserFavorites record, @Param("example") UserFavoritesExample example);
}