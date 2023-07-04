package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import com.denisyordanp.mymoviecatalogue.usecase.GetReviews
import com.denisyordanp.mymoviecatalogue.usecase.GetVideos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val getVideos: GetVideos,
    private val getReviews: GetReviews
) : ViewModel() {
    private val _viewState = MutableStateFlow(DetailViewState.idle())
    val viewState = _viewState.asStateFlow()

    fun loadMovieDetail(movieId: Long, isForce: Boolean) {
        viewModelScope.launch {
            getMovieDetail(movieId, isForce)
                .map {
                    _viewState.value.copy(
                        movieDetail = it,
                        isLoading = false,
                        error = null
                    )
                }.onStart {
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
                }.onEach {
                    loadVideos(movieId, isForce)
                    loadReviews(movieId, isForce)
                }.collect {
                    _viewState.emit(it)
                }
        }
    }

    fun loadVideos(movieId: Long, isForce: Boolean) {
        viewModelScope.launch {
            getVideos(movieId, isForce)
                .map {
                    val currentState = _viewState.value
                    currentState.copy(
                        videosViewState = currentState.videosViewState.copy(
                            videos = it,
                            isLoading = false,
                            error = null
                        )
                    )
                }.onStart {
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            videosViewState = currentState.videosViewState.copy(
                                isLoading = true
                            )
                        )
                    )
                }.catch {
                    StackTrace.printStackTrace(it)
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            videosViewState = currentState.videosViewState.copy(
                                error = it,
                                isLoading = false
                            )
                        )
                    )
                }.collect {
                    _viewState.emit(it)
                }
        }
    }

    private fun loadReviews(movieId: Long, isForce: Boolean) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(
                    reviews = getReviews(movieId, isForce)
                )
            )
        }
    }
}