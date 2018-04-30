package dto

import play.api.libs.json.{Json, OWrites}

case class Author(name: String, tweetCount: Int, lastTweetText: String)

object Author {

  implicit val authorWrites: OWrites[Author] = Json.writes[Author]

}