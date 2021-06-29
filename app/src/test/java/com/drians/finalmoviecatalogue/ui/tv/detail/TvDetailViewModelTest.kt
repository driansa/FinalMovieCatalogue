package com.drians.finalmoviecatalogue.ui.tv.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.utils.DataDummy
import com.drians.finalmoviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvDetailViewModelTest {

    private lateinit var viewModel: TvDetailViewModel
    private val dummyTv = DataDummy.generateDummyTvs()[0]
    private val id = dummyTv.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvEntity>>

    @Before
    fun setUp() {
        viewModel = TvDetailViewModel(movieTvRepository)
        id?.let { viewModel.setSelectedTv(it) }
    }

    @Test
    fun `getTvDetail should be success`() {
        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(dummyTv)

        `when`(id?.let { movieTvRepository.getTvDetail(it) }).thenReturn(expected)

        viewModel.tvDetail.observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvDetail.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `setFavorite should be success`() {
        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(dummyTv)

        `when`(id?.let { movieTvRepository.getTvDetail(it) }).thenReturn(expected)

        viewModel.setFavorite()
        viewModel.tvDetail.observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvDetail.value

        assertEquals(expectedValue, actualValue)
    }
}