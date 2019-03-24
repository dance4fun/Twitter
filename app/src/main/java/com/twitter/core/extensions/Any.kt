package com.twitter.core.extensions

val Any.TAG: String
  get() = this::class.java.simpleName
