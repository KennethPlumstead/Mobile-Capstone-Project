package com.kennyschool.livestocklistingboard.data.repository

import android.content.Context
import com.kennyschool.livestocklistingboard.data.model.Listing
import kotlinx.serialization.json.Json

class AssetListingRepository(
    private val context: Context
) : ListingRepository {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun loadListings(): List<Listing> {
        val rawJson = context.assets
            .open("listings.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(rawJson)
    }
}