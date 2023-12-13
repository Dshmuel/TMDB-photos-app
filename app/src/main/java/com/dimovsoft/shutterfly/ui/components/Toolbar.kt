package com.dimovsoft.shutterfly.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dimovsoft.shutterfly.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun Toolbar(modifier: Modifier = Modifier) {
	CenterAlignedTopAppBar(
		title = {
			Text(
				text = "Genres",
				textAlign = TextAlign.Center,
				modifier = modifier,
				color = Color.White,
			)
		},
		navigationIcon = {
			IconButton(onClick = {}) {
				Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
			}
		},
		colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Black),
	)
}