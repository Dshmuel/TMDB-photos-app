package com.dimovsoft.shutterfly.data

import androidx.paging.PagingData
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface RepositoryContract {

	suspend fun getGenres(): List<Genre>

	fun getMovies(genre: Int): Flow<PagingData<Movie>>
	fun getMoviesPager(scope: CoroutineScope, genreId: Int): Flow<PagingData<Movie>>

	suspend fun getConfiguration()
}