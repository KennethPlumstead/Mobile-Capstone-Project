package com.kennyschool.livestocklistingboard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kennyschool.livestocklistingboard.R
import com.kennyschool.livestocklistingboard.data.model.Listing

@Composable
fun ListingCard(
    item: Listing,
    onClick: () -> Unit
) {
    val imageRes = when (item.species.lowercase()) {
        "chicken" -> R.drawable.chicken
        "goat" -> R.drawable.goat
        "cattle" -> R.drawable.cattle
        else -> R.drawable.cattle
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = item.title,
                modifier = Modifier.size(88.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${item.species} • ${item.location}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(1.dp))
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}