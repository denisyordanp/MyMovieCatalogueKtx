package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = Review.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = [Movie.ID_COLUMN],
            childColumns = [Movie.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [Review.ID_COLUMN]),
        Index(value = [Movie.ID_COLUMN]),
    ]
)
data class Review(
    @ColumnInfo(name = ID_COLUMN)
    val id: String,

    @ColumnInfo(name = Movie.ID_COLUMN)
    val movieId: Int,

    @ColumnInfo(name = AUTHOR_COLUMN)
    val author: String,

    @ColumnInfo(name = CONTENT_COLUMN)
    val content: String,

    @ColumnInfo(name = CREATE_COLUMN)
    val createdAt: String
) {
    companion object {
        const val TABLE_NAME = "review"
        const val ID_COLUMN = "id_review"
        const val AUTHOR_COLUMN = "author"
        const val CONTENT_COLUMN = "content"
        const val CREATE_COLUMN = "create"
    }
}
