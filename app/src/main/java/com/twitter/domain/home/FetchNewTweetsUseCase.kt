package com.twitter.domain.home

import com.twitter.data.repository.feed.TweetsRepository
import com.twitter.data.repository.user.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class FetchNewTweetsUseCase @Inject constructor(
    private val tweetsRepository: TweetsRepository,
    private val userRepository: UserRepository
) {

  sealed class UseCaseResult {
    object Loading : UseCaseResult()
    object Success : UseCaseResult()
    data class Error(val error: Throwable) : UseCaseResult()
  }

  operator fun invoke(): Observable<UseCaseResult> =
      userRepository.getUserToken()
          .flatMapCompletable(tweetsRepository::fetchTweets)
          .andThen(Observable.just<UseCaseResult>(UseCaseResult.Success))
          .onErrorReturn(UseCaseResult::Error)
          .startWith(UseCaseResult.Loading)
}
