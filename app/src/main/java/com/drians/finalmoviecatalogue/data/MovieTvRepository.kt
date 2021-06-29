package com.drians.finalmoviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.local.LocalDataSource
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.data.remote.ApiResponse
import com.drians.finalmoviecatalogue.data.remote.RemoteDataSource
import com.drians.finalmoviecatalogue.data.remote.response.MovieDetailResponse
import com.drians.finalmoviecatalogue.data.remote.response.ResultMovie
import com.drians.finalmoviecatalogue.data.remote.response.ResultTv
import com.drians.finalmoviecatalogue.data.remote.response.TvDetailResponse
import com.drians.finalmoviecatalogue.utils.AppExecutors
import com.drians.finalmoviecatalogue.vo.Resource

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieTvDataSource {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors
        ): MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(
                    remoteData,
                    localData,
                    appExecutors
                ).apply { instance = this }
            }
    }

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ResultMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(1)
                    .setPrefetchDistance(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getDataMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultMovie>>> =
                remoteDataSource.getPopularMovies()

            override fun saveCallResult(data: List<ResultMovie>) {
                val listMovie = ArrayList<MovieEntity>()
                for (response in data) {
                    response.apply {
                        val movie = MovieEntity(
                            id, title, overview, releaseDate, voteAverage, posterPath
                        )
                        listMovie.add(movie)
                    }
                }
                localDataSource.insertMovies(listMovie)
            }

        }.asLiveData()
    }

    override fun getPopularTvs(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<ResultTv>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(1)
                    .setPrefetchDistance(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getDataTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultTv>>> =
                remoteDataSource.getPopularTvs()

            override fun saveCallResult(data: List<ResultTv>) {
                val listTv = ArrayList<TvEntity>()
                for (response in data) {
                    response.apply {
                        val tv = TvEntity(
                            id, name, overview, firstAirDate, voteAverage, posterPath
                        )
                        listTv.add(tv)
                    }
                }
                localDataSource.insertTvs(listTv)
            }

        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieId(id)

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(data: MovieDetailResponse) {
                with(data) {
                    val movieDetail = MovieEntity(
                        id = id,
                        title = title,
                        overview = overview,
                        releaseDate = releaseDate,
                        voteAverage = voteAverage,
                        posterPath = posterPath,
                        favorited = false
                    )
                    localDataSource.updateFavoriteMovie(movieDetail, false)
                }
            }

        }.asLiveData()
    }

    override fun getTvDetail(id: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvDetailResponse>(appExecutors) {
            override fun shouldFetch(data: TvEntity?): Boolean =
                data == null

            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getTvId(id)

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse>> =
                remoteDataSource.getTvDetail(id)

            override fun saveCallResult(data: TvDetailResponse) {
                with(data) {
                    val tvDetail = TvEntity(
                        id = id,
                        name = name,
                        overview = overview,
                        firstAirDate = firstAirDate,
                        voteAverage = voteAverage,
                        posterPath = posterPath,
                        favorited = false
                    )
                    localDataSource.updateFavoriteTv(tvDetail, false)
                }
            }

        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.updateFavoriteMovie(movie, state)
        }

    override fun setFavoriteTv(tv: TvEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.updateFavoriteTv(tv, state)
        }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(1)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedMovie(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(1)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedTv(), config).build()
    }
}