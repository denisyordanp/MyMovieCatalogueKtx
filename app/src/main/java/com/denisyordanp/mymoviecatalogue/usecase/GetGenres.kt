package com.denisyordanp.mymoviecatalogue.usecase

import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import kotlinx.coroutines.flow.Flow

fun interface GetGenres {
    operator fun invoke(isForce: Boolean): Flow<List<Genre>>
}
