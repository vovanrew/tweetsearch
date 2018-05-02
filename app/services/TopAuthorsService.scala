package services

import com.google.inject._
import dto.Author

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

@Singleton
class TopAuthorsService @Inject() (tweetService: TweetService){

  def topNAuthorsByHashtag(n: Int, hashtag: String): Future[Seq[Author]] = {
    val tweets = tweetService.tweetsByHashtag(hashtag)

    //grouping tweets by authors and sort them
    val groupedTweets = tweets.map( tweetList =>
      tweetList.filter(_.retweeted_status.isEmpty) //filtering original tweets here. we need them only, without retweets
        .groupBy(_.user)
        .toSeq
        .sortWith(_._2.length > _._2.length)
        .take(n)
        .filter(_._1.isEmpty)                           //just checking that tweet has an author
        .map(tweet => (tweet._1.get, tweet._2.length))) //we can use .get method here because we filtered empty elements before

    val topAuthors: Future[List[Author]] = groupedTweets.flatMap { grouped =>
      val authors = grouped.map { group =>
        tweetService
          .lastUsersTweet(group._1.screen_name)
          .map( tweet => Author(group._1.screen_name, group._2, tweet.text))
      }.toList

      //List[Future] to Future[List]
      sequenceIgnoringFailures(authors)
    }

    topAuthors
  }

  def sequenceIgnoringFailures[A](xs: List[Future[A]]): Future[List[A]] = {
    val opts = xs.map(_.map(Some(_)).fallbackTo(Future(None)))
    Future.sequence(opts).map(_.flatten)
  }

}
