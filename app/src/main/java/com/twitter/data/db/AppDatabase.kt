package com.twitter.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.twitter.data.db.AppDatabase.Companion.DB_VERSION
import com.twitter.data.db.dao.TweetDao
import com.twitter.data.db.entity.TweetEntity

@Database(
    version = DB_VERSION,
    entities = [
      TweetEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

  companion object {
    const val DB_NAME = "twitter_database"
    const val DB_VERSION = 1
  }

  abstract val tweetDao: TweetDao

}
