package com.dimovsoft.shutterfly.ui.components.genres_tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun NoGenresTab(
	modifier: Modifier = Modifier,
) {
	Text(
		text = "LOADING...",
		textAlign = TextAlign.Center,
		modifier = modifier
			.padding(16.dp)
			.fillMaxWidth(),
	)
}