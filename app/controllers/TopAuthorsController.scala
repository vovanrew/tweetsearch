package controllers

import com.google.inject._
import dto.{ErrorResp, RespObject}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TopAuthorsService

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}
import dto.ErrorResp._
import dto.Author._
import dto.RespObject._

class TopAuthorsController @Inject() (topAuthorsService: TopAuthorsService, cc: ControllerComponents)
                                     (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def top10(hashtag: String) =  Action.async {

    val authors = topAuthorsService.topNAuthorsByHashtag(10, hashtag)

    authors.transform {
      case Success(authorSeq) if authorSeq.nonEmpty =>
        Try(Ok(Json.toJson(RespObject(data = authorSeq))))

      case Success(authorSeq) if authorSeq.isEmpty =>
        Try(NotFound(Json.toJson(ErrorResp(reason = "No authors were found"))))

      case Failure(e) =>
        Try(InternalServerError(Json.toJson(ErrorResp(reason = e.toString))))
    }
  }

}
