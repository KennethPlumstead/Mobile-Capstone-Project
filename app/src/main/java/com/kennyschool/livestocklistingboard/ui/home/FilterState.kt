package com.kennyschool.livestocklistingboard.ui.home

data class FilterState(
    val query: String = "",
    val species: String = "All",
    val locationQuery: String = "",
    val minPrice: Int = 0,
    val maxPrice: Int = 10000
)