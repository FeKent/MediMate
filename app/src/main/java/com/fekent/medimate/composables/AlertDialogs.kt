package com.fekent.medimate.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, medName: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        text = { Text(text = "Delete ${medName}?", color = MaterialTheme.colorScheme.onSurface) }
    )
}

@Composable
fun EditAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, medName: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        text = { Text(text = "Edit ${medName}?", color = MaterialTheme.colorScheme.onSurface) }
    )
}

@Composable
fun ValidationDialog(
    label: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) { Text(text = "OK") }
        }, text = {
            Text(text = "$label field(s) invalid")
        }
    )
}