package com.dimovsoft.shutterfly.ui.components.genres_tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimovsoft.shutterfly.R
import com.dimovsoft.shutterfly.model.Genre

@Composable
fun Genre(
	genre: Genre,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Text(
		text = genre.name ?: stringResource(R.string.unknown_name),
		textAlign = TextAlign.Center,
		modifier = modifier
			.paddingFromBaseline(16.dp)
			.clickable { onClick() },
	)
}

@Preview(showBackground = true)
@Composable
fun GenrePreview() {
	val genre = Genre(0, "Action")
	Genre(genre = genre, onClick = {})
}