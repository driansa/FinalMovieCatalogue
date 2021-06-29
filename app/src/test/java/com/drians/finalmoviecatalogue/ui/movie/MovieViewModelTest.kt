package com.drians.finalmoviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.utils.DataDummy
import com.drians.finalmoviecatalogue.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieTvRepository)
    }

    @Test
    fun `getPopularMovies should be success`() {
        val movies = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(movies)

        `when`(movieTvRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `getPopularMovies should be success but data is empty`() {
        val movies = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(movies)

        `when`(movieTvRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualValueDataSize = viewModel.getMovies().value?.data?.size
        assertTrue(
            "size of data should be 0, actual is $actualValueDataSize",
            actualValueDataSize == 0
        )
    }

    @Test
    fun `getPopularMovies should be error`() {
        val expectedMessage = "Something happen guys!"
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(movieTvRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getMovies().value?.message
        assertEquals(expectedMessage, actualMessage)
    }

    class PagedTestDataSources private constructor(private val items: List<MovieEntity>) :
        PositionalDataSource<MovieEntity>() {
        companion object {
            fun snapshot(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MovieEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}