package com.twitter.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.twitter.data.db.entity.TweetEntity
import io.reactivex.Flowable

@Dao
interface TweetDao {

  @Query("SELECT * FROM tweets ORDER BY id DESC")
  fun observeTweets(): Flowable<List<TweetEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertTweets(tweets: List<TweetEntity>)

  @Query("DELETE FROM tweets")
  fun clear()
}
