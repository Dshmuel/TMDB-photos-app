package com.dimovsoft.shutterfly.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dimovsoft.shutterfly.model.Movie
import javax.inject.Inject

private const val MOVIE_STARTING_PAGE_INDEX = 1

/**
 * Class responsible to request movies from the network as needed more.
 */
class MoviesPagingSource @Inject constructor(
	private val apiService: ApiService,
	private val genres: String
) : PagingSource<Int, Movie>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
		val page = params.key ?: MOVIE_STARTING_PAGE_INDEX
		return try {
			val response = apiService.getMovies(page, genres)
			val movies = response.results
			LoadResult.Page(
				data = movies ?: emptyList(),
				prevKey = null, // No need backward
				nextKey = if (page >= (response.totalPages ?: 0)) null else page.plus(1)
			)
		} catch (exception: Exception) {
			LoadResult.Error(exception)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey
		}
	}
}