package com.drians.finalmoviecatalogue.data.remote.api

import com.drians.finalmoviecatalogue.data.remote.response.MovieDetailResponse
import com.drians.finalmoviecatalogue.data.remote.response.MovieResponse
import com.drians.finalmoviecatalogue.data.remote.response.TvDetailResponse
import com.drians.finalmoviecatalogue.data.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>

    @GET("tv/popular")
    fun getPopularTvs(@Query("api_key") apiKey: String): Call<TvResponse>

    @GET("tv/{id}")
    fun getTvDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<TvDetailResponse>

}