package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.GenresDao
import com.denisyordanp.mymoviecatalogue.database.MovieDetailDao
import com.denisyordanp.mymoviecatalogue.database.MovieGenreDao
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieGenre
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.MovieWithGenres
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
import kotlin.random.Random

internal class MovieDetailRepositoryImplTest {
    private lateinit var underTest: MovieDetailRepository

    private val mockedService = mock<MovieService>()
    private val mockedGenreDao = mock<GenresDao>()
    private val mockedMovieDetailDao = mock<MovieDetailDao>()
    private val mockedMovieGenreDao = mock<MovieGenreDao>()

    @Before
    fun setup() {
        underTest = MovieDetailRepositoryImpl(
            mockedService,
            mockedMovieDetailDao,
            mockedGenreDao,
            mockedMovieGenreDao
        )
    }

    @Test
    fun `reloadMovieDetail should call fetchMovieDetail insertMovieDetail insertGenres insertMovieGenres`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getResponseMovieDetail()
        val expected = dummyDetail.toEntity()
        val expectedMovieGenres = expected.second.map { it.toMovieGenre(dummyId) }

        whenever(mockedService.fetchMovieDetail(movieId = dummyId)).thenReturn(dummyDetail)

        underTest.reloadMovieDetail(dummyId)

        verify(mockedService).fetchMovieDetail(dummyId)
        verify(mockedMovieDetailDao).insertMovieDetail(expected.first)
        verify(mockedGenreDao).insertGenres(expected.second)
        verify(mockedMovieGenreDao).insertMovieGenres(expectedMovieGenres)
    }

    @Test
    fun `reloadMovieDetail should return error when insertMovieGenres error`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getResponseMovieDetail()
        val converted = dummyDetail.toEntity()
        val expected = MockitoKotlinException(null, null)
        val expectedMovieGenres = converted.second.map { it.toMovieGenre(dummyId) }

        whenever(mockedService.fetchMovieDetail(movieId = dummyId)).thenReturn(dummyDetail)
        whenever(mockedMovieGenreDao.insertMovieGenres(any())).thenThrow(expected)

        try {
            underTest.reloadMovieDetail(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchMovieDetail(dummyId)
        verify(mockedMovieDetailDao).insertMovieDetail(converted.first)
        verify(mockedGenreDao).insertGenres(converted.second)
        verify(mockedMovieGenreDao).insertMovieGenres(expectedMovieGenres)
    }

    @Test
    fun `reloadMovieDetail should return error when insertGenres error`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getResponseMovieDetail()
        val converted = dummyDetail.toEntity()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchMovieDetail(movieId = dummyId)).thenReturn(dummyDetail)
        whenever(mockedGenreDao.insertGenres(any())).thenThrow(expected)

        try {
            underTest.reloadMovieDetail(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchMovieDetail(dummyId)
        verify(mockedMovieDetailDao).insertMovieDetail(converted.first)
        verify(mockedGenreDao).insertGenres(converted.second)
        verify(mockedMovieGenreDao, never()).insertMovieGenres(any())
    }

    @Test
    fun `reloadMovieDetail should return error when insertMovieDetail error`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getResponseMovieDetail()
        val converted = dummyDetail.toEntity()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchMovieDetail(movieId = dummyId)).thenReturn(dummyDetail)
        whenever(mockedMovieDetailDao.insertMovieDetail(any())).thenThrow(expected)

        try {
            underTest.reloadMovieDetail(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchMovieDetail(dummyId)
        verify(mockedMovieDetailDao).insertMovieDetail(converted.first)
        verify(mockedGenreDao, never()).insertGenres(any())
        verify(mockedMovieGenreDao, never()).insertMovieGenres(any())
    }

    @Test
    fun `reloadMovieDetail should return error when fetchMovieDetail error`() = runTest {
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchMovieDetail(movieId = dummyId)).thenThrow(expected)

        try {
            underTest.reloadMovieDetail(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchMovieDetail(dummyId)
        verify(mockedMovieDetailDao, never()).insertMovieDetail(any())
        verify(mockedGenreDao, never()).insertGenres(any())
        verify(mockedMovieGenreDao, never()).insertMovieGenres(any())
    }

    @Test
    fun `getMovieDetail should return expected data`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getDbMovieDetail()
        val dummyGenres = Dummy.getDbGenres()
        val dummyMovieGenres = dummyGenres.map {
            MovieWithGenres(
                MovieGenre = MovieGenre(
                    id = Random.nextLong(),
                    movieId = dummyId,
                    genreId = it.id
                ),
                genre = it
            )
        }
        val expected = dummyDetail.toUi(dummyGenres)

        whenever(mockedMovieDetailDao.getMovieDetail(movieId = dummyId)).thenReturn(flowOf(dummyDetail))
        whenever(mockedMovieGenreDao.getMovieGenres(movieId = dummyId)).thenReturn(flowOf(dummyMovieGenres))

        val actual = underTest.getMovieDetail(dummyId).first()

        assertEquals(expected, actual)

        verify(mockedMovieDetailDao).getMovieDetail(dummyId)
        verify(mockedMovieGenreDao).getMovieGenres(dummyId)
    }

    @Test
    fun `getMovieDetail should return error when getMovieGenres error`() = runTest {
        val dummyId = Random.nextLong()
        val dummyDetail = Dummy.getDbMovieDetail()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedMovieDetailDao.getMovieDetail(movieId = dummyId)).thenReturn(flowOf(dummyDetail))
        whenever(mockedMovieGenreDao.getMovieGenres(movieId = dummyId)).thenThrow(expected)

        try {
            underTest.getMovieDetail(dummyId).first()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedMovieDetailDao).getMovieDetail(dummyId)
        verify(mockedMovieGenreDao).getMovieGenres(dummyId)
    }

    @Test
    fun `getMovieDetail should return error when getMovieDetail error`() = runTest {
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedMovieDetailDao.getMovieDetail(movieId = dummyId)).thenThrow(expected)

        try {
            underTest.getMovieDetail(dummyId).first()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedMovieDetailDao).getMovieDetail(dummyId)
        verify(mockedMovieGenreDao, never()).getMovieGenres(dummyId)
    }
}
