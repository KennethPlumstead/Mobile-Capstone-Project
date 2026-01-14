package com.kennyschool.livestocklistingboard.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeFilters(
    state: FilterState,
    onChange: (FilterState) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = state.query,
            onValueChange = { onChange(state.copy(query = it)) },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SpeciesDropdown(
                value = state.species,
                onSelect = { onChange(state.copy(species = it)) },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = state.locationQuery,
                onValueChange = { onChange(state.copy(locationQuery = it)) },
                label = { Text("Location") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text("Price: $${state.minPrice} – $${state.maxPrice}")

        RangeSlider(
            value = state.minPrice.toFloat()..state.maxPrice.toFloat(),
            onValueChange = { range ->
                onChange(
                    state.copy(
                        minPrice = range.start.toInt(),
                        maxPrice = range.endInclusive.toInt()
                    )
                )
            },
            valueRange = 0f..10000f
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpeciesDropdown(
    value: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("All", "Chicken", "Goat", "Cattle")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Species") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt) },
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}