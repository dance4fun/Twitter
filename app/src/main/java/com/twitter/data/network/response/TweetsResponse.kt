package com.twitter.data.network.response

import com.google.gson.annotations.SerializedName

data class TweetsResponse(
    @SerializedName("tweets") val tweets: List<Tweet>
)

data class Tweet(
    @SerializedName("tweet_id") val tweetId: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("icon_url") val iconUrl: String?,
    @SerializedName("message") val message: String,
    @SerializedName("date") val date: String
)
