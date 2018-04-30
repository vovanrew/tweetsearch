package controllers

import com.google.inject._
import dto.ErrorResp
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TopAuthorsService
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}
import dto.ErrorResp._
import dto.Author._

class TopAuthorsController @Inject() (topAuthorsService: TopAuthorsService, cc: ControllerComponents)
                                     (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def top10(hashtag: String) =  Action.async {

    val authors = topAuthorsService.topNAuthorsByHashtag(10, hashtag)

    authors.transform {
      case Success(authorSeq) =>
        Try(Ok(Json.toJson(authorSeq)))

      case Failure(e) =>
        Try(NotFound(Json.toJson(ErrorResp(e.toString))))
    }
  }

}
