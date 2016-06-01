package controllers

import javax.inject.Inject

import dao.NodeDao
import models.{Cat, Node}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by sunsai on 2016/5/31 - 12:59.
  */
class NodeController @Inject() (nodeDao: NodeDao) extends Controller{

  def index(pageIndex:Int, pageSize:Int) = Action.async {
    nodeDao.list(pageIndex, pageSize).map(nodes => Ok(views.html.node.nodes(nodes)))
  }

  def add() = Action {
   Ok(views.html.node.add())
  }


  val nodeForm = Form(
    mapping(
      "name" -> text(),
      "server" -> text(),
      "nodeType" -> text(),
      "method" -> text(),
      "customerMethod" -> optional(text()),
      "trafficRate" -> optional(text()),
      "info" -> optional(text()),
      "status" -> optional(text()),
      "sort" -> optional(number())
    )((name, server, nodeType, method, customerMethod, trafficRate, info, status, sort) =>
      Node(Some(0), name, server, nodeType, method, customerMethod, trafficRate, info, status, sort))
    ((node:Node) => Some((node.name, node.server, node.nodeType, node.method, node.customerMethod, node.trafficRate, node.info, node.status, node.sort)))
  )



  def save() = Action.async { implicit request =>

    val node:Node = nodeForm.bindFromRequest.get
    println(node)
    nodeDao.insert(node).map {
      _ => Redirect(routes.NodeController.index())
    }

  }


}
