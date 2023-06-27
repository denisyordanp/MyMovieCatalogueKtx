package com.denisyordanp.mymoviecatalogue.schemas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = Genre.TABLE_NAME,
    indices = [
        Index(value = [Genre.ID_COLUMN])
    ]
)
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,

    @ColumnInfo(name = NAME_COLUMN)
    val name: String
) {

    fun toUi(): com.denisyordanp.mymoviecatalogue.schemas.ui.Genre {
        return com.denisyordanp.mymoviecatalogue.schemas.ui.Genre(
            id = id,
            name = name
        )
    }
    companion object {
        const val TABLE_NAME = "genre"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
    }
}