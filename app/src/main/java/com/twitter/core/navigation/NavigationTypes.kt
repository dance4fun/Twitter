package com.twitter.core.navigation

import android.support.annotation.IntDef
import com.twitter.core.navigation.NavigationTypes.HOME
import com.twitter.core.navigation.NavigationTypes.LOGIN

object NavigationTypes {
  const val HOME = 0
  const val LOGIN = 1
}

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@IntDef(HOME, LOGIN)
@Retention(AnnotationRetention.SOURCE)
annotation class NavigationType
