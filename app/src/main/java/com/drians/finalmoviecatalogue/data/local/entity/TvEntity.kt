package com.drians.finalmoviecatalogue.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorite_tv")
data class TvEntity(

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false

)