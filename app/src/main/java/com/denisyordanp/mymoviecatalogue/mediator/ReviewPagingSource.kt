package com.denisyordanp.mymoviecatalogue.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import javax.inject.Inject

class ReviewPagingSource @Inject constructor(
    private val service: MovieService,
    private val movieId: Long,
    private val isForce: Boolean
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return if (isForce) { 1 } else {
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            val response = service.fetchReviews(movieId = movieId, page = page).toEntity(movieId)
                .map { it.toUi() }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}