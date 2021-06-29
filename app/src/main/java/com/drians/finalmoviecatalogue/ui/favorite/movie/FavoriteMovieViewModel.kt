package com.drians.finalmoviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity

class FavoriteMovieViewModel (private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getFavorites(): LiveData<PagedList<MovieEntity>> = movieTvRepository.getFavoriteMovie()

    fun setFavorite(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorited
        movieTvRepository.setFavoriteMovie(movieEntity, newState)
    }
}