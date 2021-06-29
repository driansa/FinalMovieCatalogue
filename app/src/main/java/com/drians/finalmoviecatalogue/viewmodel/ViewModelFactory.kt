package com.drians.finalmoviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.di.Injection
import com.drians.finalmoviecatalogue.ui.favorite.movie.FavoriteMovieViewModel
import com.drians.finalmoviecatalogue.ui.favorite.tv.FavoriteTvViewModel
import com.drians.finalmoviecatalogue.ui.movie.MovieViewModel
import com.drians.finalmoviecatalogue.ui.movie.detail.MovieDetailViewModel
import com.drians.finalmoviecatalogue.ui.tv.TvViewModel
import com.drians.finalmoviecatalogue.ui.tv.detail.TvDetailViewModel

class ViewModelFactory private constructor(private val movieTvRepository: MovieTvRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(TvDetailViewModel::class.java) -> {
                TvDetailViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                FavoriteTvViewModel(movieTvRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}