package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class GetGenresImplTest {
    private lateinit var underTest: GetGenres
    private val mockedGenreRepository = mock<GenreRepository>()

    @Before
    fun setup() {
        underTest = GetGenresImpl(mockedGenreRepository)
    }

    @Test
    fun `invoke with isForce true should call reloadGenres and return expected data`() = runTest {
        val isForce = true
        val expected = Dummy.getDbGenres().map { it.toUi() }

        whenever(mockedGenreRepository.getGenres()).thenReturn(flowOf(expected))

        val actual = underTest(isForce).first()

        assertEquals(expected, actual)

        verify(mockedGenreRepository).reloadGenres()
    }

    @Test
    fun `invoke with isForce false should not call reloadGenres and return expected data`() = runTest {
        val isForce = false
        val expected = Dummy.getDbGenres().map { it.toUi() }

        whenever(mockedGenreRepository.getGenres()).thenReturn(flowOf(expected))

        val actual = underTest(isForce).first()

        assertEquals(expected, actual)

        verify(mockedGenreRepository, never()).reloadGenres()
    }

    @Test
    fun `invoke should call reloadGenres when data is empty`() = runTest {
        val isForce = false
        val expected = Dummy.getDbGenres().map { it.toUi() }

        whenever(mockedGenreRepository.getGenres())
            .thenReturn(flow {
                emit(emptyList())
                emit(expected)
            })

        val actual = underTest(isForce).last()

        assertEquals(expected, actual)

        verify(mockedGenreRepository).reloadGenres()
    }

    @Test
    fun `invoke should not call reloadGenres when data is not empty`() = runTest {
        val isForce = false
        val expected = Dummy.getDbGenres().map { it.toUi() }

        whenever(mockedGenreRepository.getGenres())
            .thenReturn(flowOf(expected))

        val actual = underTest(isForce).last()

        assertEquals(expected, actual)

        verify(mockedGenreRepository, never()).reloadGenres()
    }

    @Test
    fun `invoke should throw error when getGenres error`() = runTest {
        val isForce = true
        val expected = MockitoKotlinException(null, null)

        whenever(mockedGenreRepository.getGenres()).thenThrow(expected)

        try {
            underTest(isForce)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke with isForce true should throw error when reloadGenres error`() = runTest {
        val isForce = true
        val expected = MockitoKotlinException(null, null)

        whenever(mockedGenreRepository.reloadGenres()).thenThrow(expected)

        try {
            underTest(isForce)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke should throw error when data is empty and reloadGenres error`() = runTest {
        val isForce = false
        val expected = MockitoKotlinException(null,null)

        whenever(mockedGenreRepository.getGenres()).thenReturn(flowOf(emptyList()))
        whenever(mockedGenreRepository.reloadGenres()).thenThrow(expected)

        try {
            underTest(isForce)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }
    }
}
