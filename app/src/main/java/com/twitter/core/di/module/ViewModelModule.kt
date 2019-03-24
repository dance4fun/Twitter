package com.twitter.core.di.module

import android.arch.lifecycle.ViewModelProvider
import com.twitter.core.base.BaseViewModel
import com.twitter.core.di.ViewModelFactory
import com.twitter.core.di.ViewModelKey
import com.twitter.feature.home.HomeViewModel
import com.twitter.feature.login.LoginViewModel
import com.twitter.feature.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

  @Binds
  @Singleton
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  internal abstract fun homeViewModel(viewModel: HomeViewModel): BaseViewModel

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  internal abstract fun loginViewModel(viewModel: LoginViewModel): BaseViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SplashViewModel::class)
  internal abstract fun splashViewModel(viewModel: SplashViewModel): BaseViewModel
}
