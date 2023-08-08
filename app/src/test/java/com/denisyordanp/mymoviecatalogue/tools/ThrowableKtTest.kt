package com.denisyordanp.mymoviecatalogue.tools

import com.nhaarman.mockitokotlin2.MockitoKotlinException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.random.Random

internal class ThrowableKtTest {

    @Test
    fun `isAuthError should return true when HttpException and message is HTTP_401`() {
        val message = "".toResponseBody()
        val error = HttpException(Response.error<String>(HTTP_401, message))

        val actual = error.isAuthError()

        assertTrue(actual)
    }

    @Test
    fun `isAuthError should return false when error not HttpException`() {
        val error = MockitoKotlinException(null,null)

        val actual = error.isAuthError()

        assertFalse(actual)
    }

    @Test
    fun `isAuthError should return false when HttpException but message is not HTTP_401`() {
        val message = "".toResponseBody()
        val code = Random.nextInt(402, 500)
        val error = HttpException(Response.error<String>(code, message))


        val actual = error.isAuthError()

        assertFalse(actual)
    }
}
