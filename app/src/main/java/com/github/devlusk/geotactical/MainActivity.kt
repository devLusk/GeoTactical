package com.github.devlusk.geotactical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.devlusk.geotactical.screens.location.LocationScreen
import com.github.devlusk.geotactical.ui.theme.GeoTacticalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoTacticalTheme {
                LocationScreen()
            }
        }
    }
}