package com.twitter.data.network

import com.twitter.data.network.response.TweetsResponse
import com.twitter.data.network.response.UserResponse
import com.twitter.domain.login.User
import io.reactivex.Single

interface Api {

  fun loginUser(user: User): Single<UserResponse>

  fun fetchTweets(accessToken: String): Single<TweetsResponse>

  fun createTweet(accessToken: String, message: String): Single<TweetsResponse>
}
