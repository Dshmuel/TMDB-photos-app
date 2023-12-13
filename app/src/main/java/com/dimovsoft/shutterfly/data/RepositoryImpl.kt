package com.dimovsoft.shutterfly.data

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dimovsoft.shutterfly.model.Configuration.Companion.currentConfiguration
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.model.Movie
import com.dimovsoft.shutterfly.util.bestImageSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context,
	private val apiService: ApiService
): RepositoryContract {

	// Map of all genre -> Pager pairs. Saving Pager for each genre to save experience in that page if user come back later
	private val pagers = mutableMapOf<Int, Flow<PagingData<Movie>>>()

	override suspend fun getGenres(): List<Genre> {
		return try {
			apiService.getGenres().genres ?: emptyList()
		} catch (exception: Exception) {
			Log.e("RepositoryImpl", "Network error: ${exception.message}")
			emptyList()
		}
	}

	override fun getMovies(genre: Int): Flow<PagingData<Movie>> =
		Pager(
			config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
			pagingSourceFactory = { MoviesPagingSource(apiService, genre.toString()) }
		).flow

	override suspend fun getConfiguration() {
		try {
			currentConfiguration = apiService.getConfiguration().images ?: return
			currentConfiguration.bestDpi = bestImageSize(context, currentConfiguration.posterSizes!!)
		} catch (exception: Exception) {
			Log.e("RepositoryImpl", "Network error: ${exception.message}")
		}
	}

	override fun getMoviesPager(scope: CoroutineScope, genreId: Int): Flow<PagingData<Movie>> {
		return pagers[genreId] ?: getMovies(genreId).cachedIn(scope)
			.also { pagers[genreId] = it }
	}

	companion object {
		private const val NETWORK_PAGE_SIZE = 20
	}

}