package com.denisyordanp.mymoviecatalogue.tools

import com.denisyordanp.mymoviecatalogue.helper.random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DateConverterKtTest {
    private val dummyDefaultFormat = "2023-05-20"
    private val dummyDateMonthYearFormat = "20 May 2023"
    private val dummyDateFullFormat = "2023-05-20T08:13:12.255Z"

    private fun DateFormat.getResultFormat() = when (this) {
        DateFormat.DEFAULT_FORMAT -> dummyDefaultFormat
        DateFormat.DATE_MONTH_YEAR_FORMAT -> dummyDateMonthYearFormat
        DateFormat.DATE_FULL_FORMAT -> dummyDateFullFormat
    }

    @Test
    fun `convertFormat defaultFormat should return expected format`() {
        val toFormat = DateFormat.values().random()
        val expected = toFormat.getResultFormat()
        val actual = dummyDefaultFormat.convertFormat(DateFormat.DEFAULT_FORMAT, toFormat)

        assertTrue(actual.isNotEmpty())
        assertEquals(expected, actual)
    }

    @Test
    fun `convertFormat should return empty string when given from format not match`() {
        val dummy = String.random(6)
        val actual = dummy.convertFormat(DateFormat.values().random(), DateFormat.values().random())

        assertTrue(actual.isEmpty())
    }

    @Test
    fun `convertFormat should return empty when given string empty`() {
        val actual = "".convertFormat(DateFormat.DEFAULT_FORMAT, DateFormat.values().random())

        assertTrue(actual.isEmpty())
    }
}
