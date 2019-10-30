package com.xq.live.globalVoyage.dao;

import com.xq.live.globalVoyage.entity.UserFavorites;

public interface UserFavoritesMapper {
    int insert(UserFavorites record);

    int insertSelective(UserFavorites record);
}