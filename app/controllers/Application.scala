package controllers

import javax.inject.Inject

import play.api.mvc.Action
import play.api.mvc.Controller

/**
  * Created by sunsai on 2016/4/25 - 13:27.
  */
class Application @Inject() extends Controller{

  def login = Action {
    Ok(views.html.login)
  }
}
