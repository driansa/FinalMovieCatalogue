package com.drians.finalmoviecatalogue.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity

class FavoriteTvViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getFavorites(): LiveData<PagedList<TvEntity>> = movieTvRepository.getFavoriteTv()

    fun setFavorite(tvEntity: TvEntity) {
        val newState = !tvEntity.favorited
        movieTvRepository.setFavoriteTv(tvEntity, newState)
    }
}