package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.convertFormat

import com.denisyordanp.mymoviecatalogue.schemas.ui.Review as UiReview

@Entity(
    tableName = Review.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = [MovieDetail.ID_COLUMN],
            childColumns = [MovieDetail.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [Review.ID_COLUMN]),
        Index(value = [MovieDetail.ID_COLUMN]),
    ]
)
data class Review(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: String,

    @ColumnInfo(name = MovieDetail.ID_COLUMN)
    val movieId: Long,

    @ColumnInfo(name = AUTHOR_COLUMN)
    val author: String,

    @ColumnInfo(name = CONTENT_COLUMN)
    val content: String,

    @ColumnInfo(name = CREATE_COLUMN)
    val createdAt: String
) {
    fun toUi(): UiReview {
        return UiReview(
            id = id,
            author = author,
            content = content,
            createdAt = createdAt.convertFormat(
                from = DateFormat.DATE_FULL_FORMAT,
                to = DateFormat.DATE_MONTH_YEAR_FORMAT
            )
        )
    }

    companion object {
        const val TABLE_NAME = "review"
        const val ID_COLUMN = "id_review"
        const val AUTHOR_COLUMN = "author"
        const val CONTENT_COLUMN = "content"
        const val CREATE_COLUMN = "create"
    }
}
