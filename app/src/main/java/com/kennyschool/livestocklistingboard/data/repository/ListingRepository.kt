package com.kennyschool.livestocklistingboard.data.repository

import com.kennyschool.livestocklistingboard.data.model.Listing

interface ListingRepository {
    suspend fun loadListings(): List<Listing>
}