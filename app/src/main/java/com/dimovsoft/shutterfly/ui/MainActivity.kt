package com.dimovsoft.shutterfly.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import com.dimovsoft.shutterfly.ui.components.Toolbar
import com.dimovsoft.shutterfly.ui.components.genres_tabs.GenresTabs
import com.dimovsoft.shutterfly.ui.components.movies_list.MoviesList
import com.dimovsoft.shutterfly.ui.theme.Black
import com.dimovsoft.shutterfly.ui.theme.ShutterflyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

	private val mainViewModel by viewModels<MainViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainViewModel.loadGenres()

		setContent {
			ShutterflyTheme {
				val currentPage = mainViewModel.currentPage.collectAsState()
				val pagerState = rememberPagerState(pageCount = { mainViewModel.genresList.value.size })

				// Update genres toolbar to correct place
				LaunchedEffect(pagerState) {
					snapshotFlow { pagerState.currentPage }
						.distinctUntilChanged()
						.collect { index ->
							mainViewModel.genreClicked(index)
						}
				}

				// Update pager to correct page
				LaunchedEffect(currentPage) {
					snapshotFlow { currentPage.value }
						.distinctUntilChanged()
						.collect { index ->
							pagerState.animateScrollToPage(index)
						}
				}

				Scaffold(
					topBar = {
						Column {
							Toolbar()
							GenresTabs(
								genresList = mainViewModel.genresList.collectAsState(),
								selectedTabIndex = currentPage.value,
								onClick = { newGenreIndex -> mainViewModel.genreClicked(newGenreIndex) }
							)
						}
					},
					containerColor = Black,
				) { innerPadding ->
					HorizontalPager(
						pagerState
					) { pageIndex ->
						MoviesList(mainViewModel.getPagingData(pageIndex), innerPadding)
					}
				}
			}
		}
	}
}