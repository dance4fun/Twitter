package com.twitter.feature.home

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.twitter.R
import com.twitter.core.base.BaseViewModel
import com.twitter.core.extensions.TAG
import com.twitter.core.logger.Logger
import com.twitter.core.navigation.NavigationTypes
import com.twitter.core.util.StringUtils
import com.twitter.domain.home.CreateTweetUseCase
import com.twitter.domain.home.FetchNewTweetsUseCase
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor,
    private val stringUtils: StringUtils
) : BaseViewModel() {

  val isLoading = ObservableBoolean()
  val message = MutableLiveData<String>()
  val tweets = MutableLiveData<List<Tweet>>()
  val errorMessage = MutableLiveData<String>()

  init {
    getTweets()
    observeNewsFeed()
  }

  fun onRefresh() {
    getTweets()
  }

  fun onTweetDoneClicked() {
    homeInteractor.sendTweet(message.value.orEmpty())
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { hideKeyboard() }
        .subscribe(
            { result ->
              when (result) {
                CreateTweetUseCase.UseCaseResult.Success -> message.postValue(null)
                CreateTweetUseCase.UseCaseResult.EmptyMessage -> {
                  errorMessage.postValue(stringUtils.getString(R.string.home_empty_message_error))
                }
                is CreateTweetUseCase.UseCaseResult.Error -> handleError(result.error)
              }
              isLoading.set(result === CreateTweetUseCase.UseCaseResult.Loading)
            },
            ::handleError
        )
        .let(disposables::add)
  }

  fun onLogoutClicked() {
    homeInteractor.logout()
        .subscribeOn(Schedulers.io())
        .subscribe(
            { navigationLiveData.postValue(NavigationTypes.LOGIN) },
            { Logger.e(TAG, it, "Logout error") })
        .let(disposables::add)
  }

  private fun getTweets() {
    homeInteractor.fetchTweets()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { hideKeyboard() }
        .subscribe(
            { result ->
              when (result) {
                FetchNewTweetsUseCase.UseCaseResult.Success -> message.postValue(null)
                is FetchNewTweetsUseCase.UseCaseResult.Error -> handleError(result.error)
              }
              isLoading.set(result === FetchNewTweetsUseCase.UseCaseResult.Loading)
            },
            ::handleError
        )
        .let(disposables::add)
  }

  private fun observeNewsFeed() {
    homeInteractor.observeFeed()
        .subscribeOn(Schedulers.io())
        .doOnNext { isLoading.set(false) }
        .subscribe(
            (tweets::postValue),
            { Logger.e(TAG, it, "error with observing data") }
        )
        .let(disposables::add)
  }
}
