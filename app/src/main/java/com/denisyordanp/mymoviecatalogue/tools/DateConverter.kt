package com.denisyordanp.mymoviecatalogue.tools

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.convertFormat(from: DateFormat, to: DateFormat): String {
    if (this.isBlank()) return ""

    val fromFormat = DateTimeFormatter.ofPattern(from.format, Locale.getDefault())
    val toFormat = DateTimeFormatter.ofPattern(to.format, Locale.getDefault())

    return try {
        val localDateTime = LocalDate.parse(this, fromFormat)
        localDateTime?.let {
            return localDateTime.format(toFormat)
        } ?: ""
    } catch (e: Exception) {
        ""
    }
}