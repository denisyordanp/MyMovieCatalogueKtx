package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.paging.PagingData
import app.cash.turbine.test
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import com.nhaarman.mockitokotlin2.mock
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
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {
    private lateinit var underTest: MainViewModel
    private val mockedGetGenres = mock<GetGenres>()
    private val mockedGetMovies = mock<GetMovies>()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        underTest = MainViewModel(mockedGetGenres, mockedGetMovies)
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGenres should emit new viewState data`() = runTest {
        val dummyIsForce = Random.nextBoolean()
        val dummyGenres = Dummy.getUiGenres()
        val dummySelectedGenre = dummyGenres.first()
        val dummyMovies = flowOf(PagingData.from(Dummy.getUiMovies()))
        val expected = MainViewState.idle().copy(
            selectedGenre = dummySelectedGenre,
            genres = dummyGenres,
            movies = dummyMovies
        )

        whenever(mockedGetGenres.invoke(dummyIsForce)).thenReturn(flowOf(dummyGenres))
        whenever(mockedGetMovies.invoke(dummySelectedGenre.id, dummyIsForce)).thenReturn(dummyMovies)

        underTest.viewState.test {
            val initial = awaitItem()
            assertEquals(MainViewState.idle(), initial)

            underTest.loadGenres(dummyIsForce)

            val actual = awaitItem()
            assertEquals(expected, actual)

            verify(mockedGetGenres).invoke(dummyIsForce)
            verify(mockedGetMovies).invoke(dummySelectedGenre.id, dummyIsForce)
        }
    }

    @Test
    fun `selectGenre should emit new viewState data`() = runTest {
        val dummySelectedGenre = Dummy.getUiGenres().first()
        val dummyMovies = flowOf(PagingData.from(Dummy.getUiMovies()))
        val expected = MainViewState.idle().copy(
            selectedGenre = dummySelectedGenre,
            movies = dummyMovies
        )

        whenever(mockedGetMovies.invoke(dummySelectedGenre.id, false)).thenReturn(dummyMovies)

        underTest.viewState.test {
            val initial = awaitItem()
            assertEquals(MainViewState.idle(), initial)

            underTest.selectGenre(dummySelectedGenre)

            val actual = awaitItem()
            assertEquals(expected, actual)

            verify(mockedGetMovies).invoke(dummySelectedGenre.id, false)
        }
    }
}
