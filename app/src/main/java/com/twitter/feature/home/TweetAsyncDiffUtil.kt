package com.twitter.feature.home

import android.support.v7.util.DiffUtil


class TweetAsyncDiffUtil : DiffUtil.ItemCallback<Tweet>() {

	override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
		oldItem.id == newItem.id

	override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
		oldItem == newItem
}