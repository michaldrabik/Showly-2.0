package com.michaldrabik.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.michaldrabik.storage.database.dao.ActorsDao
import com.michaldrabik.storage.database.dao.DiscoverShowsDao
import com.michaldrabik.storage.database.dao.EpisodesDao
import com.michaldrabik.storage.database.dao.FollowedShowsDao
import com.michaldrabik.storage.database.dao.ImagesDao
import com.michaldrabik.storage.database.dao.RecentSearchDao
import com.michaldrabik.storage.database.dao.SeasonsDao
import com.michaldrabik.storage.database.dao.ShowsDao
import com.michaldrabik.storage.database.dao.UserDao
import com.michaldrabik.storage.database.model.Actor
import com.michaldrabik.storage.database.model.DiscoverShow
import com.michaldrabik.storage.database.model.Episode
import com.michaldrabik.storage.database.model.FollowedShow
import com.michaldrabik.storage.database.model.Image
import com.michaldrabik.storage.database.model.RecentSearch
import com.michaldrabik.storage.database.model.Season
import com.michaldrabik.storage.database.model.Show
import com.michaldrabik.storage.database.model.User

private const val DATABASE_VERSION = 1

@Database(
  version = DATABASE_VERSION,
  entities = [
    Show::class,
    DiscoverShow::class,
    FollowedShow::class,
    Image::class,
    User::class,
    Season::class,
    Actor::class,
    Episode::class,
    RecentSearch::class]
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun showsDao(): ShowsDao

  abstract fun discoverShowsDao(): DiscoverShowsDao

  abstract fun followedShowsDao(): FollowedShowsDao

  abstract fun imagesDao(): ImagesDao

  abstract fun userDao(): UserDao

  abstract fun recentSearchDao(): RecentSearchDao

  abstract fun episodesDao(): EpisodesDao

  abstract fun seasonsDao(): SeasonsDao

  abstract fun actorsDao(): ActorsDao
}