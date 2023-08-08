package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie

@Entity(
    tableName = RemoteKeys.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Genre::class,
            parentColumns = [Genre.ID_COLUMN],
            childColumns = [Genre.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = [Movie.ID_COLUMN],
            childColumns = [Movie.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [RemoteKeys.ID_COLUMN]),
        Index(value = [Movie.ID_COLUMN]),
        Index(value = [Genre.ID_COLUMN]),
    ]
)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Long,

    @ColumnInfo(name = Movie.ID_COLUMN)
    val movieId: Long,

    @ColumnInfo(name = Genre.ID_COLUMN)
    val genreId: Long,

    @ColumnInfo(name = PAGE_COLUMN)
    val currentPage: Int,

    @ColumnInfo(name = PREV_KEY_COLUMN)
    val prevKey: Int?,

    @ColumnInfo(name = NEXT_KEY_COLUMN)
    val nextKey: Int?,

    @ColumnInfo(name = CREATED_AT_COLUMN)
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val TABLE_NAME = "remote_key"
        const val ID_COLUMN = "id_remote_key"
        const val PAGE_COLUMN = "page"
        const val PREV_KEY_COLUMN = "prev_key"
        const val NEXT_KEY_COLUMN = "next_key"
        const val CREATED_AT_COLUMN = "created_at"
    }
}
