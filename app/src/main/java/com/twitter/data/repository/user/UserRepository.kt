package com.twitter.data.repository.user

import com.twitter.domain.login.User
import io.reactivex.Completable
import io.reactivex.Maybe

interface UserRepository {

  fun getUserToken(): Maybe<String>

  fun saveUserToken(token: String): Completable

  fun login(user: User): Completable

  fun clearUserToken(): Completable
}
