package com.dimovsoft.shutterfly.ui.components.movies_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dimovsoft.shutterfly.R
import com.dimovsoft.shutterfly.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

const val COLUMNS = 2

@Composable
fun MoviesList(
	moviesFlow: Flow<PagingData<Movie>>,
	innerPadding: PaddingValues,
	modifier: Modifier = Modifier
) {
	val pagingItems: LazyPagingItems<Movie> = moviesFlow.collectAsLazyPagingItems()

	LazyVerticalGrid(
		columns = GridCells.Fixed(COLUMNS),
		modifier = modifier
			.padding(innerPadding)
			.fillMaxSize(),
		contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.margin_normal)),
	) {

		items(
			count = pagingItems.itemCount,
			key = { index ->
				val movie = pagingItems[index]
				"${ movie?.id ?: ""}${index}"
			}
		) { index ->
			val movie = pagingItems[index] ?: return@items
			MovieItem(movie = movie)
		}
	}
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