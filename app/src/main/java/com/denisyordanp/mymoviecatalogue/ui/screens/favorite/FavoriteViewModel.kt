package com.denisyordanp.mymoviecatalogue.ui.screens.favorite

import androidx.lifecycle.ViewModel
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getFavorites: GetFavorites
) : ViewModel() {

    val favorites = getFavorites()
}
