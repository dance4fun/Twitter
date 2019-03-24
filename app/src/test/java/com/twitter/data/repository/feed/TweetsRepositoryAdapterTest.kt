package com.twitter.data.repository.feed

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.twitter.core.exception.AppException
import com.twitter.core.util.HttpErrorHandler
import com.twitter.data.db.AppDatabase
import com.twitter.data.db.dao.TweetDao
import com.twitter.data.db.entity.TweetEntity
import com.twitter.data.network.Api
import com.twitter.data.network.response.Tweet
import com.twitter.data.network.response.TweetsResponse
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class TweetsRepositoryAdapterTest {
  private val database: AppDatabase = mock()
  private val tweetDao: TweetDao = mock()
  private val api: Api = mock()
  private val errorHandler: HttpErrorHandler = mock()

  private val sut = TweetsRepositoryAdapter(database, api, errorHandler)

  @Before
  fun init() {
    given { database.tweetDao }.willReturn(tweetDao)
  }

  @Test
  fun `observeTweets should return empty list if tweetDao returns empty list`() {
    given { tweetDao.observeTweets() }.willReturn(Flowable.just(emptyList()))

    sut.observeTweets().test().assertValue(emptyList())
  }

  @Test
  fun `observeTweets should return list with data`() {
    val data = listOf(TweetEntity("id", "name", "icon", "text", "date"))
    given { tweetDao.observeTweets() }.willReturn(Flowable.just(data))

    sut.observeTweets().test().assertValue(data)
  }

  @Test
  fun `tweet should be created if api response is successful`() {
    val token = "1sq4"
    val message = "tweet message"
    val response = TweetsResponse(
        listOf(Tweet("id", "name", null, message, "date"))
    )
    val expectedDbEntity = TweetEntity("id", "name", "", message, "date")

    given { api.createTweet(token, message) }.willReturn(Single.just(response))

    sut.createTweet(token, message).test().assertComplete()
    verify(api).createTweet(token, message)
    verify(tweetDao).insertTweets(listOf(expectedDbEntity))
  }

  @Test
  fun `tweet should not be added if api response is error`() {
    val token = "1sq4"
    val message = "tweet message"
    val response = Response.error<Nothing>(
        HttpURLConnection.HTTP_BAD_REQUEST,
        ResponseBody.create(MediaType.parse("application/json"), "{ error response }")
    )
    val httpError = HttpException(response)
    val expectedError = AppException.ApiException("errorType")

    given { api.createTweet(token, message) }.willReturn(Single.error(httpError))
    given { errorHandler.handleError(httpError) }.willReturn(expectedError)

    sut.createTweet(token, message).test().assertError(expectedError)
    verify(api).createTweet(token, message)
    verify(tweetDao, never()).insertTweets(anyList())
  }

  @Test
  fun `clearUserTweets should call tweetDao#clear()`() {
    sut.clearUserTweets().test().assertComplete()
    verify(tweetDao).clear()
  }
}
