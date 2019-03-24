package com.twitter.domain.login

import com.twitter.core.exception.AppException.ApiException
import com.twitter.core.exception.ErrorTypes
import com.twitter.data.repository.user.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

  sealed class UseCaseResult {
    object Loading : UseCaseResult()
    object Success : UseCaseResult()
    object EmptyEmail : UseCaseResult()
    object EmptyPassword : UseCaseResult()
    object EmptyEmailPassword : UseCaseResult()
    object InvalidCredentials : UseCaseResult()
    data class Error(val error: Throwable) : UseCaseResult()
  }

  operator fun invoke(user: User): Observable<UseCaseResult> =
      when {
        user.email.isEmpty() && user.password.isEmpty() -> {
          Observable.just<UseCaseResult>(UseCaseResult.EmptyEmailPassword)
        }
        user.email.isEmpty() -> Observable.just<UseCaseResult>(UseCaseResult.EmptyEmail)
        user.password.isEmpty() -> Observable.just<UseCaseResult>(UseCaseResult.EmptyPassword)
        else -> login(user)
      }.startWith(UseCaseResult.Loading)

  private fun login(user: User): Observable<UseCaseResult> =
      userRepository.login(user)
          .andThen(Observable.just<UseCaseResult>(UseCaseResult.Success))
          .onErrorReturn(::handleError)

  private fun handleError(throwable: Throwable): UseCaseResult =
      when {
        throwable is ApiException && throwable.errorType == ErrorTypes.INVALID_CREDENTIALS -> {
          UseCaseResult.InvalidCredentials
        }
        else -> UseCaseResult.Error(throwable)
      }
}
