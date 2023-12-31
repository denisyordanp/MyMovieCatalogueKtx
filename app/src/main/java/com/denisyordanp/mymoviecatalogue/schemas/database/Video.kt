package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.NetworkConfig
import com.denisyordanp.mymoviecatalogue.tools.convertFormat
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video as UiVideo

@Entity(tableName = Video.TABLE_NAME)
data class Video(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: String,

    @ColumnInfo(name = ID_MOVIE_COLUMN)
    val movieId: Long,

    @ColumnInfo(name = KEY_COLUMN)
    val key: String,

    @ColumnInfo(name = NAME_COLUMN)
    val name: String,

    @ColumnInfo(name = PUBLISH_COLUMN)
    val publishedAt: String
) {
    fun toUi(): UiVideo {
        return UiVideo(
            id = id,
            thumbnailPath = NetworkConfig.getYoutubeThumbnailUrl(key),
            name = name,
            publishedAt = publishedAt.convertFormat(
                from = DateFormat.DATE_FULL_FORMAT,
                to = DateFormat.DATE_MONTH_YEAR_FORMAT
            ),
            key = key
        )
    }

    companion object {
        const val TABLE_NAME = "video"
        const val ID_COLUMN = "id_video"
        const val ID_MOVIE_COLUMN = "id_movie"
        const val KEY_COLUMN = "key"
        const val NAME_COLUMN = "name"
        const val PUBLISH_COLUMN = "publish"
    }
}
