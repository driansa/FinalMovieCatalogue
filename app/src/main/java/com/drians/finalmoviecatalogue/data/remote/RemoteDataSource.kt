package com.drians.finalmoviecatalogue.data.remote


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.drians.finalmoviecatalogue.BuildConfig.TMDB_API_KEY
import com.drians.finalmoviecatalogue.data.remote.api.ApiConfig
import com.drians.finalmoviecatalogue.data.remote.response.*
import com.drians.finalmoviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getPopularMovies(): LiveData<ApiResponse<List<ResultMovie>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<ResultMovie>>>()
        ApiConfig.getApiService().getPopularMovies(TMDB_API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d(this@RemoteDataSource.toString(), "Get Popular Movie Success")
                    resultMovie.value =
                        ApiResponse.success(response.body()?.results as List<ResultMovie>)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(
                        this@RemoteDataSource.toString(),
                        "Get Popular Movie onFailure: ${t.message}"
                    )
                    EspressoIdlingResource.decrement()
                }
            })
        return resultMovie
    }

    fun getPopularTvs(): LiveData<ApiResponse<List<ResultTv>>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<List<ResultTv>>>()
        ApiConfig.getApiService().getPopularTvs(TMDB_API_KEY)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(
                    call: Call<TvResponse>,
                    response: Response<TvResponse>
                ) {
                    Log.d(this@RemoteDataSource.toString(), "Get Popular TV Success")
                    resultTv.value = ApiResponse.success(response.body()?.results as List<ResultTv>)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    Log.e(
                        this@RemoteDataSource.toString(),
                        "Get Popular TV onFailure: ${t.message}"
                    )
                    EspressoIdlingResource.decrement()
                }
            })
        return resultTv
    }

    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        ApiConfig.getApiService().getMovieDetail(id, TMDB_API_KEY)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    Log.d(this@RemoteDataSource.toString(), "Get Movie Detail Success")
                    resultMovieDetail.value =
                        ApiResponse.success(response.body() as MovieDetailResponse)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    Log.e(
                        this@RemoteDataSource.toString(),
                        "Get Movie Detail onFailure: ${t.message}"
                    )
                    EspressoIdlingResource.decrement()
                }
            })
        return resultMovieDetail
    }

    fun getTvDetail(id: Int): LiveData<ApiResponse<TvDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultTvDetail = MutableLiveData<ApiResponse<TvDetailResponse>>()
        ApiConfig.getApiService().getTvDetail(id, TMDB_API_KEY)
            .enqueue(object : Callback<TvDetailResponse> {
                override fun onResponse(
                    call: Call<TvDetailResponse>,
                    response: Response<TvDetailResponse>
                ) {
                    Log.d(this@RemoteDataSource.toString(), "Get Tv Detail Success")
                    resultTvDetail.value =
                        ApiResponse.success(response.body() as TvDetailResponse)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                    Log.e(this@RemoteDataSource.toString(), "Get Tv Detail onFailure: ${t.message}")
                    EspressoIdlingResource.decrement()
                }
            })
        return resultTvDetail
    }
}