package com.twitter.core.di.module

import com.twitter.data.network.Api
import com.twitter.data.network.MockApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

	@Provides
	@Singleton
	fun provideApi(api: MockApi): Api = api
}
