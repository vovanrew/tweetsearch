package dto

import play.api.libs.json.{Json, OWrites}

case class ErrorResp(status: String = "failure", reason: String = "Sorry, something went wrong")

object ErrorResp {

  implicit val errorRespWrites: OWrites[ErrorResp] = Json.writes[ErrorResp]

}