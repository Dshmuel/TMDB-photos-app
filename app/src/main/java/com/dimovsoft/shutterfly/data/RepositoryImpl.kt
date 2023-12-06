package com.dimovsoft.shutterfly.data

import android.content.Context
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dimovsoft.shutterfly.model.Configuration.Companion.currentConfiguration
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.model.Movie
import com.dimovsoft.shutterfly.util.bestImageSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context,
	private val apiService: ApiService
): RepositoryContract {

	override suspend fun getGenres(): List<Genre> {
		return try {
			apiService.getGenres().genres ?: emptyList()
		} catch (exception: Exception) {
			Toast.makeText(context, "Network error: ${exception.message}", Toast.LENGTH_LONG).show()
			emptyList()
		}
	}

	override fun getMovies(genre: Int): Flow<PagingData<Movie>> =
		Pager(
			config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
			pagingSourceFactory = { MoviesPagingSource(apiService, genre.toString()) }
		).flow

	override suspend fun getConfiguration() {
		return try {
			currentConfiguration = apiService.getConfiguration().images ?: return
			currentConfiguration.bestDpi = bestImageSize(context, currentConfiguration.posterSizes!!)
		} catch (exception: Exception) {
			Toast.makeText(context, "Network error: ${exception.message}", Toast.LENGTH_LONG).show()
		}
	}

	companion object {
		private const val NETWORK_PAGE_SIZE = 20
	}

}