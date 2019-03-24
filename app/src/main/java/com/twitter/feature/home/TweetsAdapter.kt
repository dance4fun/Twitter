package com.twitter.feature.home

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.twitter.R

class TweetsAdapter : RecyclerView.Adapter<TweetViewHolder>() {

  private val differ = AsyncListDiffer(this, TweetAsyncDiffUtil())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder =
      TweetViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context),
              R.layout.item_tweet,
              parent,
              false
          )
      )

  override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
    holder.bind(differ.currentList[position])
  }

  override fun getItemCount() = differ.currentList.size

  fun updateList(newList: List<Tweet>) {
    differ.submitList(newList)
  }

}
