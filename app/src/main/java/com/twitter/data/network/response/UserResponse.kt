package com.twitter.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int
)
