package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail

@Entity(
    tableName = MovieGenre.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = [MovieDetail.ID_COLUMN],
            childColumns = [MovieDetail.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = [Genre.ID_COLUMN],
            childColumns = [Genre.ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [MovieGenre.ID_COLUMN]),
        Index(value = [MovieDetail.ID_COLUMN]),
        Index(value = [Genre.ID_COLUMN]),
    ]
)
data class MovieGenre(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int,

    @ColumnInfo(name = MovieDetail.ID_COLUMN)
    val movieId: Int,

    @ColumnInfo(name = Genre.ID_COLUMN)
    val genreId: Int
) {
    companion object {
        const val TABLE_NAME = "movie_genre"
        const val ID_COLUMN = "id_movie_genre"
    }
}
