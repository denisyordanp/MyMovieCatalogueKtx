package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import com.nhaarman.mockitokotlin2.MockitoKotlinException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
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

internal class GetVideosImplTest {
    private lateinit var underTest: GetVideos
    private val mockedVideoRepository = mock<VideosRepository>()

    @Before
    fun setup() {
        underTest = GetVideosImpl(mockedVideoRepository)
    }

    @Test
    fun `invoke with isForce true should call reloadVideos and return expected data`() = runTest {
        val isForce = true
        val dummyId = Random.nextLong()
        val expected = Dummy.getDbVideos(dummyId).map { it.toUi() }

        whenever(mockedVideoRepository.getVideos(dummyId)).thenReturn(flowOf(expected))

        val actual = underTest(dummyId, isForce).first()

        Assert.assertEquals(expected, actual)

        verify(mockedVideoRepository).reloadVideos(dummyId)
    }

    @Test
    fun `invoke with isForce false should not call reloadVideos and return expected data`() =
        runTest {
            val isForce = false
            val dummyId = Random.nextLong()
            val expected = Dummy.getDbVideos(dummyId).map { it.toUi() }

            whenever(mockedVideoRepository.getVideos(dummyId)).thenReturn(flowOf(expected))

            val actual = underTest(dummyId, isForce).first()

            Assert.assertEquals(expected, actual)

            verify(mockedVideoRepository, never()).reloadVideos(dummyId)
        }

    @Test
    fun `invoke should call reloadVideos when data is empty`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val expected = Dummy.getDbVideos(dummyId).map { it.toUi() }

        whenever(mockedVideoRepository.getVideos(dummyId))
            .thenReturn(flow {
                emit(emptyList())
                emit(expected)
            })

        val actual = underTest(dummyId, isForce).last()

        Assert.assertEquals(expected, actual)

        verify(mockedVideoRepository).reloadVideos(dummyId)
    }

    @Test
    fun `invoke should call reloadVideos once when data is empty twice and return empty videos`() =
        runTest {
            val isForce = false
            val dummyId = Random.nextLong()

            whenever(mockedVideoRepository.getVideos(dummyId))
                .thenReturn(flow {
                    emit(emptyList())
                    emit(emptyList())
                })

            val actual = underTest(dummyId, isForce).last()

            Assert.assertTrue(actual.isEmpty())

            verify(mockedVideoRepository, times(1)).reloadVideos(dummyId)
        }

    @Test
    fun `invoke should not call reloadVideos when data is not empty`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val expected = Dummy.getDbVideos(dummyId).map { it.toUi() }

        whenever(mockedVideoRepository.getVideos(dummyId))
            .thenReturn(flowOf(expected))

        val actual = underTest(dummyId, isForce).last()

        Assert.assertEquals(expected, actual)

        verify(mockedVideoRepository, never()).reloadVideos(dummyId)
    }

    @Test
    fun `invoke should throw error when getVideos error`() = runTest {
        val isForce = true
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedVideoRepository.getVideos(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke with isForce true should throw error when reloadVideos error`() = runTest {
        val isForce = true
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedVideoRepository.reloadVideos(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `invoke should throw error when data is empty and reloadVideos error`() = runTest {
        val isForce = false
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedVideoRepository.getVideos(dummyId)).thenReturn(flowOf(emptyList()))
        whenever(mockedVideoRepository.reloadVideos(dummyId)).thenThrow(expected)

        try {
            underTest(dummyId, isForce)
        } catch (actual: Exception) {
            Assert.assertTrue(actual is MockitoKotlinException)
            Assert.assertEquals(expected, actual)
        }
    }
}
