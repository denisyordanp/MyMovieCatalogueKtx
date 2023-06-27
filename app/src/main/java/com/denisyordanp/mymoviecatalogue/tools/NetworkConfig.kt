package com.denisyordanp.mymoviecatalogue.tools

import com.denisyordanp.mymoviecatalogue.BuildConfig

object NetworkConfig {
    fun isDebugMode(): Boolean = BuildConfig.DEBUG
    fun getBaseUrl(): String = "https://api.themoviedb.org/3"
    fun timeout(): Long = 60
}
