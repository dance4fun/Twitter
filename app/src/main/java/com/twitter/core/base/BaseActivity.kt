package com.twitter.core.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.twitter.R
import com.twitter.core.navigation.Navigation
import com.twitter.core.platform.TwitterApplication
import com.twitter.databinding.DialogProgressBinding
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

  @Inject
  lateinit var navigation: Navigation
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val dialogProgressBinding: DialogProgressBinding by lazy {
    DataBindingUtil.inflate<DialogProgressBinding>(
        LayoutInflater.from(this),
        R.layout.dialog_progress, null, false
    )
  }
  private val progressDialog: AlertDialog by lazy {
    AlertDialog.Builder(this, R.style.DialogTransparent)
        .setView(dialogProgressBinding.root)
        .setCancelable(false)
        .create()
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    TwitterApplication.applicationComponent.inject(this)
  }

  fun observeViewModel(viewModel: BaseViewModel) {
    viewModel.showProgressLiveData.observe(this, Observer {
      if (it == true) {
        showProgressDialog()
      } else {
        dismissProgressDialog()
      }
    })
    viewModel.showKeyboardLiveData.observe(this, Observer {
      showHideKeyboard(it ?: false)
    })
    viewModel.navigationLiveData.observe(this, Observer {
      it?.let { navigation.openScreen(this, it) }
    })
  }

  protected fun showError(error: String?) {
    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
  }

  private fun showProgressDialog() {
    progressDialog.show()
  }

  private fun dismissProgressDialog() {
    progressDialog.dismiss()
  }

  private fun showHideKeyboard(isShown: Boolean) {
    with(getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
      if (isShown) toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
      else hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
  }

  protected inline fun <reified T : BaseViewModel> getViewModel(): T =
      ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}
