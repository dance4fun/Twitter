package com.twitter.data.repository.feed

import com.twitter.core.util.HttpErrorHandler
import com.twitter.data.db.AppDatabase
import com.twitter.data.db.entity.TweetEntity
import com.twitter.data.network.Api
import com.twitter.data.network.response.Tweet
import com.twitter.data.network.response.TweetsResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TweetsRepositoryAdapter @Inject constructor(
    private val database: AppDatabase,
    private val api: Api,
    private val errorHandler: HttpErrorHandler
) : TweetsRepository {

  override fun observeTweets(): Flowable<List<TweetEntity>> =
      database.tweetDao.observeTweets()

  override fun fetchTweets(accessToken: String): Completable =
      api.fetchTweets(accessToken)
          .flatMapCompletable(::saveTweetsToDb)
          .onErrorResumeNext { Completable.error(errorHandler.handleError(it)) }

  override fun createTweet(accessToken: String, message: String): Completable =
      api.createTweet(accessToken, message)
          .flatMapCompletable(::saveTweetsToDb)
          .onErrorResumeNext { Completable.error(errorHandler.handleError(it)) }

  private fun saveTweetsToDb(response: TweetsResponse): Completable =
      Completable.fromAction {
        database.tweetDao.insertTweets(mapToDbEntity(response.tweets))
      }

  private fun mapToDbEntity(tweets: List<Tweet>): List<TweetEntity> =
      tweets.map {
        TweetEntity(
            id = it.tweetId,
            userName = it.userName,
            userIcon = it.iconUrl.orEmpty(),
            text = it.message,
            date = it.date
        )
      }

  override fun clearUserTweets(): Completable =
      Completable.fromAction {
        database.tweetDao.clear()
      }
}
