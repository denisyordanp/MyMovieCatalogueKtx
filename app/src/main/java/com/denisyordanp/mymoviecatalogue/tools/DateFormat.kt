package com.denisyordanp.mymoviecatalogue.tools

enum class DateFormat(val format: String) {
    DEFAULT_FORMAT("yyyy-MM-dd"),
    DATE_MONTH_YEAR_FORMAT("dd MMMM yyyy"),
    DATE_FULL_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
}