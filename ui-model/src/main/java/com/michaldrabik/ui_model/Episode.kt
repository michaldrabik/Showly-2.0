package com.michaldrabik.ui_model

import android.os.Parcelable
import com.michaldrabik.common.extensions.nowUtcMillis
import com.michaldrabik.common.extensions.toMillis
import kotlinx.android.parcel.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Episode(
  val season: Int,
  val number: Int,
  val title: String,
  val ids: Ids,
  val overview: String,
  val rating: Float,
  val votes: Int,
  val commentCount: Int,
  val firstAired: ZonedDateTime?,
  val runtime: Int,
) : Parcelable {

  companion object {
    val EMPTY = Episode(-1, -1, "", Ids.EMPTY, "", -1F, -1, -1, null, -1)
  }

  fun hasAired(season: Season) =
    when (firstAired) {
      null -> season.episodes.any { it.number > number && it.firstAired != null }
      else -> nowUtcMillis() >= firstAired.toMillis()
    }
}
