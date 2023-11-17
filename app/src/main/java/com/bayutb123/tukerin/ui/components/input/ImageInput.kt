package com.bayutb123.tukerin.ui.components.input

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun ImageInput(
    modifier: Modifier = Modifier
) {
    val selectedImage = remember { mutableStateListOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedImage.apply {
            clear()
            addAll(it)
        }
    }

    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(selectedImage) {uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = uri.toString(),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Log.d("TestImageUri", uri.toString())
            }
        }

        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Select Image")
        }

    }
}

@Preview
@Composable
fun PreviewImageInput() {
    TukerInTheme {
        ImageInput()
    }
}