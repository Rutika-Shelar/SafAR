package com.example.veezar.ai_Feature

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun TripResultScreen(response: String, onBack: () -> Unit) {
    val responseMap = response.split("\n")
        .mapNotNull { line ->
            val parts = line.split(":", limit = 2)
            if (parts.size == 2) {
                parts[0].trim() to cleanMarkdownContent(parts[1].trim())
            } else {
                null
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Trip Planning Result",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(responseMap) { (title, content) ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (title.equals("Photo URL", ignoreCase = true)) {
                        Log.d("TripResultScreen", "Loading image from URL: $content")
                        Image(
                            painter = rememberAsyncImagePainter(content),
                            contentDescription = "Trip  Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .height(180.dp)
                        )
                    } else {
                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}