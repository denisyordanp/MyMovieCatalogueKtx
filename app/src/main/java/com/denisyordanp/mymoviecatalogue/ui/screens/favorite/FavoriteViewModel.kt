package com.denisyordanp.mymoviecatalogue.ui.screens.favorite

import androidx.lifecycle.ViewModel
import com.denisyordanp.mymoviecatalogue.usecase.GetFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
) : ViewModel() {

    val activeFavorites get() = getFavorites.invoke()
}
