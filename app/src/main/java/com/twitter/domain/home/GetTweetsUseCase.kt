package com.twitter.domain.home

import com.twitter.data.repository.feed.TweetsRepository
import com.twitter.feature.home.Tweet
import io.reactivex.Flowable
import javax.inject.Inject

class GetTweetsUseCase @Inject constructor(
    private val tweetsRepository: TweetsRepository
) {

  operator fun invoke(): Flowable<List<Tweet>> =
      tweetsRepository.observeTweets()
          .map { dbList ->
            dbList.map { Tweet(it.id, it.userName, it.userIcon, it.text) }
          }

}
