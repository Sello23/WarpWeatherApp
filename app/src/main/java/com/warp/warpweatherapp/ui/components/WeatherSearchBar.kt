package com.warp.warpweatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.warp.warpweatherapp.ui.theme.WarpWeatherTextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(query) { query.trim().isNotEmpty() }
    var expanded by remember { mutableStateOf(false) }

    SearchBar(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        inputField = {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                label = { Text(text = "Search city") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (valid) {
                            onSearch()
                            keyboardController?.hide()
                            expanded = false
                        }
                    }
                ),
                shape = RoundedCornerShape(24.dp),
                colors = WarpWeatherTextFieldDefaults.colors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (valid) {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    onSearch()
                    keyboardController?.hide()
                    expanded = false
                }
            ) {
                Text("Search \"$query\"")
            }
        }
    }
}