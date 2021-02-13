package com.michaldrabik.network.trakt.api

import com.michaldrabik.network.Config.TRAKT_CLIENT_ID
import com.michaldrabik.network.Config.TRAKT_CLIENT_SECRET
import com.michaldrabik.network.Config.TRAKT_REDIRECT_URL
import com.michaldrabik.network.Config.TRAKT_SYNC_PAGE_LIMIT
import com.michaldrabik.network.trakt.model.Comment
import com.michaldrabik.network.trakt.model.Episode
import com.michaldrabik.network.trakt.model.Movie
import com.michaldrabik.network.trakt.model.OAuthResponse
import com.michaldrabik.network.trakt.model.Show
import com.michaldrabik.network.trakt.model.SyncExportRequest
import com.michaldrabik.network.trakt.model.SyncItem
import com.michaldrabik.network.trakt.model.request.CommentRequest
import com.michaldrabik.network.trakt.model.request.OAuthRefreshRequest
import com.michaldrabik.network.trakt.model.request.OAuthRequest
import com.michaldrabik.network.trakt.model.request.OAuthRevokeRequest
import com.michaldrabik.network.trakt.model.request.RatingRequest
import com.michaldrabik.network.trakt.model.request.RatingRequestValue
import java.lang.System.currentTimeMillis

class TraktApi(private val service: TraktService) {

  suspend fun fetchShow(traktId: Long) = service.fetchShow(traktId)

  suspend fun fetchMovie(traktId: Long) = service.fetchMovie(traktId)

  suspend fun fetchPopularShows(genres: String) = service.fetchPopularShows(genres)

  suspend fun fetchPopularMovies(genres: String) = service.fetchPopularMovies(genres)

  suspend fun fetchTrendingShows(genres: String, limit: Int) = service.fetchTrendingShows(genres, limit).map { it.show!! }

  suspend fun fetchTrendingMovies(genres: String, limit: Int) = service.fetchTrendingMovies(genres, limit).map { it.movie!! }

  suspend fun fetchAnticipatedShows(genres: String) = service.fetchAnticipatedShows(genres).map { it.show!! }

  suspend fun fetchAnticipatedMovies(genres: String) = service.fetchAnticipatedMovies(genres).map { it.movie!! }

  suspend fun fetchRelatedShows(traktId: Long) = service.fetchRelatedShows(traktId)

  suspend fun fetchRelatedMovies(traktId: Long) = service.fetchRelatedMovies(traktId)

  suspend fun fetchNextEpisode(traktId: Long): Episode? {
    val response = service.fetchNextEpisode(traktId)
    if (response.isSuccessful && response.code() == 204) return null
    return response.body()
  }

  suspend fun fetchSearch(query: String, withMovies: Boolean) =
    if (withMovies) service.fetchSearchResultsMovies(query)
    else service.fetchSearchResults(query)

  suspend fun fetchSeasons(traktId: Long) =
    service.fetchSeasons(traktId)
      .sortedByDescending { it.number }

  suspend fun fetchShowComments(traktId: Long, limit: Int) =
    service.fetchShowComments(traktId, limit, currentTimeMillis())

  suspend fun fetchMovieComments(traktId: Long, limit: Int) =
    service.fetchMovieComments(traktId, limit, currentTimeMillis())

  suspend fun fetchCommentReplies(commentId: Long) =
    service.fetchCommentReplies(commentId)

  suspend fun postComment(token: String, commentRequest: CommentRequest) =
    service.postComment("Bearer $token", commentRequest)

  suspend fun updateComment(token: String, commentId: Long, commentRequest: CommentRequest) =
    service.updateComment("Bearer $token", commentId, commentRequest)

  suspend fun fetchShowTranslations(traktId: Long, code: String) =
    service.fetchShowTranslations(traktId, code)

  suspend fun fetchMovieTranslations(traktId: Long, code: String) =
    service.fetchMovieTranslations(traktId, code)

  suspend fun fetchSeasonTranslations(showTraktId: Long, seasonNumber: Int, code: String) =
    service.fetchSeasonTranslations(showTraktId, seasonNumber, code)

  suspend fun fetchEpisodeComments(
    traktId: Long,
    seasonNumber: Int,
    episodeNumber: Int
  ): List<Comment> = try {
    service.fetchEpisodeComments(traktId, seasonNumber, episodeNumber, currentTimeMillis())
  } catch (t: Throwable) {
    emptyList()
  }

  suspend fun fetchShowActors(traktId: Long) =
    service.fetchShowPeople(traktId).cast ?: emptyList()

  suspend fun fetchMovieActors(traktId: Long) =
    service.fetchMoviePeople(traktId).cast ?: emptyList()

  suspend fun fetchAuthTokens(code: String): OAuthResponse {
    val request = OAuthRequest(
      code,
      TRAKT_CLIENT_ID,
      TRAKT_CLIENT_SECRET,
      TRAKT_REDIRECT_URL
    )
    return service.fetchOAuthToken(request)
  }

  suspend fun refreshAuthTokens(refreshToken: String): OAuthResponse {
    val request = OAuthRefreshRequest(
      refreshToken,
      TRAKT_CLIENT_ID,
      TRAKT_CLIENT_SECRET,
      TRAKT_REDIRECT_URL
    )
    return service.refreshOAuthToken(request)
  }

  suspend fun revokeAuthTokens(token: String) {
    val request = OAuthRevokeRequest(
      token,
      TRAKT_CLIENT_ID,
      TRAKT_CLIENT_SECRET
    )
    service.revokeOAuthToken(request)
  }

  suspend fun fetchMyProfile(token: String) =
    service.fetchMyProfile("Bearer $token")

  suspend fun fetchHiddenShows(token: String) =
    service.fetchHiddenShows("Bearer $token", pageLimit = 100)

  suspend fun fetchHiddenMovies(token: String) =
    service.fetchHiddenMovies("Bearer $token", pageLimit = 100)

  suspend fun fetchSyncWatchedShows(token: String, extended: String? = null) =
    service.fetchSyncWatched("Bearer $token", "shows", extended)

  suspend fun fetchSyncWatchedMovies(token: String, extended: String? = null) =
    service.fetchSyncWatched("Bearer $token", "movies", extended)

  suspend fun fetchSyncShowsWatchlist(token: String) = fetchSyncWatchlist(token, "shows")

  suspend fun fetchSyncMoviesWatchlist(token: String) = fetchSyncWatchlist(token, "movies")

  private suspend fun fetchSyncWatchlist(token: String, type: String): List<SyncItem> {
    var page = 1
    val results = mutableListOf<SyncItem>()

    do {
      val items = service.fetchSyncWatchlist("Bearer $token", type, page, TRAKT_SYNC_PAGE_LIMIT)
      results.addAll(items)
      page += 1
    } while (items.size >= TRAKT_SYNC_PAGE_LIMIT)

    return results
  }

  suspend fun postSyncWatchlist(token: String, request: SyncExportRequest) =
    service.postSyncWatchlist("Bearer $token", request)

  suspend fun postSyncWatched(token: String, request: SyncExportRequest) =
    service.postSyncWatched("Bearer $token", request)

  suspend fun postDeleteProgress(token: String, request: SyncExportRequest) =
    service.deleteHistory("Bearer $token", request)

  suspend fun postDeleteWatchlist(token: String, request: SyncExportRequest) =
    service.deleteWatchlist("Bearer $token", request)

  suspend fun deleteRating(token: String, show: Show) {
    val requestValue = RatingRequestValue(0, show.ids)
    val body = RatingRequest(shows = listOf(requestValue))
    service.postRemoveRating("Bearer $token", body)
  }

  suspend fun deleteRating(token: String, movie: Movie) {
    val requestValue = RatingRequestValue(0, movie.ids)
    val body = RatingRequest(movies = listOf(requestValue))
    service.postRemoveRating("Bearer $token", body)
  }

  suspend fun deleteRating(token: String, episode: Episode) {
    val requestValue = RatingRequestValue(0, episode.ids)
    val body = RatingRequest(episodes = listOf(requestValue))
    service.postRemoveRating("Bearer $token", body)
  }

  suspend fun postRating(token: String, movie: Movie, rating: Int) {
    val requestValue = RatingRequestValue(rating, movie.ids)
    val body = RatingRequest(movies = listOf(requestValue))
    service.postRating("Bearer $token", body)
  }

  suspend fun postRating(token: String, show: Show, rating: Int) {
    val requestValue = RatingRequestValue(rating, show.ids)
    val body = RatingRequest(shows = listOf(requestValue))
    service.postRating("Bearer $token", body)
  }

  suspend fun postRating(token: String, episode: Episode, rating: Int) {
    val requestValue = RatingRequestValue(rating, episode.ids)
    val body = RatingRequest(episodes = listOf(requestValue))
    service.postRating("Bearer $token", body)
  }

  suspend fun fetchShowsRatings(token: String) =
    service.fetchShowsRatings("Bearer $token")

  suspend fun fetchMoviesRatings(token: String) =
    service.fetchMoviesRatings("Bearer $token")

  suspend fun fetchEpisodesRatings(token: String) =
    service.fetchEpisodesRatings("Bearer $token")
}
