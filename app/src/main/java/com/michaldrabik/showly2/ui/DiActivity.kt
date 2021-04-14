package com.michaldrabik.showly2.ui

import com.michaldrabik.showly2.appComponent
import com.michaldrabik.ui_comments.post.di.UiPostCommentComponent
import com.michaldrabik.ui_comments.post.di.UiPostCommentComponentProvider
import com.michaldrabik.ui_discover.di.UiDiscoverComponent
import com.michaldrabik.ui_discover.di.UiDiscoverComponentProvider
import com.michaldrabik.ui_discover_movies.di.UiDiscoverMoviesComponent
import com.michaldrabik.ui_discover_movies.di.UiDiscoverMoviesComponentProvider
import com.michaldrabik.ui_episodes.details.di.UiEpisodeDetailsComponent
import com.michaldrabik.ui_episodes.details.di.UiEpisodeDetailsComponentProvider
import com.michaldrabik.ui_gallery.custom.di.UiCustomImagesComponent
import com.michaldrabik.ui_gallery.custom.di.UiCustomImagesComponentProvider
import com.michaldrabik.ui_gallery.fanart.di.UiArtGalleryComponentProvider
import com.michaldrabik.ui_gallery.fanart.di.UiFanartGalleryComponent
import com.michaldrabik.ui_lists.create.di.UiCreateListComponent
import com.michaldrabik.ui_lists.create.di.UiCreateListComponentProvider
import com.michaldrabik.ui_lists.details.di.UiListDetailsComponent
import com.michaldrabik.ui_lists.details.di.UiListDetailsComponentProvider
import com.michaldrabik.ui_lists.lists.di.UiListsComponent
import com.michaldrabik.ui_lists.lists.di.UiListsComponentProvider
import com.michaldrabik.ui_lists.manage.di.UiManageListsComponent
import com.michaldrabik.ui_lists.manage.di.UiManageListsComponentProvider
import com.michaldrabik.ui_movie.di.UiMovieDetailsComponent
import com.michaldrabik.ui_movie.di.UiMovieDetailsComponentProvider
import com.michaldrabik.ui_my_movies.di.UiMyMoviesComponent
import com.michaldrabik.ui_my_movies.di.UiMyMoviesComponentProvider
import com.michaldrabik.ui_my_shows.di.UiMyShowsComponent
import com.michaldrabik.ui_my_shows.di.UiMyShowsComponentProvider
import com.michaldrabik.ui_news.di.UiNewsComponent
import com.michaldrabik.ui_news.di.UiNewsComponentProvider
import com.michaldrabik.ui_premium.di.UiPremiumComponent
import com.michaldrabik.ui_premium.di.UiPremiumComponentProvider
import com.michaldrabik.ui_progress.di.UiProgressComponent
import com.michaldrabik.ui_progress.di.UiProgressComponentProvider
import com.michaldrabik.ui_progress_movies.di.UiProgressMoviesComponent
import com.michaldrabik.ui_progress_movies.di.UiProgressMoviesComponentProvider
import com.michaldrabik.ui_search.di.UiSearchComponentProvider
import com.michaldrabik.ui_settings.di.UiSettingsComponent
import com.michaldrabik.ui_settings.di.UiSettingsComponentProvider
import com.michaldrabik.ui_show.di.UiShowDetailsComponent
import com.michaldrabik.ui_show.di.UiShowDetailsComponentProvider
import com.michaldrabik.ui_statistics.di.UiSearchComponent
import com.michaldrabik.ui_statistics.di.UiStatisticsComponent
import com.michaldrabik.ui_statistics.di.UiStatisticsComponentProvider
import com.michaldrabik.ui_statistics_movies.di.UiStatisticsMoviesComponent
import com.michaldrabik.ui_statistics_movies.di.UiStatisticsMoviesComponentProvider
import com.michaldrabik.ui_trakt_sync.di.UiTraktSyncComponent
import com.michaldrabik.ui_trakt_sync.di.UiTraktSyncComponentProvider

abstract class DiActivity :
  BaseActivity(),
  UiTraktSyncComponentProvider,
  UiStatisticsComponentProvider,
  UiStatisticsMoviesComponentProvider,
  UiDiscoverComponentProvider,
  UiDiscoverMoviesComponentProvider,
  UiShowDetailsComponentProvider,
  UiMovieDetailsComponentProvider,
  UiArtGalleryComponentProvider,
  UiEpisodeDetailsComponentProvider,
  UiCustomImagesComponentProvider,
  UiMyShowsComponentProvider,
  UiMyMoviesComponentProvider,
  UiListsComponentProvider,
  UiListDetailsComponentProvider,
  UiCreateListComponentProvider,
  UiManageListsComponentProvider,
  UiProgressComponentProvider,
  UiProgressMoviesComponentProvider,
  UiSearchComponentProvider,
  UiSettingsComponentProvider,
  UiPostCommentComponentProvider,
  UiNewsComponentProvider,
  UiPremiumComponentProvider {

  private lateinit var uiDiscoverComponent: UiDiscoverComponent
  private lateinit var uiDiscoverMoviesComponent: UiDiscoverMoviesComponent
  private lateinit var uiEpisodeDetailsComponent: UiEpisodeDetailsComponent
  private lateinit var uiCustomImagesComponent: UiCustomImagesComponent
  private lateinit var uiMyShowsComponent: UiMyShowsComponent
  private lateinit var uiMyMoviesComponent: UiMyMoviesComponent
  private lateinit var uiListsComponent: UiListsComponent
  private lateinit var uiListDetailsComponent: UiListDetailsComponent
  private lateinit var uiCreateListComponent: UiCreateListComponent
  private lateinit var uiManageListComponent: UiManageListsComponent
  private lateinit var uiSearchComponent: UiSearchComponent
  private lateinit var uiSettingsComponent: UiSettingsComponent
  private lateinit var uiShowDetailsComponent: UiShowDetailsComponent
  private lateinit var uiMovieDetailsComponent: UiMovieDetailsComponent
  private lateinit var uiShowGalleryComponent: UiFanartGalleryComponent
  private lateinit var uiStatisticsComponent: UiStatisticsComponent
  private lateinit var uiStatisticsMoviesComponent: UiStatisticsMoviesComponent
  private lateinit var uiTraktSyncComponent: UiTraktSyncComponent
  private lateinit var uiProgressComponent: UiProgressComponent
  private lateinit var uiProgressMoviesComponent: UiProgressMoviesComponent
  private lateinit var uiPremiumComponent: UiPremiumComponent
  private lateinit var uiPostCommentComponent: UiPostCommentComponent
  private lateinit var uiNewsComponent: UiNewsComponent

  override fun provideDiscoverComponent() = uiDiscoverComponent
  override fun provideDiscoverMoviesComponent() = uiDiscoverMoviesComponent
  override fun provideEpisodeDetailsComponent() = uiEpisodeDetailsComponent
  override fun provideCustomImagesComponent() = uiCustomImagesComponent
  override fun provideArtGalleryComponent() = uiShowGalleryComponent
  override fun provideMyShowsComponent() = uiMyShowsComponent
  override fun provideMyMoviesComponent() = uiMyMoviesComponent
  override fun provideSearchComponent() = uiSearchComponent
  override fun provideSettingsComponent() = uiSettingsComponent
  override fun provideShowDetailsComponent() = uiShowDetailsComponent
  override fun provideMovieDetailsComponent() = uiMovieDetailsComponent
  override fun provideStatisticsComponent() = uiStatisticsComponent
  override fun provideStatisticsMoviesComponent() = uiStatisticsMoviesComponent
  override fun provideTraktSyncComponent() = uiTraktSyncComponent
  override fun provideProgressComponent() = uiProgressComponent
  override fun provideProgressMoviesComponent() = uiProgressMoviesComponent
  override fun providePremiumComponent() = uiPremiumComponent
  override fun providePostCommentComponent() = uiPostCommentComponent
  override fun provideListsComponent() = uiListsComponent
  override fun provideListDetailsComponent() = uiListDetailsComponent
  override fun provideCreateListComponent() = uiCreateListComponent
  override fun provideManageListsComponent() = uiManageListComponent
  override fun provideNewsComponent() = uiNewsComponent

  protected open fun setupComponents() {
    uiDiscoverComponent = appComponent().uiDiscoverComponent().create()
    uiDiscoverMoviesComponent = appComponent().uiDiscoverMoviesComponent().create()
    uiEpisodeDetailsComponent = appComponent().uiEpisodeDetailsComponent().create()
    uiCustomImagesComponent = appComponent().uiCustomImagesComponent().create()
    uiMyShowsComponent = appComponent().uiMyShowsComponent().create()
    uiMyMoviesComponent = appComponent().uiMyMoviesComponent().create()
    uiListsComponent = appComponent().uiListsComponent().create()
    uiListDetailsComponent = appComponent().uiListDetailsComponent().create()
    uiCreateListComponent = appComponent().uiCreateListComponent().create()
    uiManageListComponent = appComponent().uiManageListsComponent().create()
    uiSearchComponent = appComponent().uiSearchComponent().create()
    uiSettingsComponent = appComponent().uiSettingsComponent().create()
    uiShowDetailsComponent = appComponent().uiShowDetailsComponent().create()
    uiMovieDetailsComponent = appComponent().uiMovieDetailsComponent().create()
    uiShowGalleryComponent = appComponent().uiShowGalleryComponent().create()
    uiStatisticsComponent = appComponent().uiStatisticsComponent().create()
    uiStatisticsMoviesComponent = appComponent().uiStatisticsMoviesComponent().create()
    uiTraktSyncComponent = appComponent().uiTraktSyncComponent().create()
    uiProgressComponent = appComponent().uiProgressComponent().create()
    uiProgressMoviesComponent = appComponent().uiProgressMoviesComponent().create()
    uiPremiumComponent = appComponent().uiPremiumMoviesComponent().create()
    uiPostCommentComponent = appComponent().uiPostCommentComponent().create()
    uiNewsComponent = appComponent().uiNewsComponent().create()
  }
}
