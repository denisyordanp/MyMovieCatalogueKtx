package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.FavoritesDao
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

internal class FavoriteRepositoryImplTest {
    private lateinit var underTest: FavoriteRepository
    private val mockedFavoriteDao = mock<FavoritesDao>()

    @Before
    fun setup() {
        underTest = FavoriteRepositoryImpl(mockedFavoriteDao)
    }

    @Test
    fun `getFavorites should return expected data`() = runTest {
        val expected = Dummy.getDbFavorites()

        whenever(mockedFavoriteDao.getFavorites()).thenReturn(flowOf(expected))

        val actual = underTest.getFavorites().first()

        assertEquals(expected, actual)
    }

    @Test
    fun `getFavorites should return error when getFavorites dao error`() = runTest {
        val expected = MockitoKotlinException(null,null)

        whenever(mockedFavoriteDao.getFavorites()).thenThrow(expected)

        try {
            underTest.getFavorites().first()
        }catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `isExist should return true when exist`() = runTest {
        val dummy = Dummy.getDbFavorites().first()

        whenever(mockedFavoriteDao.getFavorite(dummy.id)).thenReturn(flowOf(dummy))

        val actual = underTest.isExist(dummy)

        assertTrue(actual)
    }

    @Test
    fun `isExist should return false when do not exist`() = runTest {
        val dummy = Dummy.getDbFavorites().first()

        whenever(mockedFavoriteDao.getFavorite(dummy.id)).thenReturn(flowOf(null))

        val actual = underTest.isExist(dummy)

        assertFalse(actual)
    }

    @Test
    fun `isExist should return error when getFavorite dao error`() = runTest {
        val dummy = Dummy.getDbFavorites().first()
        val expected = MockitoKotlinException(null,null)

        whenever(mockedFavoriteDao.getFavorite(any())).thenThrow(expected)

        try {
            underTest.isExist(dummy)
        }catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `add should call insertFavorite dao`() = runTest {
        val dummy = Dummy.getDbFavorites().first()

        underTest.add(dummy)

        verify(mockedFavoriteDao).insertFavorite(dummy)
    }

    @Test
    fun `add should return error when insertFavorite dao error`() = runTest {
        val dummy = Dummy.getDbFavorites().first()
        val expected = MockitoKotlinException(null,null)

        whenever(mockedFavoriteDao.insertFavorite(any())).thenThrow(expected)

        try {
            underTest.add(dummy)
        }catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `remove should call removeMovie dao`() = runTest {
        val dummy = Random.nextLong()

        underTest.remove(dummy)

        verify(mockedFavoriteDao).removeMovie(dummy)
    }

    @Test
    fun `remove should return error when removeMovie dao error`() = runTest {
        val dummy = Random.nextLong()
        val expected = MockitoKotlinException(null,null)

        whenever(mockedFavoriteDao.removeMovie(any())).thenThrow(expected)

        try {
            underTest.remove(dummy)
        }catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }
}
