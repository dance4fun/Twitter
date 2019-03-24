package com.twitter.data.repository.user

import android.content.SharedPreferences
import com.twitter.BuildConfig
import com.twitter.core.util.HttpErrorHandler
import com.twitter.data.network.Api
import com.twitter.domain.login.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryAdapter @Inject constructor(
    private val preferences: SharedPreferences,
    private val api: Api,
    private val errorHandle: HttpErrorHandler
) : UserRepository {

  private companion object {
    const val USER_TOKEN_PREF = "${BuildConfig.APPLICATION_ID}.user_token"
  }

  override fun getUserToken(): Maybe<String> =
      Maybe.fromCallable {
        preferences.getString(USER_TOKEN_PREF, null)
      }

  override fun saveUserToken(token: String): Completable =
      Completable.fromAction {
        preferences.edit().putString(USER_TOKEN_PREF, token).apply()
      }

  override fun login(user: User): Completable =
      api.loginUser(user)
          .onErrorResumeNext { Single.error(errorHandle.handleError(it)) }
          .flatMapCompletable { saveUserToken(it.accessToken) }

  override fun clearUserToken(): Completable =
      Completable.fromAction {
        preferences.edit().remove(USER_TOKEN_PREF).apply()
      }
}
