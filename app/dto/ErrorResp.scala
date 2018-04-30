package dto

import play.api.libs.json.{Json, OWrites}

case class ErrorResp(reason: String)

object ErrorResp {

  implicit val errorRespWrites: OWrites[ErrorResp] = Json.writes[ErrorResp]

}