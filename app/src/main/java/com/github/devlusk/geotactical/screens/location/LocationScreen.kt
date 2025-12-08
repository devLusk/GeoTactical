package com.github.devlusk.geotactical.screens.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.geotactical.screens.location.components.CurrentNeighborhood
import com.github.devlusk.geotactical.screens.location.components.LocationDetails
import com.github.devlusk.geotactical.screens.location.components.LocationHeader
import com.github.devlusk.geotactical.screens.location.components.MapPlaceholder
import com.github.devlusk.geotactical.screens.location.components.PositionOptions

@Composable
fun LocationScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            LocationHeader({}) // TODO: Add navigation to configurations

            Spacer(modifier = Modifier.height(16.dp))

            MapPlaceholder()

            Spacer(modifier = Modifier.height(16.dp))

            CurrentNeighborhood("") // TODO: Pass current neighborhood

            Spacer(modifier = Modifier.height(16.dp))

            PositionOptions(false, {}, {}) // TODO: More shit to do

            Spacer(modifier = Modifier.height(16.dp))

            LocationDetails(
                latitude = "-22.7554",
                longitude = "-47.0094",
                fullAddress = "Main Avenue, Downtown, City XP",
                status = "Click \"GET CURRENT POSITION\" or enable tracking."
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocationScreenPreview() {
    LocationScreen()
}