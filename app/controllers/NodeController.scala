package controllers

import javax.inject.Inject

import dao.NodeDao
import models.Node
import play.api.mvc._
import scala.collection.SortedMap
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by sunsai on 2016/5/31 - 12:59.
  */
class NodeController @Inject() (nodeDao: NodeDao) extends Controller{

  def index() = Action.async {
    nodeDao.all().map(nodes => Ok(views.html.node.nodes(nodes)))
  }

  def add(name:String) = Action.async {
    val node = Node(Some(2), name=name, method = "rc4-md5", server= "127.0.0.1", nodeType = "")
    nodeDao.insert(node).map { _ =>
      Redirect(routes.NodeController.index())
    }
  }


}
