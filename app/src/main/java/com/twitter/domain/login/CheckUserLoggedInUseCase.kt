package com.twitter.domain.login

import com.twitter.data.repository.user.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

  operator fun invoke(): Single<Boolean> =
      userRepository.getUserToken()
          .map { true }
          .toSingle(false)
}
