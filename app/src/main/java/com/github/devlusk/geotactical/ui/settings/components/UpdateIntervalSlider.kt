package com.github.devlusk.geotactical.ui.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UpdateIntervalSlider(
    sliderPosition: Float = 1f,
    onSliderPositionChange: (Float) -> Unit
) {
    val intervalMs = (sliderPosition.toLong() * 1000).toInt()

    Surface(
        shadowElevation = 3.dp,
        tonalElevation = 3.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Constant Update Frequency ($intervalMs ms)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = sliderPosition,
                onValueChange = onSliderPositionChange,
                valueRange = 1f..5f,
                steps = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Interval between updates in constant mode",
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateIntervalSliderPreview() {
    UpdateIntervalSlider(
        sliderPosition = 1f,
        onSliderPositionChange = {}
    )
}