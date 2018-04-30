package controllers

import com.google.inject._
import play.api.mvc._

@Singleton
class PingController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def ping = Action {
    Ok(views.html.ping("pong"))
  }

}
