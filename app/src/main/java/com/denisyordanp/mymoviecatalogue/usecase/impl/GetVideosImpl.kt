package com.denisyordanp.mymoviecatalogue.usecase.impl

import com.denisyordanp.mymoviecatalogue.repositories.VideosRepository
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVideosImpl @Inject constructor(
    private val repository: VideosRepository
) : GetVideos {
    override fun invoke(movieId: Long, isForce: Boolean) = flow {
        var hasReload = false

        if (isForce) {
            repository.reloadVideos(movieId)
            hasReload = true
        }
        repository.getVideos(movieId)
            .collect {
                if (it.isEmpty() && hasReload.not()) {
                    repository.reloadVideos(movieId)
                    hasReload = true
                } else {
                    emit(it)
                }
            }
    }
}