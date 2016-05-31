package controllers

import javax.inject._
import dao.CatDAO
import models.{Cat, CommonResult}
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json._


@Singleton
class HomeController @Inject() (catDao:CatDAO) extends Controller {


  implicit val format = Json.format[Cat]

  def index2(name: String) = Action.async { implicit request =>
    catDao.findByName(name).map {case cat =>
      Ok(Json.toJson(cat.get))}
  }

  def index() = Action {
    Ok(views.html.index())
  }


  def hello = Action { implicit  request =>
    Redirect(routes.HomeController.index())
  }

  def home = Action {
    Ok("hello_world")
      .withCookies(Cookie("sunsai", "43232"))
      .discardingCookies(DiscardingCookie("ssss"))
  }

}
