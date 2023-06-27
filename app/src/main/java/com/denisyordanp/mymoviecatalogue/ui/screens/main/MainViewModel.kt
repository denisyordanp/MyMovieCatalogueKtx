package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.mymoviecatalogue.domain.GetGenres
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
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
    private val getGenres: GetGenres
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
                        genres = it,
                        error = null,
                        isLoading = false,
                        selectedGenre = selectedGenre
                    )
                }.onStart {
                    emit(
                        _viewState.value.copy(
                            isLoading = true
                        )
                    )
                }.catch {
                    emit(
                        _viewState.value.copy(
                            error = it,
                            isLoading = false
                        )
                    )
                }.collect {
                    _viewState.emit(it)
                }
        }
    }

    fun selectGenre(genre: Genre) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(
                    selectedGenre = genre
                )
            )
        }
    }
}