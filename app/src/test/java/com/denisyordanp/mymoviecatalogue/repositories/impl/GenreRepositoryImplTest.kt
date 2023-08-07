package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.GenreRepository
import com.denisyordanp.mymoviecatalogue.schemas.response.Genres
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class GenreRepositoryImplTest {
    private lateinit var underTest: GenreRepository

    private val mockedService = mock<MovieService>()
    private val mockedGenreDao = mock<GenresDao>()

    @Before
    fun setup() {
        underTest = GenreRepositoryImpl(mockedService, mockedGenreDao)
    }

    @Test
    fun `reloadGenres should call fetchGenres and then insertGenres`() = runTest {
        val dummy = Genres(genres = Dummy.getResponseGenres())

        whenever(mockedService.fetchGenres(any())).thenReturn(dummy)

        underTest.reloadGenres()

        verify(mockedService).fetchGenres()
        verify(mockedGenreDao).insertGenres(any())
    }

    @Test
    fun `reloadGenres should throw error when fetchGenres error`() = runTest {
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchGenres(any())).thenThrow(expected)

        try {
            underTest.reloadGenres()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchGenres()
        verify(mockedGenreDao, never()).insertGenres(any())
    }

    @Test
    fun `reloadGenres should throw error when insertGenres error`() = runTest {
        val expected = MockitoKotlinException(null, null)
        val dummy = Genres(genres = Dummy.getResponseGenres())

        whenever(mockedService.fetchGenres(any())).thenReturn(dummy)
        whenever(mockedGenreDao.insertGenres(any())).thenThrow(expected)

        try {
            underTest.reloadGenres()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchGenres()
        verify(mockedGenreDao).insertGenres(any())
    }

    @Test
    fun `getGenres should return mapped genres`() = runTest {
        val dummy = Dummy.getDbGenres()
        val expected = dummy.map { it.toUi() }

        whenever(mockedGenreDao.getGenres()).thenReturn(flowOf(dummy))

        val actual = underTest.getGenres().first()
        assertEquals(expected, actual)

        verify(mockedGenreDao).getGenres()
    }

    @Test
    fun `getGenres should return error when dao getGenres error`() = runTest {
        val expected = MockitoKotlinException(null, null)

        whenever(mockedGenreDao.getGenres()).thenThrow(expected)

        try {
            underTest.getGenres()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedGenreDao).getGenres()
    }
}
