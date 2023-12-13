package com.dimovsoft.shutterfly.ui.components.genres_tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.ui.theme.Transparent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenresTabs(
	genresList: State<List<Genre>>,
	pagerState: PagerState,
	onClick: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	ScrollableTabRow(
		edgePadding = 0.dp,
		selectedTabIndex = pagerState.currentPage,
		containerColor = Transparent,
		modifier = modifier.padding(16.dp),
		indicator = @Composable { tabPositions: List<TabPosition> ->
			TabRowDefaults.Indicator(
				Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
			)
		}
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

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun GenresTabsPreview() {
	val genres = remember { mutableStateOf(List(10) { index -> Genre(index, "Action_$index") }) }
	val pagerState = rememberPagerState(initialPage = 1, pageCount = { 1 })

	GenresTabs(genresList = genres, pagerState, onClick = {})
}