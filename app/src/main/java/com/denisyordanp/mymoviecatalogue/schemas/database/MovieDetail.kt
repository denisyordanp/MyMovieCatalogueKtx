package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = MovieDetail.TABLE_NAME,
    indices = [
        Index(value = [MovieDetail.ID_COLUMN])
    ]
)
data class MovieDetail(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: Long,

    @ColumnInfo(name = BACKDROP_COLUMN)
    val backdropPath: String,

    @ColumnInfo(name = BUDGET_COLUMN)
    val budget: Long,

    @ColumnInfo(name = OVERVIEW_COLUMN)
    val overview: String,

    @ColumnInfo(name = POSTER_COLUMN)
    val posterPath: String,

    @ColumnInfo(name = RELEASE_COLUMN)
    val releaseDate: String,

    @ColumnInfo(name = REVENUE_COLUMN)
    val revenue: Long,

    @ColumnInfo(name = TAGLINE_COLUMN)
    val tagline: String,

    @ColumnInfo(name = TITLE_COLUMN)
    val title: String,

    @ColumnInfo(name = VOTE_AVERAGE_COLUMN)
    val voteAverage: Double,

    @ColumnInfo(name = VOTE_COUNT_COLUMN)
    val voteCount: Long
) {
    companion object {
        const val TABLE_NAME = "movie_detail"
        const val ID_COLUMN = "id_movie_detail"
        const val BACKDROP_COLUMN = "backdrop"
        const val BUDGET_COLUMN = "budget"
        const val OVERVIEW_COLUMN = "overview"
        const val POSTER_COLUMN = "poster"
        const val RELEASE_COLUMN = "release"
        const val REVENUE_COLUMN = "revenue"
        const val TAGLINE_COLUMN = "tagline"
        const val TITLE_COLUMN = "title"
        const val VOTE_AVERAGE_COLUMN = "vote_average"
        const val VOTE_COUNT_COLUMN = "vote_count"
    }
}
