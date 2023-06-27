package com.denisyordanp.mymoviecatalogue.schemas.ui

import com.denisyordanp.mymoviecatalogue.ui.components.ChipItem

data class Genre(
    val id: Int,
    override val name: String
): ChipItem