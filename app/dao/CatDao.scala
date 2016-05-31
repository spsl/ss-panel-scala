package dao

import scala.concurrent.Future

import javax.inject.Inject
import models.Cat
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

/**
  * Created by sunsai on 2016/5/31 - 09:01.
  */
class CatDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Cats = TableQuery[CatsTable]

  def all(): Future[Seq[Cat]] = db.run(Cats.result)

  def insert(cat: Cat): Future[Unit] = db.run(Cats += cat).map { _ => () }

  def findByName(name:String):Future[Option[Cat]] = db.run(Cats.filter(x => x.name === name).result.headOption)

  def update(cat:Cat):Future[Unit] = db.run(Cats.filter(x => x.name === cat.name).update(cat)).map { z => ()}

  private class CatsTable(tag: Tag) extends Table[Cat](tag, "CAT") {

    def name = column[String]("NAME", O.PrimaryKey)
    def color = column[String]("COLOR")

    def * = (name, color) <> (Cat.tupled, Cat.unapply _)
  }
}
