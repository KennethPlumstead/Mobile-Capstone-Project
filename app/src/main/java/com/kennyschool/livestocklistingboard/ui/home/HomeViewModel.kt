package com.kennyschool.livestocklistingboard.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kennyschool.livestocklistingboard.data.model.Listing
import com.kennyschool.livestocklistingboard.data.repository.ListingRepository
import com.kennyschool.livestocklistingboard.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: ListingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Listing>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Listing>>> = _uiState

    private val _filters = MutableStateFlow(FilterState())
    val filters: StateFlow<FilterState> = _filters

    private var allListings: List<Listing> = emptyList()

    fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val items = repo.loadListings()
                allListings = items
                _uiState.value = UiState.Success(applyFilters(items, _filters.value))
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateFilters(newState: FilterState) {
        _filters.value = newState
        val filtered = applyFilters(allListings, newState)
        _uiState.value = UiState.Success(filtered)
    }

    private fun applyFilters(list: List<Listing>, f: FilterState): List<Listing> {
        return list
            .filter { f.species == "All" || it.species.equals(f.species, ignoreCase = true) }
            .filter { it.price in f.minPrice..f.maxPrice }
            .filter { f.locationQuery.isBlank() || it.location.contains(f.locationQuery, ignoreCase = true) }
            .filter {
                if (f.query.isBlank()) true
                else it.title.contains(f.query, ignoreCase = true) ||
                        it.description.contains(f.query, ignoreCase = true)
            }
            .sortedBy { it.price }
    }
}