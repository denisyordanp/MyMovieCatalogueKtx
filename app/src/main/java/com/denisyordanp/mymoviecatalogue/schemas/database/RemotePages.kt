package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = RemotePages.TABLE_NAME,
    indices = [
        Index(value = [RemotePages.ID_COLUMN])
    ]
)
data class RemotePages(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Long = 0,

    @ColumnInfo(name = LABEL_COLUMN)
    val label: String,

    @ColumnInfo(name = NEXT_PAGE_COLUMN)
    val nextPage: Int
) {
    companion object {
        const val TABLE_NAME = "remote_pages"
        const val ID_COLUMN = "id_remote_pages"
        const val LABEL_COLUMN = "label"
        const val NEXT_PAGE_COLUMN = "next_page"
    }
}