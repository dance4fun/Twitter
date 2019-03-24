package com.twitter.core.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.twitter.core.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<BaseViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var provider = viewModels[modelClass]
    provider = provider ?: viewModels.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
    provider ?: throw IllegalArgumentException("model class $modelClass not found")
    return provider.get() as T
  }
}
