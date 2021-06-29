package com.drians.finalmoviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.vo.Resource

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieTvRepository.getPopularMovies()
}