package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import com.denisyordanp.mymoviecatalogue.usecase.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail
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
                            error = it
                        )
                    )
                }.collect {
                    _viewState.emit(it)
                }
        }
    }

}