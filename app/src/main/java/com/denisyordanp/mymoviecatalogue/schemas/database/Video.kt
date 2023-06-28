package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = Video.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = [Movie.ID_COLUMN],
            childColumns = [Movie.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [Video.ID_COLUMN]),
        Index(value = [Movie.ID_COLUMN]),
    ]
)
data class Video(
    @ColumnInfo(name = ID_COLUMN)
    val id: String,

    @ColumnInfo(name = Movie.ID_COLUMN)
    val movieId: Int,

    @ColumnInfo(name = KEY_COLUMN)
    val key: String,

    @ColumnInfo(name = NAME_COLUMN)
    val name: String,

    @ColumnInfo(name = PUBLISH_COLUMN)
    val publishedAt: String
) {
    companion object {
        const val TABLE_NAME = "video"
        const val ID_COLUMN = "id_video"
        const val KEY_COLUMN = "key"
        const val NAME_COLUMN = "name"
        const val PUBLISH_COLUMN = "publish"
    }
}