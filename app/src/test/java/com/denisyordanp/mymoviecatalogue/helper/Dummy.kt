package com.denisyordanp.mymoviecatalogue.helper

import com.denisyordanp.mymoviecatalogue.schemas.response.Video as ResponseVideo
import com.denisyordanp.mymoviecatalogue.schemas.database.Video as DbVideo
import com.denisyordanp.mymoviecatalogue.schemas.response.Videos
import kotlin.random.Random
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail as DbMovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.response.Genre as ResponseGenre
import com.denisyordanp.mymoviecatalogue.schemas.response.MovieDetail as ResponseMovieDetail

object Dummy {
    fun getResponseGenres() = listOf(
        ResponseGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
        ResponseGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
    )

    fun getDbGenres() = listOf(
        DbGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
        DbGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
    )

    fun getResponseMovieDetail() = ResponseMovieDetail(
        id = Random.nextLong(),
        backdropPath = String.random(2),
        budget = Random.nextLong(),
        genres = getResponseGenres(),
        overview = String.random(2),
        releaseDate = String.random(2),
        revenue = Random.nextLong(),
        tagline = String.random(2),
        title = String.random(2),
        voteAverage = Random.nextDouble(),
        voteCount = Random.nextLong()
    )

    fun getDbMovieDetail() = DbMovieDetail(
        id = Random.nextLong(),
        backdropPath = String.random(2),
        budget = Random.nextLong(),
        overview = String.random(2),
        releaseDate = String.random(2),
        revenue = Random.nextLong(),
        tagline = String.random(2),
        title = String.random(2),
        voteAverage = Random.nextDouble(),
        voteCount = Random.nextLong()
    )

    fun getResponseVideos(): Videos {
        val videos = mutableListOf<ResponseVideo>()
        repeat(10) {
            videos.add(ResponseVideo(
                id = String.random(2),
                key = String.random(2),
                name = String.random(2),
                official = Random.nextBoolean(),
                publishedAt = String.random(2),
                site = ResponseVideo.YOUTUBE,
                size = Random.nextInt(),
                type = String.random(2),
            ))
        }
        return Videos(
            id = Random.nextInt(),
            results = videos
        )
    }

    fun getDbVideos(movieId: Long) = listOf(
        DbVideo(
            id = String.random(2),
            key = String.random(2),
            name = String.random(2),
            publishedAt = String.random(2),
            movieId = movieId
        ),
        DbVideo(
            id = String.random(2),
            key = String.random(2),
            name = String.random(2),
            publishedAt = String.random(2),
            movieId = movieId
        )
    )
}