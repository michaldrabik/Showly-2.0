package com.michaldrabik.ui_my_shows.watchlist.cases

import com.michaldrabik.common.Config
import com.michaldrabik.common.di.AppScope
import com.michaldrabik.ui_model.Show
import com.michaldrabik.ui_model.SortOrder.DATE_ADDED
import com.michaldrabik.ui_model.SortOrder.NAME
import com.michaldrabik.ui_model.SortOrder.NEWEST
import com.michaldrabik.ui_model.SortOrder.RATING
import com.michaldrabik.ui_model.Translation
import com.michaldrabik.ui_repository.SettingsRepository
import com.michaldrabik.ui_repository.TranslationsRepository
import com.michaldrabik.ui_repository.shows.ShowsRepository
import javax.inject.Inject

@AppScope
class WatchlistLoadShowsCase @Inject constructor(
  private val showsRepository: ShowsRepository,
  private val translationsRepository: TranslationsRepository,
  private val settingsRepository: SettingsRepository
) {

  suspend fun loadShows(): List<Pair<Show, Translation?>> {
    val language = settingsRepository.getLanguage()
    val translations =
      if (language == Config.DEFAULT_LANGUAGE) emptyMap()
      else translationsRepository.loadAllShowsLocal(language)

    val sortType = settingsRepository.load().watchlistShowsSortBy
    val shows = showsRepository.watchlistShows.loadAll()
      .map { it to translations[it.traktId] }

    return when (sortType) {
      NAME -> shows.sortedBy {
        val translatedTitle = if (it.second?.hasTitle == false) null else it.second?.title
        translatedTitle ?: it.first.title
      }
      DATE_ADDED -> shows.sortedByDescending { it.first.updatedAt }
      RATING -> shows.sortedByDescending { it.first.rating }
      NEWEST -> shows.sortedByDescending { it.first.year }
      else -> error("Should not be used here.")
    }
  }
}