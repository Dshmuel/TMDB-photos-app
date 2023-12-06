package com.dimovsoft.shutterfly.ui.components.genres_tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.ui.theme.Transparent

@Composable
fun GenresTabs(
	genresList: State<List<Genre>>,
	selectedTabIndex: Int,
	onClick: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	ScrollableTabRow(
		edgePadding = 0.dp,
		selectedTabIndex = selectedTabIndex,
		containerColor = Transparent,
		modifier = modifier.padding(16.dp),
	) {
		if (genresList.value.isEmpty()) {
			NoGenresTab()
			return@ScrollableTabRow
		}

		genresList.value.forEachIndexed { index, genre ->
			Genre(
				genre,
				onClick = { onClick(index) },
			)
		}
	}
}

@Preview
@Composable
fun GenresTabsPreview() {
	val genres = List(10) { index -> Genre(index, "Action_$index") }
	GenresTabs(genresList = mutableStateOf(genres), selectedTabIndex = 0, onClick = {})
}