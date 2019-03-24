package com.twitter.core.di.module

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.twitter.core.di.qualifier.PreferenceInfo
import com.twitter.data.db.AppDatabase
import com.twitter.data.repository.feed.TweetsRepository
import com.twitter.data.repository.feed.TweetsRepositoryAdapter
import com.twitter.data.repository.user.UserRepository
import com.twitter.data.repository.user.UserRepositoryAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

  @Provides
  @Singleton
  fun provideDatabase(context: Context) =
      Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
          .fallbackToDestructiveMigration()
          .build()

  @Singleton
  @Provides
  fun provideSharedPreferences(application: Context, @PreferenceInfo prefFileName: String): SharedPreferences =
      application.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

  @Provides
  @PreferenceInfo
  fun providePreferenceFileName(): String = "Default_prefs"

  @Provides
  @Singleton
  fun provideGson(): Gson = Gson()

  @Provides
  @Singleton
  fun provideUserRepository(repository: UserRepositoryAdapter): UserRepository = repository

  @Provides
  @Singleton
  fun provideTweetsRepository(repository: TweetsRepositoryAdapter): TweetsRepository = repository
}
