package com.michaldrabik.ui_widgets.progress_movies

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RemoteViews
import com.michaldrabik.common.Config
import com.michaldrabik.ui_base.utilities.extensions.dimenToPx
import com.michaldrabik.ui_model.IdTrakt
import com.michaldrabik.ui_widgets.BaseWidgetProvider
import com.michaldrabik.ui_widgets.R
import com.michaldrabik.ui_widgets.WidgetSettings
import timber.log.Timber

class ProgressMoviesWidgetProvider : BaseWidgetProvider() {

  companion object {
    const val EXTRA_CHECK_MOVIE_ID = "EXTRA_CHECK_MOVIE_ID"

    fun requestUpdate(context: Context, widgetSettings: WidgetSettings) {
      val applicationContext = context.applicationContext
      val intent = Intent(applicationContext, ProgressMoviesWidgetProvider::class.java).apply {
        val ids: IntArray = AppWidgetManager.getInstance(applicationContext)
          .getAppWidgetIds(ComponentName(applicationContext, ProgressMoviesWidgetProvider::class.java))
        action = ACTION_APPWIDGET_UPDATE
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        putExtra(EXTRA_SETTINGS, widgetSettings)
      }
      applicationContext.sendBroadcast(intent)
      Timber.d("Widget update requested.")
    }
  }

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray?
  ) {
    appWidgetIds?.forEach { updateWidget(context, appWidgetManager, it) }
    super.onUpdate(context, appWidgetManager, appWidgetIds)
  }

  private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
    val intent = Intent(context, ProgressMoviesWidgetService::class.java).apply {
      putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
      data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
    }

    val remoteViews = RemoteViews(context.packageName, R.layout.widget_movies_progress).apply {
      setRemoteAdapter(R.id.progressWidgetMoviesList, intent)
      setEmptyView(R.id.progressWidgetMoviesList, R.id.progressWidgetMoviesEmptyView)

      val spaceTiny = context.dimenToPx(R.dimen.spaceTiny)
      val paddingTop = if (settings?.showLabel == false) spaceTiny else context.dimenToPx(R.dimen.widgetPaddingTop)
      val labelVisibility = if (settings?.showLabel == false) GONE else VISIBLE
      setViewPadding(R.id.progressWidgetMoviesList, 0, paddingTop, 0, spaceTiny)
      setViewVisibility(R.id.progressWidgetMoviesLabel, labelVisibility)
    }

    val listClickIntent = Intent(context, ProgressMoviesWidgetProvider::class.java).apply {
      action = ACTION_LIST_CLICK
      data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
    }

    val listIntent = PendingIntent.getBroadcast(context, 0, listClickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    remoteViews.setPendingIntentTemplate(R.id.progressWidgetMoviesList, listIntent)

    appWidgetManager.updateAppWidget(widgetId, remoteViews)
    appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.progressWidgetMoviesList)
  }

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == ACTION_LIST_CLICK) {
      when {
        intent.extras?.containsKey(EXTRA_MOVIE_ID) == true -> {
          val movieId = intent.getLongExtra(EXTRA_MOVIE_ID, -1L)
          context.startActivity(
            Intent().apply {
              setClassName(context, Config.HOST_ACTIVITY_NAME)
              putExtra(EXTRA_MOVIE_ID, movieId.toString())
              flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
          )
        }
        intent.extras?.containsKey(EXTRA_CHECK_MOVIE_ID) == true -> {
          val movieId = intent.getLongExtra(EXTRA_CHECK_MOVIE_ID, -1L)
          ProgressMoviesWidgetCheckService.initialize(
            context.applicationContext,
            IdTrakt(movieId)
          )
        }
      }
    }
    super.onReceive(context, intent)
  }
}
