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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.dimovsoft.shutterfly.ui.components.Toolbar
import com.dimovsoft.shutterfly.ui.components.genres_tabs.GenresTabs
import com.dimovsoft.shutterfly.ui.components.movies_list.MoviesList
import com.dimovsoft.shutterfly.ui.theme.Black
import com.dimovsoft.shutterfly.ui.theme.ShutterflyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

	private val mainViewModel by viewModels<MainViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainViewModel.loadGenres()

		setContent {
			ShutterflyTheme {
				val pagerState = rememberPagerState(pageCount = { mainViewModel.genresList.value.size })
				val coroutineScope = rememberCoroutineScope()

				Scaffold(
					topBar = {
						Column {
							Toolbar()
							GenresTabs(
								genresList = mainViewModel.genresList.collectAsState(),
								pagerState = pagerState,
								onClick = { newGenreIndex ->
									coroutineScope.launch {
										pagerState.animateScrollToPage(newGenreIndex)
									}
								}
							)
						}
					},
					containerColor = Black,
				) { innerPadding ->
					if (pagerState.pageCount == 0) { // Nothing to show atm
						return@Scaffold
					}

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