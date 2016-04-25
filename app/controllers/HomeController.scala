package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {


  def index = Action {

    val a : Int = 10
    println(a)

    Ok(views.html.index)
  }

  def home = Action {
    Ok("hello_world")
  }

}
