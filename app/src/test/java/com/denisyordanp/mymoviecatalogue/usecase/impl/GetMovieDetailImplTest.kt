package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.MovieDetailRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

internal class GetMovieDetailImplTest {
    private lateinit var underTest: GetMovieDetail
    private val mockedMovieDetailRepository = mock<MovieDetailRepository>()

    @Before
    fun setup() {
        underTest = GetMovieDetailImpl(mockedMovieDetailRepository)
    }

    @Test
    fun `invoke with isForce true should call reloadMovieDetail and return expected data`() =
        runTest {
            val isForce = true
            val dummyId = Random.nextLong()
            val dummyGenres = Dummy.getDbGenres()
            val expected = Dummy.getDbMovieDetail().toUi(dummyGenres, false)

            whenever(mockedMovieDetailRepository.getMovieDetail(dummyId)).thenReturn(flowOf(expected))

            val actual = underTest(dummyId, isForce).first()

            Assert.assertEquals(expected, actual)

            verify(mockedMovieDetailRepository).reloadMovieDetail(dummyId)
        }

    @Test
    fun `invoke with isForce false should not call reloadMovieDetail and return expected data`() =
        runTest {
            val isForce = false
            val dummyId = Random.nextLong()
            val dummyGenres = Dummy.getDbGenres()
            val expected = Dummy.getDbMovieDetail().toUi(dummyGenres, false)

            whenever(mockedMovieDetailRepository.getMovieDetail(dummyId)).thenReturn(flowOf(expected))

            val actual = underTest(dummyId, isForce).first()

            Assert.assertEquals(expected, actual)

            verify(mockedMovieDetailRepository, never()).reloadMovieDetail(dummyId)
        }

    @Test
    fun `invoke should call reloadMovieDetail when data is null`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val dummyGenres = Dummy.getDbGenres()
        val expected = Dummy.getDbMovieDetail().toUi(dummyGenres, false)

        whenever(mockedMovieDetailRepository.getMovieDetail(dummyId))
            .thenReturn(flow {
                emit(null)
                emit(expected)
            })

        val actual = underTest(dummyId, isForce).last()

        Assert.assertEquals(expected, actual)

        verify(mockedMovieDetailRepository).reloadMovieDetail(dummyId)
    }

    @Test
    fun `invoke should not call reloadMovieDetail when data is not null`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val dummyGenres = Dummy.getDbGenres()
        val expected = Dummy.getDbMovieDetail().toUi(dummyGenres, false)

        whenever(mockedMovieDetailRepository.getMovieDetail(dummyId))
            .thenReturn(flowOf(expected))

        val actual = underTest(dummyId, isForce).last()

        Assert.assertEquals(expected, actual)

        verify(mockedMovieDetailRepository, never()).reloadMovieDetail(dummyId)
    }

    @Test
    fun `invoke should throw error when getMovieDetail error`() = runTest {
        val isForce = true
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedMovieDetailRepository.getMovieDetail(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke with isForce true should throw error when reloadMovieDetail error`() = runTest {
        val isForce = true
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedMovieDetailRepository.reloadMovieDetail(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke should throw error when data is null and getMovieDetail error`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedMovieDetailRepository.getMovieDetail(dummyId)).thenReturn(flowOf(null))
        whenever(mockedMovieDetailRepository.reloadMovieDetail(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }
}
