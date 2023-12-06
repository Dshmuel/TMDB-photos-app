package com.dimovsoft.shutterfly.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dimovsoft.shutterfly.data.RepositoryContract
import com.dimovsoft.shutterfly.model.Genre
import com.dimovsoft.shutterfly.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalFoundationApi @HiltViewModel
class MainViewModel @Inject constructor(private val repository: RepositoryContract): ViewModel() {

	init {
		viewModelScope.launch {
			repository.getConfiguration()
		}
	}

	// Map of all genre -> Pager pairs. Saving Pager for each genre to save experience in that page if user come back later
	val pagers = mutableMapOf<Int, Flow<PagingData<Movie>>>()

	/**
	 * State of current selected index
	 */
	private val _currentPage = MutableStateFlow(0)
	val currentPage: StateFlow<Int> = _currentPage

	/**
	 * State of current genres list
	 */
	private val _genresList = MutableStateFlow<List<Genre>>(listOf())
	val genresList: StateFlow<List<Genre>> get() = _genresList

	fun genreClicked(newGenreIndex: Int) {
		_currentPage.value = newGenreIndex
	}

	fun loadGenres() {
		viewModelScope.launch(Dispatchers.IO) {
			_genresList.value = repository.getGenres()
		}
	}

	// Get existing pager or create a new one if needed
	fun getPagingData(genreIndex: Int): Flow<PagingData<Movie>> {
		val genreId = _genresList.value.getOrNull(genreIndex)?.id ?: 0
		return pagers[genreId] ?: repository.getMovies(genreId).cachedIn(viewModelScope)
			.also { pagers[genreId] = it }
	}
}