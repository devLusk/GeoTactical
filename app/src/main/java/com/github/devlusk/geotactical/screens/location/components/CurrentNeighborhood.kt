package com.github.devlusk.geotactical.screens.location.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentNeighborhood(address: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFDC62E),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            Text(
                text = "CURRENT NEIGHBORHOOD:",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold

            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = address.uppercase(),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentNeighborhoodPreview() {
    CurrentNeighborhood("Alto da Serra")
}