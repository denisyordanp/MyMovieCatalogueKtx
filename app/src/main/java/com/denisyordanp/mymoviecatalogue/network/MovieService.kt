package com.denisyordanp.mymoviecatalogue.network

import com.denisyordanp.mymoviecatalogue.schemas.response.Genres
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("genre/movie/list")
    suspend fun fetchGenres(
        @Query("language") language: String = "en",
    ): Genres

    @GET("discover/movie")
    suspend fun fetchMovies(
        @Query("language") language: String = "en-US",
        @Query("include_adult") isAdult: Boolean = true,
        @Query("include_video") isVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genre: Int,
    ): Movies
}