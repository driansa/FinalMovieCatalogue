package com.drians.finalmoviecatalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.data.local.room.MovieTvDAO

class LocalDataSource private constructor(private val mMovieTvDAO: MovieTvDAO) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDAO: MovieTvDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDAO).apply {
                INSTANCE = this
            }

    }

    fun getDataMovies(): DataSource.Factory<Int, MovieEntity> = mMovieTvDAO.getMovie()

    fun getFavoritedMovie(): DataSource.Factory<Int, MovieEntity> = mMovieTvDAO.getFavoritedMovie()

    fun getMovieId(id: Int): LiveData<MovieEntity> = mMovieTvDAO.getMovieId(id)

    fun insertMovies(movies: List<MovieEntity>) = mMovieTvDAO.insertMovies(movies)

    fun updateFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mMovieTvDAO.updateMovie(movie)
    }

    fun getDataTvs(): DataSource.Factory<Int, TvEntity> = mMovieTvDAO.getTv()

    fun getFavoritedTv(): DataSource.Factory<Int, TvEntity> = mMovieTvDAO.getFavoritedTv()

    fun getTvId(id: Int): LiveData<TvEntity> = mMovieTvDAO.getTvId(id)

    fun insertTvs(tvs: List<TvEntity>) = mMovieTvDAO.insertTvs(tvs)

    fun updateFavoriteTv(tv: TvEntity, newState: Boolean) {
        tv.favorited = newState
        mMovieTvDAO.updateTv(tv)
    }
}