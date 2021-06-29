package com.drians.finalmoviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.vo.Resource

interface MovieTvDataSource {
    fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>>
    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>
    fun getPopularTvs(): LiveData<Resource<PagedList<TvEntity>>>
    fun getTvDetail(id: Int): LiveData<Resource<TvEntity>>
    fun setFavoriteTv(tv: TvEntity, state: Boolean)
    fun getFavoriteTv(): LiveData<PagedList<TvEntity>>
}