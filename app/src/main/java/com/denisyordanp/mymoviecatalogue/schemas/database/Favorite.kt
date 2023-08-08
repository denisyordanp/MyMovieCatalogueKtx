package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.NetworkConfig
import com.denisyordanp.mymoviecatalogue.tools.convertFormat
import com.denisyordanp.mymoviecatalogue.tools.oneDecimalFormat
import com.denisyordanp.mymoviecatalogue.schemas.ui.Favorite as UiFavorite

@Entity(
    tableName = Favorite.TABLE_NAME,
    indices = [
        Index(value = [Favorite.ID_COLUMN]),
    ]
)
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: Long,

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
    val voteCount: Long,
) {
    fun toUi(): UiFavorite {
        return UiFavorite(
            id = id,
            overview = overview,
            posterPath = "${NetworkConfig.getImagePosterUrl()}$posterPath",
            releaseDate = releaseDate.convertFormat(
                DateFormat.DEFAULT_FORMAT,
                DateFormat.DATE_MONTH_YEAR_FORMAT
            ),
            title = title,
            voteAverage = voteAverage.oneDecimalFormat(),
            voteCount = voteCount
        )
    }

    companion object {
        const val TABLE_NAME = "favorite"
        const val ID_COLUMN = "id_movie"
        const val OVERVIEW_COLUMN = "overview"
        const val POSTER_COLUMN = "poster"
        const val RELEASE_DATE_COLUMN = "release_date"
        const val TITLE_COLUMN = "title"
        const val VOTE_AVERAGE_COLUMN = "vote_average"
        const val VOTE_COUNT_COLUMN = "vote_count"
    }
}
