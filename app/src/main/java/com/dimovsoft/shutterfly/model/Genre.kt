package com.dimovsoft.shutterfly.model

import com.google.gson.annotations.SerializedName

data class Genre(
	@SerializedName("id") val id: Int?,
	@SerializedName("name") val name: String?,
)

data class GenresResponse(
	@SerializedName("genres") val genres: List<Genre>?,
)