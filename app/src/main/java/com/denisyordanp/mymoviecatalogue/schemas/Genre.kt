package com.denisyordanp.mymoviecatalogue.schemas

import com.denisyordanp.mymoviecatalogue.ui.components.ChipItem

data class Genre(
    val id: Int,
    override val name: String
): ChipItem