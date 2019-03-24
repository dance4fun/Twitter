package com.twitter.feature.home

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.twitter.R
import com.twitter.core.base.BaseActivity
import com.twitter.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

  private val viewModel: HomeViewModel by lazy {
    getViewModel<HomeViewModel>()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    binding.lifecycleOwner = this

    val adapter = TweetsAdapter()
    binding.newsFeed.adapter = adapter

    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        if (positionStart == 0) binding.newsFeed.scrollToPosition(0)
      }
    })

    observeViewModel(viewModel)
    binding.vm = viewModel
    viewModel.tweets.observe(this@HomeActivity, Observer {
      it?.let(adapter::updateList)
    })

    binding.onEditorActionListener = TextView.OnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        viewModel.onTweetDoneClicked()
        return@OnEditorActionListener true
      }
      false
    }

    viewModel.errorMessage.observe(this@HomeActivity, Observer {
      showError(it)
    })
  }
}

