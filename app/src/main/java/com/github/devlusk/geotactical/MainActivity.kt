package com.github.devlusk.geotactical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.github.devlusk.geotactical.util.LocationUtils
import com.github.devlusk.geotactical.ui.location.LocationScreen
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