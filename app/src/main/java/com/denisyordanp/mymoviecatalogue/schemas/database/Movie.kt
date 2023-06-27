package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.NetworkConfig
import com.denisyordanp.mymoviecatalogue.tools.convertFormat

import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie as UiMovie

@Entity(
    tableName = Movie.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Genre::class,
            parentColumns = [Genre.ID_COLUMN],
            childColumns = [Genre.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [Movie.ID_COLUMN]),
        Index(value = [Genre.ID_COLUMN]),
    ]
)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,

    @ColumnInfo(name = Genre.ID_COLUMN)
    val genreId: Int,

    @ColumnInfo(name = OVERVIEW_COLUMN)
    val overview: String,

    @ColumnInfo(name = POSTER_COLUMN)
    val posterPath: String,

    @ColumnInfo(name = RELEASE_DATE_COLUMN)
    val releaseDate: String,

    @ColumnInfo(name = TITLE_COLUMN)
    val title: String,

    @ColumnInfo(name = VOTE_AVERAGE_COLUMN)
    val voteAverage: Double,

    @ColumnInfo(name = VOTE_COUNT_COLUMN)
    val voteCount: Int
) {
    fun toUi(): UiMovie {
        return UiMovie(
            id = id,
            overview = overview,
            posterPath = "${NetworkConfig.getImageBaseUrl()}$posterPath",
            releaseDate = releaseDate.convertFormat(
                DateFormat.DEFAULT_FORMAT,
                DateFormat.DATE_MONTH_YEAR_FORMAT
            ),
            title = title,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    companion object {
        const val TABLE_NAME = "movie"
        const val ID_COLUMN = "id_movie"
        const val OVERVIEW_COLUMN = "overview"
        const val POSTER_COLUMN = "poster"
        const val RELEASE_DATE_COLUMN = "release_date"
        const val TITLE_COLUMN = "title"
        const val VOTE_AVERAGE_COLUMN = "vote_average"
        const val VOTE_COUNT_COLUMN = "vote_count"
    }
}