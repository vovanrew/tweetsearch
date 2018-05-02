package utils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Utils {

  def sequenceIgnoringFailures[A](xs: List[Future[A]]): Future[List[A]] = {
    val opts = xs.map(_.map(Some(_)).fallbackTo(Future(None)))
    Future.sequence(opts).map(_.flatten)
  }

}
