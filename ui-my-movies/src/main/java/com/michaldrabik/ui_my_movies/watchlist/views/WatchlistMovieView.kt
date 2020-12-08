package com.michaldrabik.ui_my_movies.watchlist.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.michaldrabik.common.extensions.toDayDisplayString
import com.michaldrabik.ui_base.common.views.MovieView
import com.michaldrabik.ui_base.utilities.extensions.capitalizeWords
import com.michaldrabik.ui_base.utilities.extensions.gone
import com.michaldrabik.ui_base.utilities.extensions.onClick
import com.michaldrabik.ui_base.utilities.extensions.visibleIf
import com.michaldrabik.ui_my_movies.R
import com.michaldrabik.ui_my_movies.watchlist.recycler.WatchlistListItem
import kotlinx.android.synthetic.main.view_watchlist_movie.view.*
import java.util.Locale.ENGLISH

@SuppressLint("SetTextI18n")
class WatchlistMovieView : MovieView<WatchlistListItem> {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    inflate(context, R.layout.view_watchlist_movie, this)
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    watchlistMoviesRoot.onClick { itemClickListener?.invoke(item) }
  }

  override val imageView: ImageView = watchlistMoviesImage
  override val placeholderView: ImageView = watchlistMoviesPlaceholder

  private lateinit var item: WatchlistListItem

  override fun bind(
    item: WatchlistListItem,
    missingImageListener: ((WatchlistListItem, Boolean) -> Unit)?
  ) {
    clear()
    this.item = item
    watchlistMoviesProgress.visibleIf(item.isLoading)
    watchlistMoviesTitle.text =
      if (item.translation?.title.isNullOrBlank()) item.movie.title
      else item.translation?.title?.capitalizeWords()

    watchlistMoviesDescription.text =
      when {
        item.translation?.overview.isNullOrBlank() -> {
          if (item.movie.overview.isNotBlank()) item.movie.overview
          else context.getString(R.string.textNoDescription)
        }
        else -> item.translation?.overview
      }

    watchlistMoviesYear.text = item.movie.released?.toDayDisplayString() ?: String.format(ENGLISH, "%d", item.movie.year)
    watchlistMoviesRating.text = String.format(ENGLISH, "%.1f", item.movie.rating)
    watchlistMoviesYear.visibleIf(item.movie.year > 0)

    loadImage(item, missingImageListener)
  }

  private fun clear() {
    watchlistMoviesTitle.text = ""
    watchlistMoviesDescription.text = ""
    watchlistMoviesYear.text = ""
    watchlistMoviesRating.text = ""
    watchlistMoviesPlaceholder.gone()
    Glide.with(this).clear(watchlistMoviesImage)
  }
}