package com.fuscus.seien.appli

import play.api.mvc.Result
import scala.concurrent.Future

package object cont {
  type ActionCont[A] = Cont[Future[Result], A]
}
