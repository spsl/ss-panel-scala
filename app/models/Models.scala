package models


/**
  * Created by sunsai on 2016/5/31 - 08:58.
  */

case class Cat(name:String, color:String)

case class Dog(name:String, color:String)

case class Node(id:Option[Int] = None, name:String, nodeType:String, server:String, method:String, customerMethod:Option[String] = None, trafficRate:Option[String] = None, info:Option[String] = None, status:Option[String] = None, offset:Option[Int] = None, sort:Option[Int] = None)

case class CommonResult(code:Int, message:String, data:Any)

