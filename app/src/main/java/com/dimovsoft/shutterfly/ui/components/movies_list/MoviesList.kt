package com.dimovsoft.shutterfly.ui.components.movies_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dimovsoft.shutterfly.R
import com.dimovsoft.shutterfly.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

const val COLUMNS = 2

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesList(
	moviesFlow: Flow<PagingData<Movie>>,
	innerPadding: PaddingValues,
	modifier: Modifier = Modifier,
) {
	val pagingItems: LazyPagingItems<Movie> = moviesFlow.collectAsLazyPagingItems()
	val refreshing by remember { mutableStateOf(false) }
	val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh = {
		pagingItems.refresh()
	})

	Box(Modifier.pullRefresh(pullRefreshState).fillMaxSize().padding(innerPadding)) {

		when {
			// Show loading state
			pagingItems.loadState.refresh is LoadState.Loading ||
					pagingItems.loadState.prepend is LoadState.Loading -> {
						LoadingIndicator(modifier = Modifier.align(Alignment.Center))
			}

			// Show empty state
			pagingItems.loadState.prepend is LoadState.NotLoading && pagingItems.itemCount == 0 -> {
				EmptyIndicator(modifier = Modifier.align(Alignment.Center))
			}

			else -> {
				LazyVerticalGrid(
					columns = GridCells.Fixed(COLUMNS),
					modifier = modifier
						.fillMaxSize(),
					contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.margin_normal)),
					state = rememberLazyGridState(),
				) {

					items(
						count = pagingItems.itemCount,
						key = { index ->
							val movie = pagingItems[index]
							"${movie?.id ?: ""}${index}"
						}
					) { index ->
						val movie = pagingItems[index] ?: return@items
						MovieItem(movie = movie)
					}
				}
			}
		}

		PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
	}
}

@Preview
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
	CircularProgressIndicator(
		modifier = modifier.size(48.dp),
	)
}

@Preview
@Composable
fun EmptyIndicator(modifier: Modifier = Modifier) {
	Text(
		text = stringResource(id = R.string.no_items),
		modifier = modifier,
		style = TextStyle(color = colorResource(id = R.color.white))
	)
}

@Preview
@Composable
fun MoviesListPreview() {
	val movies = listOf(
		Movie(951546, "", "Reign of Chaos", "2022-04-12"),
		Movie(670292, "", "The Creator", "2023-09-27"),
		Movie(670292, "", "The Creator", "2023-09-27"),
		Movie(951546, "", "Reign of Chaos", "2022-04-12"),
	)

	val moviesFlow = flowOf(PagingData.from(movies))
	MoviesList(moviesFlow = moviesFlow, innerPadding = PaddingValues())
}