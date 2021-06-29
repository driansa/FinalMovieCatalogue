package com.drians.finalmoviecatalogue.ui.tv.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.vo.Resource

class TvDetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    val id = MutableLiveData<Int>()

    fun setSelectedTv(id: Int) {
        this.id.value = id
    }

    var tvDetail: LiveData<Resource<TvEntity>> = Transformations.switchMap(id) {
        movieTvRepository.getTvDetail(it)
    }

    fun setFavorite() {
        val tvResource = tvDetail.value
        if (tvResource?.data != null) {
            val newState = !tvResource.data.favorited
            movieTvRepository.setFavoriteTv(tvResource.data, newState)
        }
    }
}