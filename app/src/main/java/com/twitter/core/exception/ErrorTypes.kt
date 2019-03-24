package com.twitter.core.exception

import android.support.annotation.StringDef
import com.twitter.core.exception.ErrorTypes.INVALID_CREDENTIALS

object ErrorTypes {
  const val INVALID_CREDENTIALS = "invalidCredentials"
}

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@StringDef(INVALID_CREDENTIALS)
@Retention(AnnotationRetention.SOURCE)
annotation class ErrorType
