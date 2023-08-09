package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.FavoriteRepository
import com.denisyordanp.mymoviecatalogue.usecase.ChangeFavorite
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class ChangeFavoriteImplTest {
    private lateinit var underTest: ChangeFavorite
    private val mockedFavoriteRepository = mock<FavoriteRepository>()

    @Before
    fun setup() {
        underTest = ChangeFavoriteImpl(mockedFavoriteRepository)
    }

    @Test
    fun `invoke should call remove when isExist true`() = runTest {
        val dummy = Dummy.getUiFavorites().first()
        val dummyDb = dummy.toDb()
        val isExist = true

        whenever(mockedFavoriteRepository.isExist(dummyDb)).thenReturn(isExist)

        underTest.invoke(dummy)

        verify(mockedFavoriteRepository).isExist(dummyDb)
        verify(mockedFavoriteRepository).remove(dummyDb.id)
        verify(mockedFavoriteRepository, never()).add(dummyDb)
    }

    @Test
    fun `invoke should call add when isExist false`() = runTest {
        val dummy = Dummy.getUiFavorites().first()
        val dummyDb = dummy.toDb()
        val isExist = false

        whenever(mockedFavoriteRepository.isExist(dummyDb)).thenReturn(isExist)

        underTest.invoke(dummy)

        verify(mockedFavoriteRepository).isExist(dummyDb)
        verify(mockedFavoriteRepository, never()).remove(dummyDb.id)
        verify(mockedFavoriteRepository).add(dummyDb)
    }

    @Test
    fun `invoke should throw error when isExist error`() = runTest {
        val dummy = Dummy.getUiFavorites().first()
        val dummyDb = dummy.toDb()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedFavoriteRepository.isExist(dummyDb)).thenThrow(expected)

        try {
            underTest.invoke(dummy)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedFavoriteRepository).isExist(dummyDb)
        verify(mockedFavoriteRepository, never()).remove(dummyDb.id)
        verify(mockedFavoriteRepository, never()).add(dummyDb)
    }

    @Test
    fun `invoke should throw error when remove error`() = runTest {
        val dummy = Dummy.getUiFavorites().first()
        val dummyDb = dummy.toDb()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedFavoriteRepository.isExist(dummyDb)).thenReturn(true)
        whenever(mockedFavoriteRepository.remove(dummyDb.id)).thenThrow(expected)

        try {
            underTest.invoke(dummy)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedFavoriteRepository).isExist(dummyDb)
        verify(mockedFavoriteRepository).remove(dummyDb.id)
        verify(mockedFavoriteRepository, never()).add(dummyDb)
    }

    @Test
    fun `invoke should throw error when add error`() = runTest {
        val dummy = Dummy.getUiFavorites().first()
        val dummyDb = dummy.toDb()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedFavoriteRepository.isExist(dummyDb)).thenReturn(false)
        whenever(mockedFavoriteRepository.add(dummyDb)).thenThrow(expected)

        try {
            underTest.invoke(dummy)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedFavoriteRepository).isExist(dummyDb)
        verify(mockedFavoriteRepository, never()).remove(dummyDb.id)
        verify(mockedFavoriteRepository).add(dummyDb)
    }
}
