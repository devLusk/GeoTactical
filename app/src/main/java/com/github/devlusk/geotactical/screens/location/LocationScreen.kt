package com.github.devlusk.geotactical.screens.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.geotactical.screens.location.components.CurrentNeighborhood
import com.github.devlusk.geotactical.screens.location.components.LocationHeader

@Composable
fun LocationScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            LocationHeader({}) // TODO: Add navigation to configurations

            Spacer(modifier = Modifier.height(16.dp))

            CurrentNeighborhood("Alto da Serra")

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocationScreenPreview() {
    LocationScreen()
}