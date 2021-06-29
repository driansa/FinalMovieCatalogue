package com.drians.finalmoviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity

@Dao
interface MovieTvDAO {

    @Query("SELECT * FROM favorite_movie")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM favorite_movie WHERE favorited = 1")
    fun getFavoritedMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM favorite_movie WHERE id = :id")
    fun getMovieId(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM favorite_tv")
    fun getTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM favorite_tv WHERE favorited = 1")
    fun getFavoritedTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM favorite_tv WHERE id = :id")
    fun getTvId(id: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvs(tvs: List<TvEntity>)

    @Update
    fun updateTv(tv: TvEntity)
}