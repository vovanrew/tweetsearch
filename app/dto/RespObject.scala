package dto

import play.api.libs.json.{Json, OWrites}

case class RespObject(status: String = "success", data: Seq[Author] = Seq.empty)

object RespObject {
  import Author._

  implicit val repsObjectWrites: OWrites[RespObject] = Json.writes[RespObject]

}
