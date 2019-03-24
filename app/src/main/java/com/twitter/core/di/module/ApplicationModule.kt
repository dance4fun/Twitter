package com.twitter.core.di.module

import android.content.Context
import com.twitter.core.navigation.AppNavigation
import com.twitter.core.navigation.Navigation
import com.twitter.core.platform.TwitterApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TwitterApplication) {

  @Provides
  @Singleton
  fun provideApplicationContext(): Context = application

  @Provides
  @Singleton
  fun provideNavigation(navigation: AppNavigation): Navigation = navigation

}
