package com.drians.finalmoviecatalogue.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.vo.Resource

class MovieDetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    val id = MutableLiveData<Int>()

    fun setSelectedMovie(id: Int) {
        this.id.value = id
    }

    var movieDetail: LiveData<Resource<MovieEntity>> = Transformations.switchMap(id) {
        movieTvRepository.getMovieDetail(it)
    }

    fun setFavorite() {
        val movieResource = movieDetail.value
        if (movieResource?.data != null) {
            val newState = !movieResource.data.favorited
            movieTvRepository.setFavoriteMovie(movieResource.data, newState)
        }
    }
}