package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.denisyordanp.mymoviecatalogue.usecase.GetGenres
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import com.denisyordanp.mymoviecatalogue.usecase.GetMoviesPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGenres: GetGenres,
    private val getMovies: GetMovies,
    private val getMoviesPaging: GetMoviesPaging
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
                        genreViewState = currentState.genreViewState.copy(
                            genres = it,
                            isLoading = false,
                            error = null
                        )
                    )
                }.onStart {
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            genreViewState = currentState.genreViewState.copy(
                                isLoading = true
                            )
                        )
                    )
                }.catch {
                    StackTrace.printStackTrace(it)
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            genreViewState = currentState.genreViewState.copy(
                                isLoading = false,
                                error = it
                            )
                        )
                    )
                }.collect {
                    _viewState.emit(it)
                    if (it.genreViewState.error == null && it.selectedGenre != null) {
//                        loadMovies(genreId = it.selectedGenre.id, isForce = isForce)
                    }
                }
        }
    }

    fun loadMovies(genreId: Long? = null, isForce: Boolean) {
        viewModelScope.launch {
            val currentGenreId = genreId ?: _viewState.value.selectedGenre!!.id
            getMovies(genreId = currentGenreId, isForce = isForce)
                .map {
                    val currentState = _viewState.value
                    currentState.copy(
                        movieViewState = currentState.movieViewState.copy(
                            movies = it,
                            isLoading = false
                        )
                    )
                }.onStart {
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            movieViewState = currentState.movieViewState.copy(
                                isLoading = true
                            )
                        )
                    )
                }.catch {
                    StackTrace.printStackTrace(it)
                    val currentState = _viewState.value
                    emit(
                        currentState.copy(
                            movieViewState = currentState.movieViewState.copy(
                                isLoading = false,
                                error = it
                            )
                        )
                    )
                }.collect {
                    _viewState.emit(it)
                }
        }
    }

    fun loadMoviePaging(genreId: Long) = getMoviesPaging(genreId).cachedIn(viewModelScope)

    fun selectGenre(genre: Genre) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(
                    selectedGenre = genre
                )
            )
//            loadMovies(genre.id, false)
        }
    }
}
