@file:OptIn(ExperimentalCoroutinesApi::class)

package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGenres: GetGenres,
    private val getMovies: GetMovies
) : ViewModel() {
    private val _viewState = MutableStateFlow(MainViewState.idle())
    val viewState = _viewState.asStateFlow()

    fun loadGenres(isForce: Boolean) {
        viewModelScope.launch {
            getGenres(isForce)
                .map {
                    val currentState = _viewState.value
                    val selectedGenre = currentState.selectedGenre ?: it.first()
                    currentState.copy(
                        error = null,
                        selectedGenre = selectedGenre,
                        genres = it,
                        isLoading = false,
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
                            isLoading = false,
                            error = it
                        )
                    )
                }.mapLatest {
                    if (it.error == null &&
                        it.selectedGenre != null &&
                        it.movies == MainViewState.idle().movies
                    ) {
                        it.copy(
                            movies = getMovies(genreId = it.selectedGenre.id, isForce = isForce)
                        )
                    } else it
                }.collect(_viewState)
        }
    }

    fun selectGenre(genre: Genre) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(
                    selectedGenre = genre,
                    movies = getMovies(genreId = genre.id, isForce = false)
                )
            )
        }
    }
}
