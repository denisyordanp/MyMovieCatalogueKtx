package com.denisyordanp.mymoviecatalogue.tools

import com.denisyordanp.mymoviecatalogue.BuildConfig

object NetworkConfig {
    fun isDebugMode(): Boolean = BuildConfig.DEBUG
    fun getAuthKey(): String = BuildConfig.AUTH_KEY
    fun getBaseUrl(): String = "https://api.themoviedb.org/3/"
    fun getImageBaseUrl(): String = "https://image.tmdb.org/t/p/w300"
    fun timeout(): Long = 60
}
