package com.twitter.core.di

import com.twitter.core.base.BaseActivity
import com.twitter.core.di.module.ApplicationModule
import com.twitter.core.di.module.DataModule
import com.twitter.core.di.module.NetworkModule
import com.twitter.core.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      ApplicationModule::class,
      ViewModelModule::class,
      NetworkModule::class,
      DataModule::class
    ]
)
interface ApplicationComponent {
  fun inject(app: BaseActivity)
}
