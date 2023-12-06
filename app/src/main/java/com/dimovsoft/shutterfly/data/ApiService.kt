package com.dimovsoft.shutterfly.data

import com.dimovsoft.shutterfly.BuildConfig
import com.dimovsoft.shutterfly.model.ConfigurationResponse
import com.dimovsoft.shutterfly.model.GenresResponse
import com.dimovsoft.shutterfly.model.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

	@GET("genre/movie/list")
	suspend fun getGenres(): GenresResponse

	@GET("discover/movie")
	suspend fun getMovies(
		@Query("page") page: Int,
		@Query("with_genres") genre: String,
	): MoviesResponse

	@GET("configuration")
	suspend fun getConfiguration(): ConfigurationResponse


	companion object {
		private const val BASE_URL = "https://api.themoviedb.org/3/"

		fun create(): ApiService {
			val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

			val client = OkHttpClient.Builder()
				.addInterceptor(logger)
				.addInterceptor { chain ->
					val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}").build()
					chain.proceed(request)
				}
				.build()

			return Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(ApiService::class.java)
		}
	}
}