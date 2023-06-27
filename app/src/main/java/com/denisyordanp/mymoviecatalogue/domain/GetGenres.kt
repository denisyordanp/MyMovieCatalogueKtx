package com.denisyordanp.mymoviecatalogue.domain

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow

fun interface GetGenres {
    operator fun invoke(): Flow<List<Genre>>
}