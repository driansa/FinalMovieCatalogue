package com.drians.finalmoviecatalogue.di

import android.content.Context
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.LocalDataSource
import com.drians.finalmoviecatalogue.data.local.room.MovieTvDatabase
import com.drians.finalmoviecatalogue.data.remote.RemoteDataSource
import com.drians.finalmoviecatalogue.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieTvRepository {

        val database = MovieTvDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieTvDAO())
        val appExecutors = AppExecutors()

        return MovieTvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}