package com.twitter.feature.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.twitter.R
import com.twitter.core.navigation.NavigationTypes
import com.twitter.core.util.StringUtils
import com.twitter.domain.login.LoginUseCase
import com.twitter.domain.login.User
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.RxTestRule

class LoginViewModelTest {
  @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
  @get:Rule val rxTestRule = RxTestRule()

  private val loginUseCase: LoginUseCase = mock()
  private val stringUtils: StringUtils = mock()
  private lateinit var sut: LoginViewModel

  private companion object {
    const val EMAIL = "email"
    const val PASS = "pass"
  }

  @Before
  fun init() {
    sut = LoginViewModel(loginUseCase, stringUtils)
    sut.email.value = EMAIL
    sut.password.value = PASS
  }

  @Test
  fun `user should be navigated HOME is loginUseCase result is Success`() {
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.Success))

    sut.onLoginClicked()
    assertThat(sut.navigationLiveData.value, equalTo(NavigationTypes.HOME))
  }

  @Test
  fun `progress should be shown if loginUseCase returns Loading`() {
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.Loading))

    sut.onLoginClicked()
    assertThat(sut.showProgressLiveData.value, equalTo(true))
  }

  @Test
  fun `progress should be hidden if loginUseCase returns NOT Loading`() {
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.Success))

    sut.onLoginClicked()
    assertThat(sut.showProgressLiveData.value, equalTo(false))
  }

  @Test
  fun `empty email error should be shown if loginUseCase returns EmptyEmail`() {
    val error = "error message"
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.EmptyEmail))
    given { stringUtils.getString(R.string.login_error_empty_email) }.willReturn(error)

    sut.onLoginClicked()
    assertThat(sut.errorMessage.value, equalTo(error))
  }

  @Test
  fun `empty password error should be shown if loginUseCase returns EmptyPassword`() {
    val error = "error message"
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.EmptyPassword))
    given { stringUtils.getString(R.string.login_error_empty_password) }.willReturn(error)

    sut.onLoginClicked()
    assertThat(sut.errorMessage.value, equalTo(error))
  }

  @Test
  fun `empty email and password error should be shown if loginUseCase returns EmptyEmailPassword`() {
    val error = "error message"
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.EmptyEmailPassword))
    given { stringUtils.getString(R.string.login_error_empty_email_and_password) }.willReturn(error)

    sut.onLoginClicked()
    assertThat(sut.errorMessage.value, equalTo(error))
  }

  @Test
  fun `invalid credentials error should be shown if loginUseCase returns InvalidCredentials`() {
    val error = "error message"
    given { loginUseCase(User(EMAIL, PASS)) }.willReturn(Observable.just(LoginUseCase.UseCaseResult.InvalidCredentials))
    given { stringUtils.getString(R.string.login_error_invalid_credentials) }.willReturn(error)

    sut.onLoginClicked()
    assertThat(sut.errorMessage.value, equalTo(error))
  }
}
