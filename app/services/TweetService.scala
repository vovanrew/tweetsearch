package services

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.TwitterRestClient._
import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.entities.enums.ResultType
import com.google.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TweetService /*@Inject()*/ (/*twitterClient: TwitterRestClient*/){

  //TwitterRestClient not injected because there was some injection exception.
  //and i decided to deal with it later
  val twitterClient = TwitterRestClient()

  //time limits is not specified (by default it's already 7 days)
  def tweetsByHashtag(hashtag: String): Future[List[Tweet]] = {
    val query = s"#${hashtag}"
    val result = twitterClient.searchTweet(query, count = 100) //default count is 15. max is 100
    val tweets = result.map(_.data.statuses)
    tweets
  }

  def lastUsersTweet(userName: String): Future[Tweet] = {
    val lastTweet = twitterClient.userTimelineForUser(screen_name = userName, count = 1)
    val tweet = lastTweet.map(_.data.headOption match {
      case Some(t) => t
      case None => throw new NoSuchElementException
    })

    tweet
  }

}
