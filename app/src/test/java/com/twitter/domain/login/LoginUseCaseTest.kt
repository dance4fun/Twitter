package com.twitter.domain.login

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.twitter.core.exception.AppException.ApiException
import com.twitter.core.exception.ErrorTypes
import com.twitter.data.repository.user.UserRepository
import io.reactivex.Completable
import org.junit.Test

class LoginUseCaseTest {
  private val userRepository: UserRepository = mock()
  private val sut = LoginUseCase(userRepository)

  @Test
  fun `invoke should return Loading as first element`() {
    val user = User("", "")

    sut.invoke(user).test().assertValueAt(0, LoginUseCase.UseCaseResult.Loading)
  }

  @Test
  fun `invoke should return Success if api call is successful`() {
    val user = User("email", "pass")
    given { userRepository.login(user) }.willReturn(Completable.complete())

    sut.invoke(user).test().assertValueAt(1, LoginUseCase.UseCaseResult.Success)
  }

  @Test
  fun `invoke should return EmptyEmailPassword if password and email are empty`() {
    val user = User("", "")

    sut.invoke(user).test().assertValueAt(1, LoginUseCase.UseCaseResult.EmptyEmailPassword)
  }

  @Test
  fun `invoke should return EmptyEmail if email is empty`() {
    val user = User("", "123")

    sut.invoke(user).test().assertValueAt(1, LoginUseCase.UseCaseResult.EmptyEmail)
  }

  @Test
  fun `invoke should return EmptyPassword if password is empty`() {
    val user = User("email", "")

    sut.invoke(user).test().assertValueAt(1, LoginUseCase.UseCaseResult.EmptyPassword)
  }

  @Test
  fun `invoke should return InvalidCredentials if api returns INVALID_CREDENTIALS error`() {
    val user = User("email", "123")
    given { userRepository.login(user) }.willReturn(Completable.error(ApiException(ErrorTypes.INVALID_CREDENTIALS)))

    sut.invoke(user).test().assertValueAt(1, LoginUseCase.UseCaseResult.InvalidCredentials)
  }
}
