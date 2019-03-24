package com.twitter.core.platform

import android.app.Application
import com.facebook.stetho.Stetho
import com.twitter.BuildConfig
import com.twitter.core.di.ApplicationComponent
import com.twitter.core.di.DaggerApplicationComponent
import com.twitter.core.di.module.ApplicationModule
import com.twitter.core.logger.LogcatLogger
import com.twitter.core.logger.Logger

class TwitterApplication : Application() {

  companion object {
    @JvmStatic
    lateinit var applicationComponent: ApplicationComponent
      private set
  }

  override fun onCreate() {
    super.onCreate()
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()

    initLogger()
    initStetho()
  }

  @Suppress("ConstantConditionIf")
  private fun initLogger() {
    if (BuildConfig.DEBUG) {
      Logger.addLogger(LogcatLogger())
    }
  }

  private fun initStetho() {
    Stetho.initializeWithDefaults(this)
  }
}
