package com.drians.finalmoviecatalogue.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1, exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {

    abstract fun movieTvDAO(): MovieTvDAO

    companion object {

        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvDatabase::class.java,
                    "movie.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}