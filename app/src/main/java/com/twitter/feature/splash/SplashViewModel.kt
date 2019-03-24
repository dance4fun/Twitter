package com.twitter.feature.splash

import com.twitter.core.base.BaseViewModel
import com.twitter.core.navigation.NavigationTypes
import com.twitter.domain.login.CheckUserLoggedInUseCase
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : BaseViewModel() {

  init {
    checkLoginState()
  }

  private fun checkLoginState() {
    checkUserLoggedInUseCase()
        .subscribeOn(Schedulers.io())
        .subscribe({ isLoggedIn ->
          navigationLiveData.postValue(if (isLoggedIn) NavigationTypes.HOME else NavigationTypes.LOGIN)
        }, ::handleError)
        .let(disposables::add)
  }
}
