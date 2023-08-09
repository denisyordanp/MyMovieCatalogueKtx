package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.paging.PagingData
import app.cash.turbine.test
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.usecase.ChangeFavorite
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailViewModelTest {
    private lateinit var underTest: DetailViewModel
    private val mockedGetMovieDetail = mock<GetMovieDetail>()
    private val mockedGetVideos = mock<GetVideos>()
    private val mockedChangeFavorite = mock<ChangeFavorite>()
    private val mockedGetReviews = mock<GetReviews>()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        underTest = DetailViewModel(
            getMovieDetail = mockedGetMovieDetail,
            getVideos = mockedGetVideos,
            getReviews = mockedGetReviews,
            changeFavorite = mockedChangeFavorite
        )
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addMovieToFavorite should call changeFavorite when movie detail not null`() = runTest {
        val dummyId = Random.nextLong()
        val dummyIsForce = Random.nextBoolean()
        val dummyDetail = Dummy.getUiMovieDetail()
        val dummyFavorite = dummyDetail.toFavorite()
        val dummyVideos = Dummy.getUiVideos()
        val expectedReviews = flowOf(PagingData.from(Dummy.getReviews()))

        whenever(mockedGetMovieDetail(dummyId, dummyIsForce)).thenReturn(flowOf(dummyDetail))
        whenever(mockedGetVideos(dummyId, dummyIsForce)).thenReturn(flowOf(dummyVideos))
        whenever(mockedGetReviews(dummyId, dummyIsForce)).thenReturn(expectedReviews)

        underTest.viewState.test {
            val initial = awaitItem()
            assertEquals(DetailViewState.idle(), initial)

            underTest.loadMovieDetail(dummyId, dummyIsForce)

            val detail = awaitItem()
            assertTrue(detail.movieDetail != null)

            underTest.addMovieToFavorite()

            verify(mockedGetMovieDetail).invoke(dummyId, dummyIsForce)
            verify(mockedGetVideos).invoke(dummyId, dummyIsForce)
            verify(mockedGetReviews).invoke(dummyId, dummyIsForce)
            verify(mockedChangeFavorite).invoke(dummyFavorite)
        }
    }

    @Test
    fun `addMovieToFavorite should not call changeFavorite when movie detail null`() = runTest {
        val dummyDetail = Dummy.getUiMovieDetail()
        val dummyFavorite = dummyDetail.toFavorite()

        underTest.addMovieToFavorite()

        verify(mockedChangeFavorite, never()).invoke(dummyFavorite)
    }

    @Test
    fun `loadMovieDetail should emit new viewState data`() = runTest {
        val dummyId = Random.nextLong()
        val dummyIsForce = Random.nextBoolean()
        val dummyDetail = Dummy.getUiMovieDetail()
        val dummyVideos = Dummy.getUiVideos()
        val expectedReviews = flowOf(PagingData.from(Dummy.getReviews()))
        val expected = DetailViewState.idle().copy(
            movieDetail = dummyDetail,
            videosViewState = VideosViewState.idle().copy(
                videos = dummyVideos
            ),
            reviews = expectedReviews
        )

        whenever(mockedGetMovieDetail(dummyId, dummyIsForce)).thenReturn(flowOf(dummyDetail))
        whenever(mockedGetVideos(dummyId, dummyIsForce)).thenReturn(flowOf(dummyVideos))
        whenever(mockedGetReviews(dummyId, dummyIsForce)).thenReturn(expectedReviews)

        underTest.viewState.test {
            val initial = awaitItem()
            assertEquals(DetailViewState.idle(), initial)

            underTest.loadMovieDetail(dummyId, dummyIsForce)

            val actual = awaitItem()
            assertEquals(expected, actual)

            verify(mockedGetMovieDetail).invoke(dummyId, dummyIsForce)
            verify(mockedGetVideos).invoke(dummyId, dummyIsForce)
            verify(mockedGetReviews).invoke(dummyId, dummyIsForce)
        }
    }

    @Test
    fun `loadVideos should emit new viewState data`() = runTest {
        val dummyId = Random.nextLong()
        val dummyIsForce = Random.nextBoolean()
        val dummyVideos = Dummy.getUiVideos()
        val expected = DetailViewState.idle().copy(
            videosViewState = VideosViewState.idle().copy(
                videos = dummyVideos
            ),
        )

        whenever(mockedGetVideos(dummyId, dummyIsForce)).thenReturn(flowOf(dummyVideos))

        underTest.viewState.test {
            val initial = awaitItem()
            assertEquals(DetailViewState.idle(), initial)

            underTest.loadVideos(dummyId, dummyIsForce)

            val actual = awaitItem()
            assertEquals(expected, actual)

            verify(mockedGetVideos).invoke(dummyId, dummyIsForce)
        }
    }
}
