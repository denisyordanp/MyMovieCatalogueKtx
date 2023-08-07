package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * When we get the last item loaded from the PagingState, there's no way to know the index of the page it belonged to.
 * To solve this problem, we can add another table that stores the next and previous page keys for each Movie.
 */
@Entity(tableName = RemoteKeys.TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MOVIE_ID_COLUMN)
    val movieID: Long,

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
        const val MOVIE_ID_COLUMN = "id_movie"
        const val PAGE_COLUMN = "page"
        const val PREV_KEY_COLUMN = "prev_key"
        const val NEXT_KEY_COLUMN = "next_key"
        const val CREATED_AT_COLUMN = "created_at"
    }
}
