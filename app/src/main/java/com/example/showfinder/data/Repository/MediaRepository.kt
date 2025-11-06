package com.example.showfinder.data.Repository

import android.util.Printer
import com.example.showfinder.data.model.Media
import com.example.showfinder.data.model.Movie
import com.example.showfinder.data.model.SearchItem
import com.example.showfinder.data.model.TvShow
import com.example.showfinder.data.model.network.ApiService
import com.example.showfinder.data.network.OmdbApiService
import com.example.showfinder.data.network.OmdbdResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import kotlinx.coroutines.rx3.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepository @Inject constructor(
    private val apiService: ApiService,
    private val omdbApi: OmdbApiService
) {

    // funcation to show movie and tvshows
    fun getMoviesAndTvShows(): Single<Pair<List<Movie>, List<TvShow>>> {

        val movie = apiService.getMovie().map { it.titles ?: emptyList() }
        val tvShow = apiService.getTvShow().map { it.titles ?: emptyList() }

        return Single.zip(
            movie, tvShow,
            BiFunction<List<Movie>, List<TvShow>, Pair<List<Movie>, List<TvShow>>> { movie, tvShow ->
                Pair(movie, tvShow)
            }
        )
    }
    // funcation for search movie and show
    fun getSearchMedia(query: String): Single<Pair<List<SearchItem>, List<SearchItem>>> {
        return apiService.searchMedia(query = query)
            .map {response ->
                val movie = response.titles?.filter {
                    it.type.equals("movie", ignoreCase = true)
                } ?: emptyList()
                val tvShow = response.titles?.filter {
                    it.type.equals("tv_series", ignoreCase = true)
                } ?: emptyList()
                Pair(movie,tvShow)
            }
    }
    // funcation for get posters or image
    suspend fun getPostersByImbd(imdbID: String): String {
        val response = omdbApi.getMovieByImdbID(imdbID).await()
        return if (response.Response.equals("True", ignoreCase = true)) {
            response.Poster ?: ""
        } else ""
    }

    // funcatoin to get data from Imdb by passing id
    suspend fun getMediaDetailsByImdb(imdbID: String): Media? {
        return try {
            val response = omdbApi.getMovieByImdbID(imdbID).await()
            if (response.Response.equals("True", ignoreCase = true)) {
                Media(
                    title = response.Title ?: "Unknown",
                    year = response.Year?.toIntOrNull(),
                    type = response.Type ?: "movie",
                    imdb_id = response.imdbID,
                    poster = response.Poster,
                    releaseDate = response.Released,
                    overview = response.Plot,
                    runtime = response.Runtime,
                    genre = response.Genre,
                    director = response.Director,
                    writer = response.Writer,
                    actors = response.Actors,
                    rated = response.Rated,
                    awards = response.Awards
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
