package com.twitter.domain.home

import com.twitter.data.repository.feed.TweetsRepository
import com.twitter.data.repository.user.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class CreateTweetUseCase @Inject constructor(
    private val tweetsRepository: TweetsRepository,
    private val userRepository: UserRepository
) {

  sealed class UseCaseResult {
    object Loading : UseCaseResult()
    object Success : UseCaseResult()
    data class Error(val error: Throwable) : UseCaseResult()
    object EmptyMessage : UseCaseResult()
  }

  operator fun invoke(message: String): Observable<UseCaseResult> =
      if (message.isEmpty()) {
        Observable.just<UseCaseResult>(UseCaseResult.EmptyMessage)
      } else {
        userRepository.getUserToken()
            .flatMapCompletable { tweetsRepository.createTweet(it, message) }
            .andThen(Observable.just<UseCaseResult>(UseCaseResult.Success))
            .onErrorReturn(UseCaseResult::Error)
      }
          .startWith(UseCaseResult.Loading)
}
