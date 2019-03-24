package com.twitter.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tweets")
data class TweetEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_icon") val userIcon: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "date") val date: String
)
