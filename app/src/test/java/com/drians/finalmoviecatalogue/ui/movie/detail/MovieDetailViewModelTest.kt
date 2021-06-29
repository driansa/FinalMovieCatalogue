package com.drians.finalmoviecatalogue.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
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
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val id = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(movieTvRepository)
        id?.let { viewModel.setSelectedMovie(it) }
    }

    @Test
    fun `getMovieDetail should be success`() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(id?.let { movieTvRepository.getMovieDetail(it) }).thenReturn(expected)

        viewModel.movieDetail.observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieDetail.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `setFavorite should be success`() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(id?.let { movieTvRepository.getMovieDetail(it) }).thenReturn(expected)

        viewModel.setFavorite()
        viewModel.movieDetail.observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieDetail.value

        assertEquals(expectedValue, actualValue)
    }
}