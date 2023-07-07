package views.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(message: String, onRetry: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = { onRetry() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text(text = "Retry")
            }
        }

    }
}