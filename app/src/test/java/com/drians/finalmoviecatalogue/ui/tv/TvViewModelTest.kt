package com.drians.finalmoviecatalogue.ui.tv


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.drians.finalmoviecatalogue.data.MovieTvRepository
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
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
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieTvRepository)
    }

    @Test
    fun `getPopularTvs should be success`() {
        val tvs = PagedTestDataSources.snapshot(DataDummy.generateDummyTvs())
        val expected = MutableLiveData<Resource<PagedList<TvEntity>>>()
        expected.value = Resource.success(tvs)

        `when`(movieTvRepository.getPopularTvs()).thenReturn(expected)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTvs().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `getPopularTvs should be success but data is empty`() {
        val tvs = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<TvEntity>>>()
        expected.value = Resource.success(tvs)

        `when`(movieTvRepository.getPopularTvs()).thenReturn(expected)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualValueDataSize = viewModel.getTvs().value?.data?.size
        assertTrue(
            "size of data should be 0, actual is $actualValueDataSize",
            actualValueDataSize == 0
        )
    }

    @Test
    fun `getPopularTvs should be error`() {
        val expectedMessage = "Something happen guys!"
        val expected = MutableLiveData<Resource<PagedList<TvEntity>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(movieTvRepository.getPopularTvs()).thenReturn(expected)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getTvs().value?.message
        assertEquals(expectedMessage, actualMessage)
    }

    class PagedTestDataSources private constructor(private val items: List<TvEntity>) :
        PositionalDataSource<TvEntity>() {
        companion object {
            fun snapshot(items: List<TvEntity> = listOf()): PagedList<TvEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TvEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}