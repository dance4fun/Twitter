package com.twitter.feature.splash

import android.os.Bundle
import com.twitter.core.base.BaseActivity

class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    observeViewModel(getViewModel<SplashViewModel>())
  }
}
