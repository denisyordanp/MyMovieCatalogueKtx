package com.denisyordanp.mymoviecatalogue.ui.screens.favorite

import app.cash.turbine.test
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class FavoriteViewModelTest {
    private lateinit var underTest: FavoriteViewModel
    private val mockedGetFavorite = mock<GetFavorites>()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        underTest = FavoriteViewModel(mockedGetFavorite)
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `favorites should return GetFavorites`() = runTest {
        val expected1 = Dummy.getUiFavorites().map {
            it.copy(
                id = 1
            )
        }
        val expected2 = Dummy.getUiFavorites().map {
            it.copy(
                id = 2
            )
        }

        whenever(mockedGetFavorite.invoke())
            .thenReturn(flow {
                emit(expected1)
                emit(expected2)
            })

        underTest.activeFavorites.test {
            val firstItem = awaitItem()
            assertEquals(expected1, firstItem)

            val secondItem = awaitItem()
            assertEquals(expected2, secondItem)

            awaitComplete()

            verify(mockedGetFavorite).invoke()
        }
    }
}
