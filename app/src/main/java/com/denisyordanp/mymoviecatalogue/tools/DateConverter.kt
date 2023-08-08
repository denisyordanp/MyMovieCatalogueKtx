package com.denisyordanp.mymoviecatalogue.tools

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.convertFormat(from: DateFormat, to: DateFormat): String {
    if (this.isBlank()) return ""

    return try {
        val fromFormat = DateTimeFormatter.ofPattern(from.format, Locale.getDefault())
        val toFormat = DateTimeFormatter.ofPattern(to.format, Locale.getDefault())

        val localDateTime = LocalDate.parse(this, fromFormat)
        localDateTime.format(toFormat)
    } catch (e: Exception) {
        StackTrace.printStackTrace(e)
        ""
    }
}
