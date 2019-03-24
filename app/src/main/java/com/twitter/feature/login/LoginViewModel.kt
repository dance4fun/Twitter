package com.twitter.feature.login

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import com.twitter.R
import com.twitter.core.base.BaseViewModel
import com.twitter.core.navigation.NavigationTypes
import com.twitter.core.util.StringUtils
import com.twitter.domain.login.LoginUseCase
import com.twitter.domain.login.User
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val stringUtils: StringUtils
) : BaseViewModel() {

  val email = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  val errorMessage = MutableLiveData<String>()

  private val user: User
    get() = User(email.value.orEmpty(), password.value.orEmpty())

  fun onLoginClicked() {
    loginUseCase(user)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { hideKeyboard() }
        .subscribe({ result ->
          when (result) {
            LoginUseCase.UseCaseResult.Success -> navigationLiveData.postValue(NavigationTypes.HOME)
            LoginUseCase.UseCaseResult.EmptyEmail -> setErrorMessage(R.string.login_error_empty_email)
            LoginUseCase.UseCaseResult.EmptyPassword -> setErrorMessage(R.string.login_error_empty_password)
            LoginUseCase.UseCaseResult.EmptyEmailPassword -> {
              setErrorMessage(R.string.login_error_empty_email_and_password)
            }
            LoginUseCase.UseCaseResult.InvalidCredentials -> setErrorMessage(R.string.login_error_invalid_credentials)
            is LoginUseCase.UseCaseResult.Error -> handleError(result.error)
          }
          showProgressLiveData.postValue(result === LoginUseCase.UseCaseResult.Loading)
        },
            ::handleError)
        .run(disposables::add)
  }

  private fun setErrorMessage(@StringRes resId: Int) {
    errorMessage.postValue(stringUtils.getString(resId))
  }
}
