package com.twitter.domain.login

import com.twitter.data.repository.feed.TweetsRepository
import com.twitter.data.repository.user.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tweetsRepository: TweetsRepository
) {

  operator fun invoke(): Completable =
      Completable.mergeArray(
          userRepository.clearUserToken(),
          tweetsRepository.clearUserTweets()
      )
}
