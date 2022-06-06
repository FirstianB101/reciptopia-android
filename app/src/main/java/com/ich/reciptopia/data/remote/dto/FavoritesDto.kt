package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Favorite

data class FavoritesDto(
    val favorites: LinkedHashMap<String, FavoriteDto>
)

fun FavoritesDto.toFavoriteList(): List<Favorite>{
    val list = mutableListOf<Favorite>()
    favorites.keys.forEach {
        list.add(favorites[it]!!.toFavorite())
    }
    return list
}