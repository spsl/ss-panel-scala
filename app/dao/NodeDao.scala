package dao

import scala.concurrent.Future

import models.{Node}

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by sunsai on 2016/5/31 - 12:39.
  */
class NodeDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends  HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Nodes = TableQuery[NodeTable]

  def all(pageSize:Int, pageIndex:Int):Future[Seq[Node]] = {
    db.run(Nodes.result)
  }


  def list( pageIndex:Int, pageSize:Int):Future[Seq[Node]] = {
    val offset = pageIndex * pageSize
    db.run(Nodes.sortBy(x => x.sort desc).drop(offset).take(pageSize).result)
  }

  def findById(id:Int):Future[Option[Node]] = {
    val query = Nodes.filter( _.id === id).result
    db.run(query.map {_.headOption})
  }

  def insert(node:Node):Future[Unit] = {
    db.run(Nodes += node).map { _ => ()}
  }

  def update(node:Node) : Future[Unit] =  {
    val query = Nodes.filter(x => x.id === node.id).update(node)
    db.run(query).map {_ => ()}
  }

  def delete(id:Int):Future[Unit] = {
    val query = Nodes.filter(_.id === id).delete
    db.run(query).map(_ => ())
  }


  private class NodeTable(tag:Tag) extends Table[Node](tag, "node") {

    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.SqlType("varchar(100)"))
    def nodeType = column[String]("type", O.SqlType("varchar(100)"))
    def server = column[String]("server", O.SqlType("varchar(100)"))
    def method = column[String]("method", O.SqlType("varchar(100)"))
    def customerMethod = column[Option[String]]("customer_method", O.SqlType("varchar(100)"))
    def trafficRate = column[Option[String]]("traffic_rate", O.SqlType("varchar(100)"))
    def info = column[Option[String]]("info", O.SqlType("varchar(100)"))
    def status = column[Option[String]]("status", O.SqlType("varchar(100)"))
    def offset = column[Option[Int]]("offset", O.SqlType("int"))
    def sort = column[Option[Int]]("sort", O.SqlType("int"))

    def * = (id, name, nodeType, server, method, customerMethod, trafficRate, info, status, offset, sort) <> (Node.tupled, Node.unapply _)
  }

}
