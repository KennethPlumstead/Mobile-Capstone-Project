package com.kennyschool.livestocklistingboard.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Listing(
    val id: String,
    val title: String,
    val species: String,      // "Chicken" | "Goat" | "Cattle"
    val price: Int,
    val location: String,
    val description: String,
    val images: List<String>,
    val sellerName: String,
    val contactHint: String
)