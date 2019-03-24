package com.twitter.core.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.twitter.core.extensions.TAG
import com.twitter.core.logger.Logger
import com.twitter.core.navigation.NavigationType
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  protected val disposables = CompositeDisposable()
  val showProgressLiveData = MutableLiveData<Boolean>()
  val showKeyboardLiveData = MutableLiveData<Boolean>()
  val navigationLiveData = MutableLiveData<@NavigationType Int>()

  fun hideKeyboard() = showKeyboardLiveData.postValue(false)

  protected open fun handleError(throwable: Throwable) {
    Logger.e(TAG, throwable, throwable.message)
  }

  override fun onCleared() {
    disposables.clear()
  }
}
