package com.drians.finalmoviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.vo.Resource

class TvViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getTvs(): LiveData<Resource<PagedList<TvEntity>>> = movieTvRepository.getPopularTvs()
}