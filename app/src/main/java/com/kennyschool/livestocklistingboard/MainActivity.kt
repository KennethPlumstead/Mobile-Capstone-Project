package com.kennyschool.livestocklistingboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kennyschool.livestocklistingboard.data.model.Listing
import com.kennyschool.livestocklistingboard.data.repository.AssetListingRepository
import com.kennyschool.livestocklistingboard.ui.components.ListingCard
import com.kennyschool.livestocklistingboard.ui.detail.DetailScreen
import com.kennyschool.livestocklistingboard.ui.home.HomeFilters
import com.kennyschool.livestocklistingboard.ui.home.HomeViewModel
import com.kennyschool.livestocklistingboard.ui.nav.Routes
import com.kennyschool.livestocklistingboard.ui.theme.LivestockListingBoardTheme
import com.kennyschool.livestocklistingboard.util.UiState

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LivestockListingBoardTheme {
                val context = LocalContext.current
                val nav = rememberNavController()

                val vm: HomeViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return HomeViewModel(AssetListingRepository(context)) as T
                        }
                    }
                )

                val uiState by vm.uiState.collectAsState()
                val filters by vm.filters.collectAsState()

                LaunchedEffect(Unit) { vm.load() }

                // Selected listing (kept simple for capstone speed)
                var selected by remember { mutableStateOf<Listing?>(null) }

                NavHost(navController = nav, startDestination = Routes.HOME) {

                    composable(Routes.HOME) {
                        Scaffold(
                            topBar = { TopAppBar(title = { Text("Livestock Listing Board") }) }
                        ) { padding ->
                            Column(
                                modifier = Modifier
                                    .padding(padding)
                                    .padding(16.dp)
                            ) {
                                // ✅ Filters UI
                                HomeFilters(
                                    state = filters,
                                    onChange = { vm.updateFilters(it) }
                                )

                                Spacer(Modifier.height(12.dp))

                                when (uiState) {
                                    is UiState.Loading -> CircularProgressIndicator()

                                    is UiState.Error -> Text(
                                        text = "Error loading listings",
                                        color = MaterialTheme.colorScheme.error
                                    )

                                    is UiState.Success -> {
                                        val listings = (uiState as UiState.Success).data

                                        if (listings.isEmpty()) {
                                            Text("No listings match your filters.")
                                        } else {
                                            LazyColumn(
                                                verticalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                items(listings) { item ->
                                                    ListingCard(item = item) {
                                                        selected = item
                                                        nav.navigate(Routes.DETAIL)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    composable(Routes.DETAIL) {
                        val listing = selected
                        if (listing != null) {
                            DetailScreen(
                                listing = listing,
                                onBack = { nav.popBackStack() }
                            )
                        } else {
                            Text("No listing selected.")
                        }
                    }
                }
            }
        }
    }
}