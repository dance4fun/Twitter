package com.twitter.core.util

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class StringUtils @Inject constructor(val context: Context) {

  fun getString(@StringRes resId: Int): String = context.getString(resId)

  @Suppress("SpreadOperator")
  fun formatString(@StringRes resId: Int, vararg args: Any?) = String.format(context.getString(resId), *args)

}
