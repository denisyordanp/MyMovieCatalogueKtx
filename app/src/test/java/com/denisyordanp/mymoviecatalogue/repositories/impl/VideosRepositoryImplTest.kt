package com.denisyordanp.mymoviecatalogue.repositories.impl

import com.denisyordanp.mymoviecatalogue.database.VideosDao
import com.denisyordanp.mymoviecatalogue.helper.Dummy
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.tools.AppConfig
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


internal class VideosRepositoryImplTest {
    private lateinit var underTest: VideosRepository

    private val mockedService = mock<MovieService>()
    private val mockedVideoDao = mock<VideosDao>()

    @Before
    fun setup() {
        underTest = VideosRepositoryImpl(
            mockedService,
            mockedVideoDao
        )
    }

    @Test
    fun `reloadVideos should call fetchVideos insertVideos and take max videos count`() = runTest {
        val dummyId = Random.nextLong()
        val dummyVideos = Dummy.getResponseVideos()
        val expectedVideos = dummyVideos.toEntity(dummyId)
            .take(AppConfig.MAX_VIDEOS)

        whenever(mockedService.fetchVideos(dummyId)).thenReturn(dummyVideos)

        underTest.reloadVideos(dummyId)

        verify(mockedService).fetchVideos(dummyId)
        verify(mockedVideoDao).insertVideos(expectedVideos)
    }

    @Test
    fun `reloadVideos should return error when insertVideos error`() = runTest {
        val dummyId = Random.nextLong()
        val dummyVideos = Dummy.getResponseVideos()
        val expectedVideos = dummyVideos.toEntity(dummyId)
            .take(AppConfig.MAX_VIDEOS)
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchVideos(dummyId)).thenReturn(dummyVideos)
        whenever(mockedVideoDao.insertVideos(any())).thenThrow(expected)

        try {
            underTest.reloadVideos(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchVideos(dummyId)
        verify(mockedVideoDao).insertVideos(expectedVideos)
    }

    @Test
    fun `reloadVideos should return error when fetchVideos error`() = runTest {
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null, null)

        whenever(mockedService.fetchVideos(dummyId)).thenThrow(expected)

        try {
            underTest.reloadVideos(dummyId)
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedService).fetchVideos(dummyId)
        verify(mockedVideoDao, never()).insertVideos(any())
    }

    @Test
    fun `getVideos should return expected data`() = runTest {
        val dummyId = Random.nextLong()
        val dummy = Dummy.getDbVideos(dummyId)
        val expected = dummy.map { it.toUi() }

        whenever(mockedVideoDao.getVideos(dummyId)).thenReturn(flowOf(dummy))

        val actual = underTest.getVideos(dummyId).first()

        assertEquals(expected, actual)

        verify(mockedVideoDao).getVideos(dummyId)
    }

    @Test
    fun `getVideos should throw error when getVideos error`() = runTest {
        val dummyId = Random.nextLong()
        val expected = MockitoKotlinException(null,null)

        whenever(mockedVideoDao.getVideos(dummyId)).thenThrow(expected)

        try {
            underTest.getVideos(dummyId).first()
        } catch (actual: Exception) {
            assertTrue(actual is MockitoKotlinException)
            assertEquals(expected, actual)
        }

        verify(mockedVideoDao).getVideos(dummyId)
    }
}
