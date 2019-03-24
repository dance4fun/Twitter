package com.twitter.feature.home

data class Tweet(
    val id: String,
    val userName: String,
    val userIconUrl: String?,
    val message: String
)
