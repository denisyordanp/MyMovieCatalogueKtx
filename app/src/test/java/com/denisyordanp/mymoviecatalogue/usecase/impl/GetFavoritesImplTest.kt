package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class GetFavoritesImplTest {
    private lateinit var underTest: GetFavorites
    private val mockedFavoriteRepository = mock<FavoriteRepository>()

    @Before
    fun setup() {
        underTest = GetFavoritesImpl(mockedFavoriteRepository)
    }

    @Test
    fun `invoke should return favorites ui`() = runTest {
        val dummy = Dummy.getDbFavorites()
        val expected = dummy.map { it.toUi() }

        whenever(mockedFavoriteRepository.getFavorites()).thenReturn(flowOf(dummy))

        val actual = underTest().first()

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke should throw error when getFavorites error`() {
        val expected = MockitoKotlinException(null, null)

        whenever(mockedFavoriteRepository.getFavorites()).thenThrow(expected)

        try {
            underTest()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }
}
