package com.twitter.feature.login

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.twitter.R
import com.twitter.core.base.BaseActivity
import com.twitter.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

  private val viewModel: LoginViewModel by lazy {
    getViewModel<LoginViewModel>()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    binding.lifecycleOwner = this

    binding.vm = viewModel
    viewModel.errorMessage.observe(this@LoginActivity, Observer {
      showError(it)
    })
    observeViewModel(viewModel)
  }
}

