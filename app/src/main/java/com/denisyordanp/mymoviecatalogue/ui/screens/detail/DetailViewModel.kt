package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import com.denisyordanp.mymoviecatalogue.usecase.ChangeFavorite
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val getVideos: GetVideos,
    private val getReviews: GetReviews,
    private val changeFavorite: ChangeFavorite,
) : ViewModel() {
    private val _viewState = MutableStateFlow(DetailViewState.idle())
    val viewState = _viewState.asStateFlow()

    fun addMovieToFavorite() = viewModelScope.launch {
        val movie = _viewState.value.movieDetail!!
        changeFavorite(movie.toFavorite())
    }

    fun loadMovieDetail(movieId: Long, isForce: Boolean) = viewModelScope.launch {
        beginLoadDetail(movieId, isForce)
            .collect(_viewState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun beginLoadDetail(movieId: Long, isForce: Boolean): Flow<DetailViewState> {
        return getMovieDetail(movieId, isForce)
            .map {
                _viewState.value.copy(
                    movieDetail = it,
                    isLoading = false,
                    error = null
                )
            }
            .flatMapMerge {
                beginLoadVideos(movieId, isForce).map { videoState ->
                    it.copy(
                        videosViewState = videoState
                    )
                }
            }
            .flatMapMerge {
                flowOf(
                    it.copy(
                        reviews = getReviews(movieId, isForce)
                    )
                )
            }
            .onStart {
                emit(
                    _viewState.value.copy(
                        isLoading = true
                    )
                )
            }.catch {
                StackTrace.printStackTrace(it)
                emit(
                    _viewState.value.copy(
                        error = it,
                        isLoading = false
                    )
                )
            }
    }

    fun loadVideos(movieId: Long, isForce: Boolean) {
        viewModelScope.launch {
            beginLoadVideos(movieId, isForce)
                .collect {
                    _viewState.value = _viewState.value.copy(
                        videosViewState = it
                    )
                }
        }
    }

    private fun beginLoadVideos(movieId: Long, isForce: Boolean): Flow<VideosViewState> {
        return getVideos(movieId, isForce)
            .map {
                val currentState = _viewState.value.videosViewState
                currentState.copy(
                    videos = it,
                    isLoading = false,
                    error = null
                )
            }.onStart {
                val currentState = _viewState.value.videosViewState
                emit(
                    currentState.copy(
                        isLoading = true
                    )
                )
            }.catch {
                StackTrace.printStackTrace(it)
                val currentState = _viewState.value.videosViewState
                emit(
                    currentState.copy(
                        error = it,
                        isLoading = false
                    )
                )
            }
    }
}
