package com.denisyordanp.mymoviecatalogue.network

import com.denisyordanp.mymoviecatalogue.schemas.response.Genres
import com.denisyordanp.mymoviecatalogue.schemas.response.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import com.denisyordanp.mymoviecatalogue.schemas.response.Reviews
import com.denisyordanp.mymoviecatalogue.schemas.response.Videos
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path(value = "movie_id", encoded = true) movieId: Int,
        @Query("language") language: String = "en-US",
    ): MovieDetail

    @GET("movie/{movie_id}/reviews")
    suspend fun fetchReviews(
        @Path(value = "movie_id", encoded = true) movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): Reviews

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideos(
        @Path(value = "movie_id", encoded = true) movieId: Int,
        @Query("language") language: String = "en-US",
    ): Videos
}