package com.dimovsoft.shutterfly.model

import com.dimovsoft.shutterfly.util.extractYear
import com.google.gson.annotations.SerializedName

data class Movie(
	@SerializedName("id") val id: Int?,
	@SerializedName("poster_path") val posterPath: String?,
	@SerializedName("title") val title: String?,
	@SerializedName("release_date") val releasedDate: String?,
) {
	val year: String get() = releasedDate.extractYear()
}

data class MoviesResponse(
	@SerializedName("page") val page: Int?,
	@SerializedName("total_pages") val totalPages: Int?,
	@SerializedName("results") val results: List<Movie>?,
)