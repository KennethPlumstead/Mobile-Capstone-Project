package com.kennyschool.livestocklistingboard.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kennyschool.livestocklistingboard.R
import com.kennyschool.livestocklistingboard.data.model.Listing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    listing: Listing,
    onBack: () -> Unit
) {
    val imageRes = when (listing.species.lowercase()) {
        "chicken" -> R.drawable.chicken
        "goat" -> R.drawable.goat
        "cattle" -> R.drawable.cattle
        else -> R.drawable.cattle
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listing Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(listing.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(6.dp))
            Text("${listing.species} • ${listing.location}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(6.dp))
            Text("$${listing.price}", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(12.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = listing.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(Modifier.height(12.dp))
            Text(listing.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(16.dp))
            Text("Seller: ${listing.sellerName}")
            Text(listing.contactHint, style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* mock */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contact Seller (Prototype)")
            }
        }
    }
}