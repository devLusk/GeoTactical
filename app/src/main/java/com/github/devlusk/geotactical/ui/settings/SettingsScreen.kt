package com.github.devlusk.geotactical.ui.settings

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
import com.github.devlusk.geotactical.ui.settings.components.SettingsHeader
import com.github.devlusk.geotactical.ui.settings.components.UpdateIntervalSlider

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            SettingsHeader({}) // TODO: Add navigation to LocationScreen

            Spacer(modifier = Modifier.height(16.dp))
            
            UpdateIntervalSlider(
                sliderPosition = 1f,
                onSliderPositionChange = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}