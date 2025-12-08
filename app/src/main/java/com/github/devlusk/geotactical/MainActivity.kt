package com.github.devlusk.geotactical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.github.devlusk.geotactical.data.location.LocationUtils
import com.github.devlusk.geotactical.screens.location.LocationScreen
import com.github.devlusk.geotactical.ui.theme.GeoTacticalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            GeoTacticalTheme {
                LocationScreen(
                    locationUtils = LocationUtils(context)
                )
            }
        }
    }
}