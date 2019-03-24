package com.twitter.feature.home

import android.support.v7.widget.RecyclerView
import com.twitter.databinding.ItemTweetBinding

class TweetViewHolder(
    private val binding: ItemTweetBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: Tweet) {
    binding.item = item
  }
}
