package com.qa.ims.controller

import akka.stream.scaladsl.{Sink, Source}
import com.qa.ims.configuration.MongoConfiguration.{customerCollection, productCollection, productReader, productWriter}
import com.qa.ims.model.{CustomerModel, ProductModel}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.akkastream.State
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.compat.{legacyWriterNewValue, toDocumentReader, toDocumentWriter}
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object ProductController {

  // Create
  def createProduct(product: ProductModel): Future[Unit] =
    productCollection.flatMap(_.insert.one(product).map(_ => {}))

  // Read
  def findAllProducts: Unit = {
    val findFuture: Future[List[ProductModel]] = productCollection.flatMap(_.find(document())
      .cursor[ProductModel]()
      .collect[List](-1, Cursor.FailOnError[List[ProductModel]]()))
    findFuture onComplete {
      case Success(productOption) => println(productOption.toString)
      case Failure(f) => {}
    }
  }

  def findProductByName(name: String) {
    val selector = BSONDocument("name" -> name)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(f) => {}
    }
  }

  /*
  def findProductPriceByName(name: String) {
    val selector = BSONDocument("name" -> name)
    val retrieved: Source[ProductModel, Future[State]] =
      customerCollection.flatMap(_.find(document()).cursor[ProductModel]())


    val findFuture = productCollection.flatMap(_.find(selector, "price").one)
    findFuture onComplete {
      case Success(productOption) => println(retrieved.runWith(Sink.seq[BSONDocument]))
      case Failure(f) => {}
    }
  }
*/

  def findProductByCategory(category: String) {
    val selector = BSONDocument("category" -> category)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(f) => {}
    }
  }

  def findProductById(id: String) {
    val pid = BSONObjectID.parse(id)
    val selector = BSONDocument("_id" -> pid.get)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(f) => {}
    }
  }

  def updateProductByName(name: String, category: String, price: BigDecimal, quantity: Long): Unit = {
    val selector = document("name" -> name)
    val modifier = document("name" -> name, "category" -> category, "price" -> price, "quantity" -> quantity)
    println(s"Updated, the product details are now: name = $name, category = $category, price = $price, quantity = $quantity")
    productCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteProductByName(name: String)  = {
    val selector = document("name" -> name)
    productCollection.flatMap(_.delete.one(selector))
  }



}
