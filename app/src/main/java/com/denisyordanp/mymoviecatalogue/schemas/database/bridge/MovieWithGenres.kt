package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.NetworkConfig
import com.denisyordanp.mymoviecatalogue.tools.convertFormat
import com.denisyordanp.mymoviecatalogue.tools.oneDecimalFormat
import com.denisyordanp.mymoviecatalogue.tools.thousand
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail as UiMovieDetail

data class MovieWithGenres(
    @Embedded
    val movieDetail: MovieDetail,

    @Relation(
        parentColumn = MovieDetail.ID_COLUMN,
        entity = Genre::class,
        entityColumn = Genre.ID_COLUMN,
        associateBy = Junction(
            value = MovieGenre::class,
            parentColumn = MovieDetail.ID_COLUMN,
            entityColumn = Genre.ID_COLUMN
        )
    )
    val genres: List<Genre> = emptyList(),
) {
    fun toUi(): UiMovieDetail {
        val convertedGenres = genres.map { it.toUi() }
        return UiMovieDetail(
            id = movieDetail.id,
            backdropPath = "${NetworkConfig.getImageBackdropUrl()}${movieDetail.backdropPath}",
            budget = movieDetail.budget.thousand(),
            genres = convertedGenres,
            overview = movieDetail.overview,
            releaseDate = movieDetail.releaseDate.convertFormat(
                DateFormat.DEFAULT_FORMAT,
                DateFormat.DATE_MONTH_YEAR_FORMAT
            ),
            revenue = movieDetail.revenue.thousand(),
            tagline = movieDetail.tagline,
            title = movieDetail.title,
            voteAverage = movieDetail.voteAverage.oneDecimalFormat(),
            voteCount = movieDetail.voteCount
        )
    }
}
