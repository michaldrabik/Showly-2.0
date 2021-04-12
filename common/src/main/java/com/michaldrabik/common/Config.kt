package com.michaldrabik.common

import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS

object Config {
  const val TVDB_IMAGE_BASE_BANNERS_URL = "https://artworks.thetvdb.com/banners/"
  const val TVDB_IMAGE_BASE_POSTER_URL = "${TVDB_IMAGE_BASE_BANNERS_URL}posters/"
  const val TVDB_IMAGE_BASE_FANART_URL = "${TVDB_IMAGE_BASE_BANNERS_URL}fanart/original/"

  private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
  const val TMDB_IMAGE_BASE_POSTER_URL = "${TMDB_IMAGE_BASE_URL}w342"
  const val TMDB_IMAGE_BASE_FANART_URL = "${TMDB_IMAGE_BASE_URL}w1280"
  const val TMDB_IMAGE_BASE_STILL_URL = "${TMDB_IMAGE_BASE_URL}original"
  const val TMDB_IMAGE_BASE_ACTOR_URL = "${TMDB_IMAGE_BASE_URL}h632"
  const val TMDB_IMAGE_BASE_ACTOR_FULL_URL = "${TMDB_IMAGE_BASE_URL}original"

  const val AWS_IMAGE_BASE_URL = "https://showly2.s3.eu-west-2.amazonaws.com/images/"

  const val DEVELOPER_MAIL = "showlyapp@gmail.com"
  const val PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.michaldrabik.showly2"
  const val TWITTER_URL = "https://twitter.com/AppShowly/"
  const val TRAKT_URL = "https://trakt.tv/"
  const val TMDB_URL = "https://www.themoviedb.org/"

  const val MAIN_GRID_SPAN = 3
  const val IMAGE_FADE_DURATION_MS = 200
  const val SEARCH_RECENTS_AMOUNT = 5
  const val FANART_GALLERY_IMAGES_LIMIT = 20
  const val PULL_TO_REFRESH_COOLDOWN_MS = 10_000
  const val INITIAL_RATING = 0
  const val DEFAULT_LANGUAGE = "en"
  const val DEFAULT_COUNTRY = "us"
  const val DEFAULT_DATE_FORMAT = "DEFAULT_24"
  const val HOST_ACTIVITY_NAME = "com.michaldrabik.showly2.ui.main.MainActivity"

  const val SHOW_TIPS_DEBUG = false
  const val SHOW_PREMIUM = true
  const val PROMOS_ENABLED = false

  val MY_SHOWS_RECENTS_OPTIONS = arrayOf(2, 4, 6, 8)
  val DISCOVER_SHOWS_CACHE_DURATION by lazy { HOURS.toMillis(12) }
  val DISCOVER_MOVIES_CACHE_DURATION by lazy { HOURS.toMillis(12) }
  val RELATED_CACHE_DURATION by lazy { DAYS.toMillis(7) }
  val SHOW_DETAILS_CACHE_DURATION by lazy { DAYS.toMillis(3) }
  val MOVIE_DETAILS_CACHE_DURATION by lazy { DAYS.toMillis(3) }
  val ACTORS_CACHE_DURATION by lazy { DAYS.toMillis(3) }
  val NEW_BADGE_DURATION by lazy { HOURS.toMillis(30) }

  const val SHOW_WHATS_NEW = true
  const val WHATS_NEW_TEXT =
    "* Added extended comments support with replies and possibility to write your own comments.\n\n" +
      "* Added option to manually unlock and mark episodes that have not been aired yet.\n\n" +
      "* Minor UI tweaks and optimizations.\n\n" +
      "As usual in case of any issues or translations mistakes don\'t hesitate to drop us an email or a tweet!"
}
