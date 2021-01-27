package com.michaldrabik.ui_show.episodes

import com.michaldrabik.ui_model.Episode
import com.michaldrabik.ui_model.Season
import com.michaldrabik.ui_model.TraktRating
import com.michaldrabik.ui_model.Translation
import org.threeten.bp.format.DateTimeFormatter

data class EpisodeListItem(
  val episode: Episode,
  val season: Season,
  val isWatched: Boolean,
  val translation: Translation? = null,
  val myRating: TraktRating? = null,
  val dateFormat: DateTimeFormatter? = null
) {

  val id = episode.ids.trakt.id
}
