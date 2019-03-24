package com.twitter.data.repository.feed

import com.twitter.data.db.entity.TweetEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface TweetsRepository {

  fun observeTweets(): Flowable<List<TweetEntity>>

  fun fetchTweets(accessToken: String): Completable

  fun createTweet(accessToken: String, message: String): Completable

  fun clearUserTweets(): Completable
}
