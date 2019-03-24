package com.twitter.feature.home

import com.twitter.domain.home.CreateTweetUseCase
import com.twitter.domain.home.FetchNewTweetsUseCase
import com.twitter.domain.home.GetTweetsUseCase
import com.twitter.domain.login.LogoutUseCase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val getTweetsUseCase: GetTweetsUseCase,
    private val fetchNewTweetsUseCase: FetchNewTweetsUseCase,
    private val createTweetUseCase: CreateTweetUseCase,
    private val logoutUseCase: LogoutUseCase
) {

  fun fetchTweets(): Observable<FetchNewTweetsUseCase.UseCaseResult> = fetchNewTweetsUseCase()

  fun observeFeed(): Flowable<List<Tweet>> = getTweetsUseCase()

  fun sendTweet(message: String): Observable<CreateTweetUseCase.UseCaseResult> = createTweetUseCase(message)

  fun logout(): Completable = logoutUseCase()
}
