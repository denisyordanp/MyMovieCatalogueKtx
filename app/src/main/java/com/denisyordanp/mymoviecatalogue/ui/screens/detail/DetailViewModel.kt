package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

) : ViewModel() {
    private val _viewState = MutableStateFlow(DetailViewState.idle())
    val viewState = _viewState.asStateFlow()

    fun loadMovieDetail(movieId: Int) {

    }

}