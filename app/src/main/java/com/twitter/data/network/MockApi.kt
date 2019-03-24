package com.twitter.data.network

import com.twitter.core.exception.ErrorTypes.INVALID_CREDENTIALS
import com.twitter.data.network.response.Tweet
import com.twitter.data.network.response.TweetsResponse
import com.twitter.data.network.response.UserResponse
import com.twitter.domain.login.User
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MockApi @Inject constructor() : Api {

  companion object {
    const val REQUEST_TIME_MILLIS = 500L
    val VALID_USER = User("adm@mail.com", "123")
  }

  override fun loginUser(user: User): Single<UserResponse> =
      Single.fromCallable {
        if (user == VALID_USER) {
          UserResponse("accessToken", 12313)
        } else {
          throw HttpException(
              Response.error<Nothing>(
                  HttpURLConnection.HTTP_BAD_REQUEST,
                  ResponseBody.create(MediaType.parse("application/json"), getInvalidCredentialsResponse())
              )
          )
        }
      }.delay(REQUEST_TIME_MILLIS, TimeUnit.MILLISECONDS)

  private fun getInvalidCredentialsResponse(): String =
      "{\n" +
          "  \"message\": \"invalid credentials\",\n" +
          "  \"errorType\": \"$INVALID_CREDENTIALS\"\n" +
          "}"

  override fun fetchTweets(accessToken: String): Single<TweetsResponse> =
      Single.just(TweetsResponse(createTweets()))
          .delay(REQUEST_TIME_MILLIS, TimeUnit.MILLISECONDS)

  override fun createTweet(accessToken: String, message: String): Single<TweetsResponse> =
      Single.just(
          TweetsResponse(
              listOf(
                  Tweet(
                      System.nanoTime().toString(),
                      "Me",
                      "https://cdn2.iconfinder.com/data/icons/scenarium-vol-4/128/019_avatar_woman_girl_female_account_profile_user-512.png",
                      message,
                      Date(System.nanoTime()).toString()
                  )
              )
          )
      ).delay(REQUEST_TIME_MILLIS, TimeUnit.MILLISECONDS)

  private fun createTweets() =
      mutableListOf<Tweet>().apply {
        for (i in 1..5) {
          add(
              Tweet(
                  System.nanoTime().toString(),
                  "user$i",
                  "https://cdn4.iconfinder.com/data/icons/eldorado-user/40/user-512.png",
                  "Sharing some news.\nFollow my on Tweeter.",
                  "Nov 21 2007"
              )
          )
        }
      }
}
