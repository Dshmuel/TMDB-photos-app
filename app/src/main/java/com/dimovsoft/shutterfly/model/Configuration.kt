package com.dimovsoft.shutterfly.model

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
	@SerializedName("images") val images: Configuration?
)

data class Configuration(
	@SerializedName("secure_base_url") val secureBaseUrl: String?,
	@SerializedName("poster_sizes") val posterSizes: List<String>?,
) {
	var bestDpi: String = ""
	val completeBaseUrl get() = "${secureBaseUrl}${bestDpi}/"

	companion object {
		var currentConfiguration = defaultConfiguration
	}
}

private val defaultConfiguration = Configuration(
	secureBaseUrl = "https://image.tmdb.org/t/p/",
	posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original")
)