package com.github.devlusk.geotactical.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionInfoDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Required permissions") },
        text = {
            Text("We need access to the location to calculate distances and display the information in the app.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Understood")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Not now")
            }
        }
    )
}

@Preview
@Composable
private fun PermissionInfoDialogPreview() {
    PermissionInfoDialog({}, {})
}